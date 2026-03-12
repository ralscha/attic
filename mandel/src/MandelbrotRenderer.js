import WebGPURenderer from './WebGPURenderer.js';
import { ColorSchemes } from './ColorSchemes.js';

class MandelbrotRenderer {
    constructor(canvas) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d', { willReadFrequently: true });
        this.workers = [];
        this.numWorkers = navigator.hardwareConcurrency || 4;
        this.isRendering = false;
        this.pendingChunks = 0;
        this.completedChunks = 0;
        this.totalChunks = 0;
        
        // Compute method ('cpu', 'webgpu')
        this.computeMethod = 'cpu';
        this.webgpuRenderer = null;
        this.gpuInitialized = false;
        this.onGPUInitialized = null; // Callback for when GPU initialization completes
        
        // Mandelbrot parameters
        this.minReal = -2.5;
        this.maxReal = 1.0;
        this.minImag = -1.25;
        this.maxImag = 1.25;
        this.maxIterations = 100;
        this.colorScheme = 'classic';
        
        // Interaction state
        this.isDragging = false;
        this.isDrawingZoomRect = false;
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.zoomRectStart = { x: 0, y: 0 };
        this.zoomRectEnd = { x: 0, y: 0 };
        this.lastRenderedImage = null;
        
        // Performance tracking
        this.renderStartTime = 0;
        this.lastComputeTime = 0;
        
        this.initializeWorkers();
        this.setupEventListeners();
        this.resizeCanvas();
        
        // Initialize GPU renderer asynchronously
        this.initializeGPURenderer();
    }
    
    initializeWorkers() {
        for (let i = 0; i < this.numWorkers; i++) {
            const worker = new Worker('/src/mandelbrot-worker.js');
            worker.onmessage = (e) => this.handleWorkerMessage(e);
            this.workers.push(worker);
        }
    }

    async initializeGPURenderer() {
        try {
            console.log('Initializing WebGPU renderer...');
            
            // Initialize WebGPU
            this.webgpuRenderer = new WebGPURenderer();
            const webgpuSuccess = await this.webgpuRenderer.initialize();
            
            this.gpuInitialized = webgpuSuccess;
            
            if (webgpuSuccess) {
                console.log('WebGPU renderer available');
            } else {
                console.log('WebGPU renderer not available');
            }
            
            if (!this.gpuInitialized) {
                console.log('GPU rendering not available, using CPU only');
            }
            
            // Call the callback if it's set
            if (this.onGPUInitialized) {
                this.onGPUInitialized();
            }
        } catch (error) {
            console.error('GPU initialization failed:', error);
            this.gpuInitialized = false;
            
            // Call the callback even on failure
            if (this.onGPUInitialized) {
                this.onGPUInitialized();
            }
        }
    }
    
    setupEventListeners() {
        // Resize handling
        window.addEventListener('resize', () => this.resizeCanvas());
        
        // Mouse events for dragging and zoom rectangle
        this.canvas.addEventListener('mousedown', (e) => this.handleMouseDown(e));
        this.canvas.addEventListener('mousemove', (e) => this.handleMouseMove(e));
        this.canvas.addEventListener('mouseup', (e) => this.handleMouseUp(e));
        this.canvas.addEventListener('mouseleave', () => this.handleMouseLeave());
        
        // Keyboard events for cursor mode
        window.addEventListener('keydown', (e) => this.handleKeyDown(e));
        window.addEventListener('keyup', (e) => this.handleKeyUp(e));
        
        // Touch events for mobile
        this.canvas.addEventListener('touchstart', (e) => this.handleTouchStart(e));
        this.canvas.addEventListener('touchmove', (e) => this.handleTouchMove(e));
        this.canvas.addEventListener('touchend', () => this.handleTouchEnd());
        
        // Prevent context menu
        this.canvas.addEventListener('contextmenu', (e) => e.preventDefault());
    }
    
    resizeCanvas() {
        const container = this.canvas.parentElement;
        this.canvas.width = container.clientWidth;
        this.canvas.height = container.clientHeight;
        
        // Maintain aspect ratio of the complex plane
        const aspectRatio = this.canvas.width / this.canvas.height;
        const currentWidth = this.maxReal - this.minReal;
        const currentHeight = this.maxImag - this.minImag;
        const currentAspectRatio = currentWidth / currentHeight;
        
        if (aspectRatio > currentAspectRatio) {
            // Canvas is wider, expand real axis
            const center = (this.minReal + this.maxReal) / 2;
            const newWidth = currentHeight * aspectRatio;
            this.minReal = center - newWidth / 2;
            this.maxReal = center + newWidth / 2;
        } else {
            // Canvas is taller, expand imaginary axis
            const center = (this.minImag + this.maxImag) / 2;
            const newHeight = currentWidth / aspectRatio;
            this.minImag = center - newHeight / 2;
            this.maxImag = center + newHeight / 2;
        }
        
        this.render();
    }
    
    handleMouseDown(e) {
        const rect = this.canvas.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        
        if (e.shiftKey) {
            // Start dragging
            this.isDragging = true;
            this.lastMouseX = e.clientX;
            this.lastMouseY = e.clientY;
            this.canvas.classList.add('dragging');
        } else {
            // Start drawing zoom rectangle
            this.isDrawingZoomRect = true;
            this.zoomRectStart = { x, y };
            this.zoomRectEnd = { x, y };
        }
    }
    
    handleKeyDown(e) {
        if (e.key === 'Shift' && !this.isDragging && !this.isDrawingZoomRect) {
            this.canvas.classList.add('drag-mode');
        }
    }
    
    handleKeyUp(e) {
        if (e.key === 'Shift') {
            this.canvas.classList.remove('drag-mode');
        }
    }
    
    handleMouseMove(e) {
        const rect = this.canvas.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        
        if (this.isDrawingZoomRect) {
            // Calculate the correct aspect ratio rectangle
            const canvasAspectRatio = this.canvas.width / this.canvas.height;
            
            const deltaX = x - this.zoomRectStart.x;
            const deltaY = y - this.zoomRectStart.y;
            
            // Determine which dimension should drive the rectangle size
            const rectWidth = Math.abs(deltaX);
            const rectHeight = Math.abs(deltaY);
            
            let correctedWidth, correctedHeight;
            
            if (rectWidth / canvasAspectRatio > rectHeight) {
                // Width is the limiting factor
                correctedWidth = rectWidth;
                correctedHeight = rectWidth / canvasAspectRatio;
            } else {
                // Height is the limiting factor
                correctedHeight = rectHeight;
                correctedWidth = rectHeight * canvasAspectRatio;
            }
            
            // Apply the correct signs based on drag direction
            const signX = deltaX >= 0 ? 1 : -1;
            const signY = deltaY >= 0 ? 1 : -1;
            
            this.zoomRectEnd = {
                x: this.zoomRectStart.x + correctedWidth * signX,
                y: this.zoomRectStart.y + correctedHeight * signY
            };
            
            this.drawZoomRectangle();
        } else if (this.isDragging) {
            const deltaX = e.clientX - this.lastMouseX;
            const deltaY = e.clientY - this.lastMouseY;
            
            this.pan(deltaX, deltaY);
            
            this.lastMouseX = e.clientX;
            this.lastMouseY = e.clientY;
        }
    }
    
    handleMouseUp(e) {
        if (this.isDrawingZoomRect) {
            this.completeZoomRectangle();
            this.isDrawingZoomRect = false;
        }
        if (this.isDragging) {
            this.isDragging = false;
            this.canvas.classList.remove('dragging');
            // Check if shift is still held to maintain drag-mode cursor
            if (e.shiftKey) {
                this.canvas.classList.add('drag-mode');
            }
        }
    }
    
    handleMouseLeave() {
        if (this.isDrawingZoomRect) {
            this.cancelZoomRectangle();
        }
        this.isDragging = false;
        this.isDrawingZoomRect = false;
        this.canvas.classList.remove('dragging', 'drag-mode');
    }
    
    handleTouchStart(e) {
        e.preventDefault();
        if (e.touches.length === 1) {
            const touch = e.touches[0];
            this.isDragging = true;
            this.lastMouseX = touch.clientX;
            this.lastMouseY = touch.clientY;
        }
    }
    
    handleTouchMove(e) {
        e.preventDefault();
        if (e.touches.length === 1 && this.isDragging) {
            const touch = e.touches[0];
            const deltaX = touch.clientX - this.lastMouseX;
            const deltaY = touch.clientY - this.lastMouseY;
            
            this.pan(deltaX, deltaY);
            
            this.lastMouseX = touch.clientX;
            this.lastMouseY = touch.clientY;
        }
    }
    
    handleTouchEnd() {
        this.isDragging = false;
    }
    
    pan(deltaX, deltaY) {
        const realDelta = -(deltaX / this.canvas.width) * (this.maxReal - this.minReal);
        const imagDelta = -(deltaY / this.canvas.height) * (this.maxImag - this.minImag);
        
        this.minReal += realDelta;
        this.maxReal += realDelta;
        this.minImag += imagDelta;
        this.maxImag += imagDelta;
        
        this.render();
        this.updateInfo();
    }
    
    zoom(factor, centerReal, centerImag) {
        const realRange = this.maxReal - this.minReal;
        const imagRange = this.maxImag - this.minImag;
        
        const newRealRange = realRange * factor;
        const newImagRange = imagRange * factor;
        
        this.minReal = centerReal - newRealRange / 2;
        this.maxReal = centerReal + newRealRange / 2;
        this.minImag = centerImag - newImagRange / 2;
        this.maxImag = centerImag + newImagRange / 2;
        
        this.render();
        this.updateInfo();
    }
    
    drawZoomRectangle() {
        if (!this.isDrawingZoomRect || !this.lastRenderedImage) return;
        
        // Restore the last rendered image
        this.ctx.putImageData(this.lastRenderedImage, 0, 0);
        
        // Draw the zoom rectangle
        const startX = Math.min(this.zoomRectStart.x, this.zoomRectEnd.x);
        const startY = Math.min(this.zoomRectStart.y, this.zoomRectEnd.y);
        const width = Math.abs(this.zoomRectEnd.x - this.zoomRectStart.x);
        const height = Math.abs(this.zoomRectEnd.y - this.zoomRectStart.y);
        
        // Draw semi-transparent overlay (more transparent)
        this.ctx.fillStyle = 'rgba(255, 107, 107, 0.05)';
        this.ctx.fillRect(startX, startY, width, height);
        
        // Draw the border
        this.ctx.strokeStyle = '#ff6b6b';
        this.ctx.lineWidth = 1;
        this.ctx.setLineDash([3, 3]);
        this.ctx.strokeRect(startX, startY, width, height);
        
        this.ctx.setLineDash([]);
    }
    
    completeZoomRectangle() {
        const startX = Math.min(this.zoomRectStart.x, this.zoomRectEnd.x);
        const startY = Math.min(this.zoomRectStart.y, this.zoomRectEnd.y);
        const endX = Math.max(this.zoomRectStart.x, this.zoomRectEnd.x);
        const endY = Math.max(this.zoomRectStart.y, this.zoomRectEnd.y);
        
        // Only zoom if rectangle is big enough
        if (Math.abs(endX - startX) > 10 && Math.abs(endY - startY) > 10) {
            // Convert screen coordinates to complex coordinates
            const realStart = this.minReal + (startX / this.canvas.width) * (this.maxReal - this.minReal);
            const realEnd = this.minReal + (endX / this.canvas.width) * (this.maxReal - this.minReal);
            const imagStart = this.minImag + (startY / this.canvas.height) * (this.maxImag - this.minImag);
            const imagEnd = this.minImag + (endY / this.canvas.height) * (this.maxImag - this.minImag);
            
            // Update the view bounds
            this.minReal = realStart;
            this.maxReal = realEnd;
            this.minImag = imagStart;
            this.maxImag = imagEnd;
            
            this.render();
            this.updateInfo();
        } else {
            // Rectangle too small, just restore the image to remove the rectangle overlay
            if (this.lastRenderedImage) {
                this.ctx.putImageData(this.lastRenderedImage, 0, 0);
            }
        }
    }
    
    cancelZoomRectangle() {
        if (this.lastRenderedImage) {
            this.ctx.putImageData(this.lastRenderedImage, 0, 0);
        }
    }
    
    resetView() {
        this.minReal = -2.5;
        this.maxReal = 1.0;
        this.minImag = -1.25;
        this.maxImag = 1.25;
        this.resizeCanvas();
        this.updateInfo();
    }
    
    setIterations(iterations) {
        this.maxIterations = iterations;
        this.render();
    }
    
    setColorScheme(scheme) {
        this.colorScheme = scheme;
        this.render();
    }

    setComputeMethod(method) {
        const validMethods = ['cpu'];
        
        if (this.gpuInitialized && this.webgpuRenderer && this.webgpuRenderer.isSupported) {
            validMethods.push('webgpu');
        }
        
        if (validMethods.includes(method)) {
            this.computeMethod = method;
            console.log(`Switched to ${method} compute method`);
            this.updateInfo(); // Update info display
            this.render();
        } else {
            console.warn(`Compute method ${method} not available, staying with ${this.computeMethod}`);
        }
    }

    getAvailableComputeMethods() {
        const methods = ['cpu'];
        
        if (this.gpuInitialized && this.webgpuRenderer && this.webgpuRenderer.isSupported) {
            methods.push('webgpu');
        }
        
        console.log('getAvailableComputeMethods called:', {
            gpuInitialized: this.gpuInitialized,
            webgpuRenderer: !!this.webgpuRenderer,
            isSupported: this.webgpuRenderer ? this.webgpuRenderer.isSupported : 'N/A',
            methods
        });
        
        return methods;
    }
    
    async render() {
        if (this.isRendering) return;
        
        this.isRendering = true;
        this.renderStartTime = performance.now();
        this.showLoading();
        
        try {
            if (this.computeMethod === 'webgpu' && this.webgpuRenderer && this.webgpuRenderer.isSupported) {
                await this.renderWithWebGPU();
            } else {
                this.renderWithCPU();
            }
        } catch (error) {
            console.error('Render error:', error);
            // Fallback to CPU rendering
            if (this.computeMethod !== 'cpu') {
                console.log('Falling back to CPU rendering');
                this.computeMethod = 'cpu';
                this.renderWithCPU();
            }
        }
    }

    async renderWithWebGPU() {
        try {
            // Update progress to show GPU computation
            this.totalChunks = 1;
            this.completedChunks = 0;
            this.updateProgress();
            
            const colorSchemeIndex = ColorSchemes.getColorSchemeIndex(this.colorScheme);
            const iterations = await this.webgpuRenderer.compute(
                this.canvas.width,
                this.canvas.height,
                this.minReal,
                this.maxReal,
                this.minImag,
                this.maxImag,
                this.maxIterations,
                colorSchemeIndex
            );
            
            // Convert iterations to ImageData
            const imageData = ColorSchemes.iterationsToImageData(
                iterations,
                this.canvas.width,
                this.canvas.height,
                this.maxIterations,
                this.colorScheme
            );
            
            // Draw to canvas
            this.ctx.putImageData(imageData, 0, 0);
            
            // Store the rendered image for zoom rectangle overlay
            this.lastRenderedImage = this.ctx.getImageData(0, 0, this.canvas.width, this.canvas.height);
            
            this.completedChunks = 1;
            this.updateProgress();
            this.lastComputeTime = performance.now() - this.renderStartTime;
            this.isRendering = false;
            this.hideLoading();
            this.updateInfo();
        } catch (error) {
            console.error('WebGPU render error:', error);
            throw error;
        }
    }

    renderWithCPU() {
        // Calculate chunk size for optimal performance
        const chunkSize = 64; // 64x64 pixel chunks work well
        const chunksX = Math.ceil(this.canvas.width / chunkSize);
        const chunksY = Math.ceil(this.canvas.height / chunkSize);
        
        this.totalChunks = chunksX * chunksY;
        this.completedChunks = 0;
        this.pendingChunks = this.totalChunks;
        
        // Create image data for the entire canvas
        this.imageData = this.ctx.createImageData(this.canvas.width, this.canvas.height);
        
        // Distribute chunks to workers
        let workerIndex = 0;
        for (let cy = 0; cy < chunksY; cy++) {
            for (let cx = 0; cx < chunksX; cx++) {
                const chunkX = cx * chunkSize;
                const chunkY = cy * chunkSize;
                const chunkWidth = Math.min(chunkSize, this.canvas.width - chunkX);
                const chunkHeight = Math.min(chunkSize, this.canvas.height - chunkY);
                
                const worker = this.workers[workerIndex % this.numWorkers];
                worker.postMessage({
                    chunkX,
                    chunkY,
                    chunkWidth,
                    chunkHeight,
                    minReal: this.minReal,
                    maxReal: this.maxReal,
                    minImag: this.minImag,
                    maxImag: this.maxImag,
                    canvasWidth: this.canvas.width,
                    canvasHeight: this.canvas.height,
                    maxIterations: this.maxIterations,
                    colorScheme: this.colorScheme,
                    workerId: workerIndex % this.numWorkers
                });
                
                workerIndex++;
            }
        }
    }
    
    handleWorkerMessage(e) {
        // Only handle worker messages for CPU rendering
        if (this.computeMethod !== 'cpu') return;
        
        const { chunkX, chunkY, chunkWidth, chunkHeight, imageData } = e.data;
        
        // Copy chunk data to main image data
        for (let y = 0; y < chunkHeight; y++) {
            for (let x = 0; x < chunkWidth; x++) {
                const srcIndex = (y * chunkWidth + x) * 4;
                const destIndex = ((chunkY + y) * this.canvas.width + (chunkX + x)) * 4;
                
                this.imageData.data[destIndex] = imageData[srcIndex];
                this.imageData.data[destIndex + 1] = imageData[srcIndex + 1];
                this.imageData.data[destIndex + 2] = imageData[srcIndex + 2];
                this.imageData.data[destIndex + 3] = imageData[srcIndex + 3];
            }
        }
        
        this.completedChunks++;
        this.updateProgress();
        
        if (this.completedChunks === this.totalChunks) {
            // All chunks completed, draw to canvas
            this.ctx.putImageData(this.imageData, 0, 0);
            
            // Store the rendered image for zoom rectangle overlay
            this.lastRenderedImage = this.ctx.getImageData(0, 0, this.canvas.width, this.canvas.height);
            
            this.lastComputeTime = performance.now() - this.renderStartTime;
            this.isRendering = false;
            this.hideLoading();
            this.updateInfo();
        }
    }
    
    showLoading() {
        document.getElementById('loading').style.display = 'block';
    }
    
    hideLoading() {
        document.getElementById('loading').style.display = 'none';
    }
    
    updateProgress() {
        const progress = (this.completedChunks / this.totalChunks) * 100;
        document.getElementById('progressFill').style.width = `${progress}%`;
    }
    
    updateInfo() {
        const zoom = 3.5 / (this.maxReal - this.minReal);
        const centerReal = (this.minReal + this.maxReal) / 2;
        const centerImag = (this.minImag + this.maxImag) / 2;
        
        const computeMethodDisplay = this.computeMethod.toUpperCase();
        const computeTimeDisplay = this.lastComputeTime > 0 ? `${Math.round(this.lastComputeTime)}ms` : 'N/A';
        
        document.getElementById('info').innerHTML = `
            <div>Compute Time: ${computeTimeDisplay}</div>
            <div>Zoom: ${zoom.toExponential(2)}x</div>
            <div>Center: (${centerReal.toFixed(6)}, ${centerImag.toFixed(6)}i)</div>
            <div>Compute: ${computeMethodDisplay}</div>
            <div>Shift+drag to pan</div>
            <div>Click+drag to zoom rectangle</div>
        `;
    }
    
    saveImage() {
        const link = document.createElement('a');
        link.download = 'mandelbrot.png';
        link.href = this.canvas.toDataURL();
        link.click();
    }
    
    destroy() {
        this.workers.forEach(worker => worker.terminate());
        if (this.webgpuRenderer) {
            this.webgpuRenderer.destroy();
        }
    }
}

export default MandelbrotRenderer;
