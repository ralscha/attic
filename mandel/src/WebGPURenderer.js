class WebGPURenderer {
    constructor() {
        this.device = null;
        this.adapter = null;
        this.computePipeline = null;
        this.bindGroup = null;
        this.isSupported = false;
        this.uniformBuffer = null;
        this.outputBuffer = null;
        this.stagingBuffer = null;
    }

    async initialize() {
        console.log('Starting WebGPU initialization...');
        try {
            // Check if WebGPU is supported
            if (!navigator.gpu) {
                console.warn('WebGPU not supported - navigator.gpu is not available');
                return false;
            }
            console.log('WebGPU API detected');

            // Request adapter and device
            console.log('Requesting WebGPU adapter...');
            this.adapter = await navigator.gpu.requestAdapter();
            if (!this.adapter) {
                console.warn('No WebGPU adapter found');
                return false;
            }
            console.log('WebGPU adapter obtained');

            console.log('Requesting WebGPU device...');
            this.device = await this.adapter.requestDevice();
            if (!this.device) {
                console.warn('WebGPU device request failed');
                return false;
            }
            console.log('WebGPU device obtained');

            this.isSupported = true;
            console.log('Setting up compute pipeline...');
            await this.setupComputePipeline();
            console.log('WebGPU initialized successfully');
            return true;
        } catch (error) {
            console.error('WebGPU initialization failed:', error);
            return false;
        }
    }

    async setupComputePipeline() {
        const shaderModule = this.device.createShaderModule({
            label: 'Mandelbrot Compute Shader',
            code: this.getComputeShaderCode()
        });

        this.computePipeline = this.device.createComputePipeline({
            label: 'Mandelbrot Compute Pipeline',
            layout: 'auto',
            compute: {
                module: shaderModule,
                entryPoint: 'main'
            }
        });

        // Create uniform buffer for parameters
        this.uniformBuffer = this.device.createBuffer({
            label: 'Mandelbrot Uniforms',
            size: 64, // 16 floats * 4 bytes = 64 bytes
            usage: GPUBufferUsage.UNIFORM | GPUBufferUsage.COPY_DST
        });
    }

    getComputeShaderCode() {
        return `
            struct Uniforms {
                width: f32,
                height: f32,
                min_real: f32,
                max_real: f32,
                min_imag: f32,
                max_imag: f32,
                max_iterations: f32,
                color_scheme: f32,
                escape_radius: f32,
                padding: vec3<f32>,
            }

            @group(0) @binding(0) var<uniform> uniforms: Uniforms;
            @group(0) @binding(1) var<storage, read_write> output: array<f32>;

            fn mandelbrot_calculate(cx: f32, cy: f32) -> f32 {
                var x: f32 = 0.0;
                var y: f32 = 0.0;
                var x2: f32 = 0.0;
                var y2: f32 = 0.0;
                var iteration: f32 = 0.0;
                let escape_radius_sq = uniforms.escape_radius * uniforms.escape_radius;
                
                while (iteration < uniforms.max_iterations && (x2 + y2) < escape_radius_sq) {
                    y = 2.0 * x * y + cy;
                    x = x2 - y2 + cx;
                    x2 = x * x;
                    y2 = y * y;
                    iteration = iteration + 1.0;
                }
                
                if (iteration >= uniforms.max_iterations) {
                    return iteration;
                }
                
                // Smooth coloring
                let log_zn = log(x2 + y2) / 2.0;
                let nu = log(log_zn / log(2.0)) / log(2.0);
                return iteration + 1.0 - nu;
            }

            @compute @workgroup_size(8, 8)
            fn main(@builtin(global_invocation_id) global_id: vec3<u32>) {
                let x = global_id.x;
                let y = global_id.y;
                
                if (x >= u32(uniforms.width) || y >= u32(uniforms.height)) {
                    return;
                }
                
                let real_step = (uniforms.max_real - uniforms.min_real) / uniforms.width;
                let imag_step = (uniforms.max_imag - uniforms.min_imag) / uniforms.height;
                
                let real = uniforms.min_real + f32(x) * real_step;
                let imag = uniforms.min_imag + f32(y) * imag_step;
                
                let iterations = mandelbrot_calculate(real, imag);
                let index = y * u32(uniforms.width) + x;
                output[index] = iterations;
            }
        `;
    }

    async compute(width, height, minReal, maxReal, minImag, maxImag, maxIterations, colorScheme = 0) {
        if (!this.isSupported || !this.device) {
            throw new Error('WebGPU not initialized');
        }

        const bufferSize = width * height * 4; // 4 bytes per float

        // Create or recreate output buffer if size changed
        if (!this.outputBuffer || this.outputBuffer.size !== bufferSize) {
            if (this.outputBuffer) this.outputBuffer.destroy();
            if (this.stagingBuffer) this.stagingBuffer.destroy();

            this.outputBuffer = this.device.createBuffer({
                label: 'Mandelbrot Output',
                size: bufferSize,
                usage: GPUBufferUsage.STORAGE | GPUBufferUsage.COPY_SRC
            });

            this.stagingBuffer = this.device.createBuffer({
                label: 'Mandelbrot Staging',
                size: bufferSize,
                usage: GPUBufferUsage.MAP_READ | GPUBufferUsage.COPY_DST
            });
        }

        // Update uniforms
        const uniformData = new Float32Array([
            width, height, minReal, maxReal,
            minImag, maxImag, maxIterations, colorScheme,
            2.0, 0, 0, 0 // escape_radius and padding
        ]);

        this.device.queue.writeBuffer(this.uniformBuffer, 0, uniformData);

        // Create bind group
        this.bindGroup = this.device.createBindGroup({
            label: 'Mandelbrot Bind Group',
            layout: this.computePipeline.getBindGroupLayout(0),
            entries: [
                {
                    binding: 0,
                    resource: {
                        buffer: this.uniformBuffer
                    }
                },
                {
                    binding: 1,
                    resource: {
                        buffer: this.outputBuffer
                    }
                }
            ]
        });

        // Create command encoder
        const commandEncoder = this.device.createCommandEncoder({
            label: 'Mandelbrot Command Encoder'
        });

        // Dispatch compute shader
        const computePass = commandEncoder.beginComputePass({
            label: 'Mandelbrot Compute Pass'
        });

        computePass.setPipeline(this.computePipeline);
        computePass.setBindGroup(0, this.bindGroup);
        
        const workgroupsX = Math.ceil(width / 8);
        const workgroupsY = Math.ceil(height / 8);
        computePass.dispatchWorkgroups(workgroupsX, workgroupsY);
        computePass.end();

        // Copy results to staging buffer
        commandEncoder.copyBufferToBuffer(
            this.outputBuffer, 0,
            this.stagingBuffer, 0,
            bufferSize
        );

        // Submit commands
        this.device.queue.submit([commandEncoder.finish()]);

        // Read results
        await this.stagingBuffer.mapAsync(GPUMapMode.READ);
        const arrayBuffer = this.stagingBuffer.getMappedRange();
        const result = new Float32Array(arrayBuffer);
        const data = new Float32Array(result); // Copy the data
        this.stagingBuffer.unmap();

        return data;
    }

    destroy() {
        if (this.uniformBuffer) this.uniformBuffer.destroy();
        if (this.outputBuffer) this.outputBuffer.destroy();
        if (this.stagingBuffer) this.stagingBuffer.destroy();
        if (this.device) this.device.destroy();
    }
}

export default WebGPURenderer;
