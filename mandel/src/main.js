import MandelbrotRenderer from './MandelbrotRenderer.js';

class MandelbrotApp {
    constructor() {
        this.renderer = null;
        this.debounceTimers = new Map(); // Store debounce timers
        this.initialize();
    }
    
    initialize() {
        // Wait for DOM to be ready
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', () => this.setup());
        } else {
            this.setup();
        }
    }
    
    async setup() {
        const canvas = document.getElementById('mandelbrotCanvas');
        if (!canvas) {
            console.error('Canvas element not found');
            return;
        }
        
        // Initialize the renderer
        this.renderer = new MandelbrotRenderer(canvas);
        
        // Set up callback for when GPU initialization completes
        this.renderer.onGPUInitialized = () => {
            console.log('GPU initialization completed, updating UI');
            this.updateComputeMethodOptions();
        };
        
        // Setup UI immediately (will show CPU option)
        this.setupUI();
        this.updateComputeMethodOptions();
        
        // Initial render
        this.renderer.render();
        this.renderer.updateInfo();
        
        console.log('Mandelbrot Explorer initialized');
        console.log(`Using ${this.renderer.numWorkers} WebWorkers for rendering`);
    }
    
    setupUI() {
        // Iterations control
        const iterationsInput = document.getElementById('iterations');
        if (iterationsInput) {
            // Handle both 'input' (real-time) and 'change' (when field loses focus) events
            const handleIterationsChange = (e) => {
                const iterations = parseInt(e.target.value);
                if (iterations >= 10 && iterations <= 100000) {
                    // Debounce the input event, but not the change event
                    if (e.type === 'input') {
                        this.debounce('iterations', () => {
                            this.renderer.setIterations(iterations);
                        }, 300); // 300ms debounce for input events
                    } else {
                        // Immediate execution for change events (when user finishes editing)
                        this.clearDebounce('iterations');
                        this.renderer.setIterations(iterations);
                    }
                }
            };
            
            iterationsInput.addEventListener('input', handleIterationsChange);
            iterationsInput.addEventListener('change', handleIterationsChange);
        }
        
        // Color scheme control
        const colorSchemeSelect = document.getElementById('colorScheme');
        if (colorSchemeSelect) {
            colorSchemeSelect.addEventListener('change', (e) => {
                this.renderer.setColorScheme(e.target.value);
            });
        }
        
        // Compute method control
        const computeMethodSelect = document.getElementById('computeMethod');
        if (computeMethodSelect) {
            computeMethodSelect.addEventListener('change', (e) => {
                this.renderer.setComputeMethod(e.target.value);
            });
        }
        
        // Reset view button
        const resetButton = document.getElementById('resetView');
        if (resetButton) {
            resetButton.addEventListener('click', () => {
                this.renderer.resetView();
            });
        }
        
        // Save image button
        const saveButton = document.getElementById('saveImage');
        if (saveButton) {
            saveButton.addEventListener('click', () => {
                this.renderer.saveImage();
            });
        }
        
        // Keyboard shortcuts
        document.addEventListener('keydown', (e) => {
            switch (e.key) {
                case 'r':
                case 'R':
                    this.renderer.resetView();
                    break;
                case 's':
                case 'S':
                    if (e.ctrlKey || e.metaKey) {
                        e.preventDefault();
                        this.renderer.saveImage();
                    }
                    break;
                case '=':
                case '+':
                    this.adjustIterations(10);
                    break;
                case '-':
                case '_':
                    this.adjustIterations(-10);
                    break;
            }
        });
    }
    
    updateComputeMethodOptions() {
        const computeMethodSelect = document.getElementById('computeMethod');
        if (!computeMethodSelect || !this.renderer) return;
        
        console.log('Updating compute method options...');
        console.log('GPU initialized:', this.renderer.gpuInitialized);
        console.log('WebGPU renderer:', this.renderer.webgpuRenderer);
        console.log('WebGPU supported:', this.renderer.webgpuRenderer ? this.renderer.webgpuRenderer.isSupported : 'N/A');
        
        const availableMethods = this.renderer.getAvailableComputeMethods();
        const currentMethod = this.renderer.computeMethod;
        
        console.log('Available methods:', availableMethods);
        console.log('Current method:', currentMethod);
        
        // Clear existing options
        computeMethodSelect.innerHTML = '';
        
        // Add available options
        availableMethods.forEach(method => {
            const option = document.createElement('option');
            option.value = method;
            
            switch (method) {
                case 'cpu':
                    option.textContent = 'CPU (WebWorkers)';
                    break;
                case 'webgpu':
                    option.textContent = 'WebGPU';
                    break;
                default:
                    option.textContent = method.toUpperCase();
            }
            
            if (method === currentMethod) {
                option.selected = true;
            }
            
            computeMethodSelect.appendChild(option);
        });
        
        // Update info message
        console.log(`Available compute methods: ${availableMethods.join(', ')}`);
    }
    
    adjustIterations(delta) {
        const iterationsInput = document.getElementById('iterations');
        if (iterationsInput) {
            const currentIterations = parseInt(iterationsInput.value);
            const newIterations = Math.max(10, Math.min(100000, currentIterations + delta));
            iterationsInput.value = newIterations;
            // Clear any pending debounced calls and execute immediately for keyboard shortcuts
            this.clearDebounce('iterations');
            this.renderer.setIterations(newIterations);
        }
    }

    // Debounce utility methods
    debounce(key, func, delay) {
        // Clear any existing timer for this key
        this.clearDebounce(key);
        
        // Set a new timer
        const timerId = setTimeout(() => {
            func();
            this.debounceTimers.delete(key);
        }, delay);
        
        this.debounceTimers.set(key, timerId);
    }

    clearDebounce(key) {
        if (this.debounceTimers.has(key)) {
            clearTimeout(this.debounceTimers.get(key));
            this.debounceTimers.delete(key);
        }
    }

    clearAllDebounces() {
        this.debounceTimers.forEach(timerId => clearTimeout(timerId));
        this.debounceTimers.clear();
    }
    
    destroy() {
        // Clear all pending debounced calls
        this.clearAllDebounces();
        
        if (this.renderer) {
            this.renderer.destroy();
        }
    }
}

// Initialize the application
const app = new MandelbrotApp();

// Handle page unload
window.addEventListener('beforeunload', () => {
    app.destroy();
});

export default MandelbrotApp;
