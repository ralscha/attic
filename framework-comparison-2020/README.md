# Micronaut vs Quarkus vs Spring Boot vs Go vs Rust

This repo contains a http request performance comparison between Micronaut, Quarkus, Spring Boot, Go and Rust.

### Setup

```
wget https://raw.githubusercontent.com/ralscha/framework-comparison-2020/refs/heads/master/setup.sh
source ./setup.sh
```


### Results

| FRAMEWORK              | Package Size in Bytes | Time to First Response (ms) | K6: Requests per second | Memory Consumption After K6 (RSS in kB) |
|---|--:|--:|--:|--:|
| Micronaut 4.9.3 OpenJDK 24    |  \* 12_156_752  | 1_075  |  56_890  | 584_729 |
| Micronaut 4.9.3 Native        | 75_237_696  |  26 | 50_457  | 73_690 | 
| Quarkus 3.26.2 OpenJDK 24     | \* 18_738_895  | 1_143  | 48_096  | 519_997 |
| Quarkus 3.26.2 Native         |  59_050_304 |  21 |  46_510 | 63_479 | 
| Spring Boot 3.5.5 OpenJDK 24  | \* 24_181_346 | 2_208  | 40_845  | 1_151_346 | 
| Spring Boot 3.5.5 Native      | 91_883_840  |  52 | 37_744  | 183_082 |
| Go 1.25.0                     | 5_812_516  |  14 | 55_984  | 44_964 | 
| Go 1.25.0  Echo  4.13.4       | 6_197_540  |  15 | 56_033 | 44_944 | 
| Go 1.25.0  Fibre 3.0.0-rc.1   | 8_257_828  |  **13** |  62_356 | **44_710** | 
| Rust 1.89.0 Salvo 0.83.0      |  **2_933_784**  |  **13** |  **67_846** | 44_753 |

\* = requires JVM

Tests ran on a [Hetzner](https://hetzner.cloud/?ref=n8nOAQHMszMa) (referral link) VPS CPX41 AMD (8 VCPU, 16 GB RAM)      
OS: Debian 13    
7th September 2025

