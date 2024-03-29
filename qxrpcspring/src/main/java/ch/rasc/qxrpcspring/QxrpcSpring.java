/**
 * Copyright 2014-2014 Ralph Schaer <ralphschaer@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.qxrpcspring;

import org.springframework.context.annotation.ComponentScan;

/**
 * No-op marker interface. Helps to configure qrpcspring in a type-safe way with
 * {@link ComponentScan#basePackageClasses()}
 * <p>
 *
 * <pre>
 *   {@literal @}Configuration
 *   {@literal @}ComponentScan(basePackageClasses=QRPCSpring.class)
 *   public class Application { ... }
 * </pre>
 */
public interface QxrpcSpring {
	// nothing here. just a marker interface
}
