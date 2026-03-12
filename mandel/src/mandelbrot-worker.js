// Advanced Mandelbrot calculation using optimized algorithms
class MandelbrotCalculator {
    constructor() {
        this.maxIterations = 100;
        this.escapeRadius = 2;
        this.escapeRadiusSquared = this.escapeRadius * this.escapeRadius;
    }

    setMaxIterations(iterations) {
        this.maxIterations = iterations;
    }

    // Optimized Mandelbrot calculation with period checking and early bailout
    calculate(cx, cy) {
        let x = 0;
        let y = 0;
        let x2 = 0;
        let y2 = 0;
        let iteration = 0;
        
        // Period checking variables
        let periodX = 0;
        let periodY = 0;
        let period = 0;
        let periodCheck = 3;
        
        while (iteration < this.maxIterations && (x2 + y2) < this.escapeRadiusSquared) {
            y = 2 * x * y + cy;
            x = x2 - y2 + cx;
            x2 = x * x;
            y2 = y * y;
            
            // Period checking optimization
            if (x === periodX && y === periodY) {
                return this.maxIterations; // Point is in the set
            }
            
            period++;
            if (period >= periodCheck) {
                period = 0;
                periodCheck *= 2;
                periodX = x;
                periodY = y;
            }
            
            iteration++;
        }
        
        if (iteration === this.maxIterations) {
            return iteration;
        }
        
        // Smooth coloring using continuous escape count
        const log_zn = Math.log(x2 + y2) / 2;
        const nu = Math.log(log_zn / Math.log(2)) / Math.log(2);
        return iteration + 1 - nu;
    }

    // Calculate a chunk of the Mandelbrot set
    calculateChunk(startX, startY, width, height, minReal, maxReal, minImag, maxImag, canvasWidth, canvasHeight) {
        const result = new Float32Array(width * height);
        const realStep = (maxReal - minReal) / canvasWidth;
        const imagStep = (maxImag - minImag) / canvasHeight;
        
        for (let py = 0; py < height; py++) {
            for (let px = 0; px < width; px++) {
                const x = startX + px;
                const y = startY + py;
                
                const real = minReal + x * realStep;
                const imag = minImag + y * imagStep;
                
                const iterations = this.calculate(real, imag);
                result[py * width + px] = iterations;
            }
        }
        
        return result;
    }
}

// Color schemes for visualization
class ColorSchemes {
    static getColor(iterations, maxIterations, scheme = 'classic') {
        if (iterations >= maxIterations) {
            return [0, 0, 0, 255]; // Black for points in the set
        }
        
        // Much more aggressive color mapping for high iteration counts
        let t = iterations / maxIterations;
        
        // For high iteration counts, use banded coloring approach
        if (maxIterations > 200) {
            // Create color bands to ensure variation even when most pixels have similar iteration counts
            const bands = Math.min(256, Math.max(16, Math.floor(maxIterations / 20))); // 16-256 bands
            const bandSize = maxIterations / bands;
            const bandIndex = Math.floor(iterations / bandSize);
            const bandPosition = (iterations % bandSize) / bandSize;
            
            // Create a cyclic pattern that repeats across bands
            t = (bandIndex / bands) + (bandPosition / bands);
            t = t % 1.0; // Wrap around to create repeating pattern
            
            // Add some variation within each band
            const inBandVariation = Math.sin(bandPosition * Math.PI * 4) * 0.1;
            t += inBandVariation;
            t = Math.max(0, Math.min(1, t));
            
            // Apply square root to enhance contrast in lower values
            t = Math.sqrt(t);
        } else {
            // For lower iteration counts, use logarithmic scaling
            t = Math.log(iterations + 1) / Math.log(maxIterations + 1);
        }
        
        // Apply additional enhancement for very high iteration counts
        if (maxIterations > 1000) {
            // Create multiple frequency components for rich detail
            const f1 = Math.sin(iterations * Math.PI * 2 / 50) * 0.15;
            const f2 = Math.sin(iterations * Math.PI * 2 / 127) * 0.1;
            const f3 = Math.sin(iterations * Math.PI * 2 / 317) * 0.05;
            t += f1 + f2 + f3;
            t = Math.max(0, Math.min(1, t));
        }
        
        switch (scheme) {
            case 'classic':
                return this.classicScheme(t);
            case 'fire':
                return this.fireScheme(t);
            case 'ocean':
                return this.oceanScheme(t);
            case 'rainbow':
                return this.rainbowScheme(t);
            case 'electric':
                return this.electricScheme(t);
            case 'sunset':
                return this.sunsetScheme(t);
            case 'purple':
                return this.purpleScheme(t);
            case 'gold':
                return this.goldScheme(t);
            case 'ice':
                return this.iceScheme(t);
            case 'neon':
                return this.neonScheme(t);
            case 'forest':
                return this.forestScheme(t);
            case 'copper':
                return this.copperScheme(t);
            case 'spectrum':
                return this.spectrumScheme(t);
            case 'plasma':
                return this.plasmaScheme(t);
            case 'stripes':
                return this.stripesScheme(t);
            case 'psychedelic':
                return this.psychedelicScheme(t);
            default:
                return this.classicScheme(t);
        }
    }
    
    static classicScheme(t) {
        // Enhanced classic scheme with multiple frequency components
        const t1 = Math.sin(t * Math.PI * 16) * 0.5 + 0.5;
        const t2 = Math.sin(t * Math.PI * 32 + Math.PI / 3) * 0.5 + 0.5;
        const t3 = Math.sin(t * Math.PI * 48 + 2 * Math.PI / 3) * 0.5 + 0.5;
        
        const r = Math.floor(255 * t1);
        const g = Math.floor(255 * t2);
        const b = Math.floor(255 * t3);
        return [r, g, b, 255];
    }
    
    static fireScheme(t) {
        // Enhanced fire scheme with more dynamic color transitions
        const intensity = Math.pow(t, 0.6); // Enhance lower values
        const r = Math.floor(255 * Math.min(1, intensity * 1.5));
        const g = Math.floor(255 * Math.max(0, Math.min(1, intensity * 1.8 - 0.3)));
        const b = Math.floor(255 * Math.max(0, Math.min(1, intensity * 2.5 - 1.5)));
        return [r, g, b, 255];
    }
    
    static oceanScheme(t) {
        const r = Math.floor(255 * t * 0.3);
        const g = Math.floor(255 * Math.sin(t * Math.PI) * 0.8);
        const b = Math.floor(255 * (0.4 + t * 0.6));
        return [r, g, b, 255];
    }
    
    static rainbowScheme(t) {
        // Enhanced rainbow with smoother transitions and more cycles
        const cycles = 2 + t * 4; // Variable cycle count
        const phase = t * cycles;
        const r = Math.floor(255 * (Math.sin(phase * Math.PI / 3) * 0.5 + 0.5));
        const g = Math.floor(255 * (Math.sin((phase + 2) * Math.PI / 3) * 0.5 + 0.5));
        const b = Math.floor(255 * (Math.sin((phase + 4) * Math.PI / 3) * 0.5 + 0.5));
        return [r, g, b, 255];
    }
    
    static electricScheme(t) {
        const r = Math.floor(255 * Math.min(1, t * 3));
        const g = Math.floor(255 * Math.min(1, Math.max(0, (t - 0.3) * 2)));
        const b = Math.floor(255 * (0.8 + 0.2 * Math.sin(t * Math.PI * 8)));
        return [r, g, b, 255];
    }
    
    static sunsetScheme(t) {
        const r = Math.floor(255 * (0.9 + 0.1 * Math.sin(t * Math.PI * 4)));
        const g = Math.floor(255 * Math.pow(t, 0.7) * 0.6);
        const b = Math.floor(255 * Math.pow(t, 2) * 0.4);
        return [r, g, b, 255];
    }
    
    static purpleScheme(t) {
        const r = Math.floor(255 * (0.4 + 0.6 * Math.pow(t, 0.5)));
        const g = Math.floor(255 * Math.pow(t, 2) * 0.5);
        const b = Math.floor(255 * (0.6 + 0.4 * Math.pow(t, 0.3)));
        return [r, g, b, 255];
    }
    
    static goldScheme(t) {
        const r = Math.floor(255 * (0.8 + 0.2 * Math.sin(t * Math.PI * 6)));
        const g = Math.floor(255 * Math.pow(t, 0.8) * 0.7);
        const b = Math.floor(255 * Math.pow(t, 3) * 0.3);
        return [r, g, b, 255];
    }
    
    static iceScheme(t) {
        const r = Math.floor(255 * Math.pow(t, 1.5) * 0.7);
        const g = Math.floor(255 * (0.7 + 0.3 * Math.pow(t, 0.5)));
        const b = Math.floor(255 * (0.9 + 0.1 * Math.sin(t * Math.PI * 3)));
        return [r, g, b, 255];
    }
    
    static neonScheme(t) {
        // Enhanced neon with multiple frequency components
        const cycles = 6 + t * 8; // Increase cycles with iteration depth
        const phase = t * Math.PI * cycles;
        const r = Math.floor(255 * (Math.abs(Math.sin(phase)) * 0.8 + 0.2));
        const g = Math.floor(255 * (Math.abs(Math.sin(phase + Math.PI / 2)) * 0.8 + 0.2));
        const b = Math.floor(255 * (Math.abs(Math.sin(phase + Math.PI)) * 0.8 + 0.2));
        return [r, g, b, 255];
    }
    
    static forestScheme(t) {
        const r = Math.floor(255 * Math.pow(t, 2) * 0.4);
        const g = Math.floor(255 * (0.3 + 0.7 * Math.pow(t, 0.6)));
        const b = Math.floor(255 * Math.pow(t, 1.5) * 0.2);
        return [r, g, b, 255];
    }
    
    static copperScheme(t) {
        const r = Math.floor(255 * (0.7 + 0.3 * Math.pow(t, 0.4)));
        const g = Math.floor(255 * Math.pow(t, 0.8) * 0.5);
        const b = Math.floor(255 * Math.pow(t, 1.5) * 0.3);
        return [r, g, b, 255];
    }

    // New schemes optimized for high iteration counts
    static spectrumScheme(t) {
        // Ultra-high frequency for maximum color variation
        const freq = 64; // Very high frequency for detailed banding
        const r = Math.floor(255 * (Math.sin(t * Math.PI * freq) * 0.5 + 0.5));
        const g = Math.floor(255 * (Math.sin(t * Math.PI * freq + Math.PI / 3) * 0.5 + 0.5));
        const b = Math.floor(255 * (Math.sin(t * Math.PI * freq + 2 * Math.PI / 3) * 0.5 + 0.5));
        return [r, g, b, 255];
    }
    
    static plasmaScheme(t) {
        // Multi-layered color scheme with very high frequency components
        const f1 = Math.sin(t * Math.PI * 20);
        const f2 = Math.sin(t * Math.PI * 40 + Math.PI / 4);
        const f3 = Math.sin(t * Math.PI * 80 + Math.PI / 2);
        const f4 = Math.sin(t * Math.PI * 160 + 3 * Math.PI / 4);
        
        const r = Math.floor(255 * Math.abs(f1 * 0.4 + f2 * 0.3 + f3 * 0.2 + 0.1));
        const g = Math.floor(255 * Math.abs(f2 * 0.4 + f3 * 0.3 + f4 * 0.2 + 0.1));
        const b = Math.floor(255 * Math.abs(f3 * 0.4 + f4 * 0.3 + f1 * 0.2 + 0.1));
        return [r, g, b, 255];
    }
    
    // New schemes for maximum color variation with high iterations
    static stripesScheme(t) {
        // Create sharp color stripes that cycle rapidly
        const stripeWidth = 0.02; // Very thin stripes
        const stripeIndex = Math.floor(t / stripeWidth);
        const hue = (stripeIndex * 0.618034) % 1.0; // Golden ratio for good distribution
        
        // Convert HSV to RGB
        const h = hue * 6;
        const c = 1; // Full saturation
        const x = c * (1 - Math.abs((h % 2) - 1));
        
        let r, g, b;
        if (h < 1) { r = c; g = x; b = 0; }
        else if (h < 2) { r = x; g = c; b = 0; }
        else if (h < 3) { r = 0; g = c; b = x; }
        else if (h < 4) { r = 0; g = x; b = c; }
        else if (h < 5) { r = x; g = 0; b = c; }
        else { r = c; g = 0; b = x; }
        
        return [Math.floor(255 * r), Math.floor(255 * g), Math.floor(255 * b), 255];
    }
    
    static psychedelicScheme(t) {
        // Extremely high contrast with multiple overlapping patterns
        const p1 = Math.sin(t * Math.PI * 100) > 0 ? 1 : 0;
        const p2 = Math.sin(t * Math.PI * 147 + Math.PI / 7) > 0 ? 1 : 0;
        const p3 = Math.sin(t * Math.PI * 223 + Math.PI / 11) > 0 ? 1 : 0;
        
        const r = Math.floor(255 * ((p1 * 0.8) + (p2 * 0.2)));
        const g = Math.floor(255 * ((p2 * 0.8) + (p3 * 0.2)));
        const b = Math.floor(255 * ((p3 * 0.8) + (p1 * 0.2)));
        
        return [r, g, b, 255];
    }
}

// Worker message handler
self.onmessage = function(e) {
    const { 
        chunkX, 
        chunkY, 
        chunkWidth, 
        chunkHeight, 
        minReal, 
        maxReal, 
        minImag, 
        maxImag, 
        canvasWidth, 
        canvasHeight, 
        maxIterations,
        colorScheme,
        workerId 
    } = e.data;
    
    const calculator = new MandelbrotCalculator();
    calculator.setMaxIterations(maxIterations);
    
    // Calculate the Mandelbrot values for this chunk
    const iterations = calculator.calculateChunk(
        chunkX, chunkY, chunkWidth, chunkHeight,
        minReal, maxReal, minImag, maxImag,
        canvasWidth, canvasHeight
    );
    
    // Convert to colored pixels
    const imageData = new Uint8ClampedArray(chunkWidth * chunkHeight * 4);
    
    for (let i = 0; i < iterations.length; i++) {
        const color = ColorSchemes.getColor(iterations[i], maxIterations, colorScheme);
        const pixelIndex = i * 4;
        imageData[pixelIndex] = color[0];     // R
        imageData[pixelIndex + 1] = color[1]; // G
        imageData[pixelIndex + 2] = color[2]; // B
        imageData[pixelIndex + 3] = color[3]; // A
    }
    
    // Send the result back
    self.postMessage({
        workerId,
        chunkX,
        chunkY,
        chunkWidth,
        chunkHeight,
        imageData
    });
};
