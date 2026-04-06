/*
 * Copyright the original author or authors.
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
package ch.rasc.openai4j.common;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;

/**
 * Create a JSON schema for a given class and convert a JSON string to an object.
 */
public class JsonSchemaService {

	private final SchemaGenerator strictSchemaGenerator;
	private final SchemaGenerator nonStrictSchemaGenerator;

	public JsonSchemaService() {
		JacksonModule module = new JacksonModule(
				JacksonOption.RESPECT_JSONPROPERTY_REQUIRED);
		SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(
				SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON).with(module);

		this.strictSchemaGenerator = new SchemaGenerator(configBuilder
				.with(Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT).build());
		this.nonStrictSchemaGenerator = new SchemaGenerator(configBuilder.build());
	}

	public ObjectNode generateStrictSchema(Class<?> clazz) {
		return this.strictSchemaGenerator.generateSchema(clazz);
	}

	public ObjectNode generateNonStrictSchema(Class<?> clazz) {
		return this.nonStrictSchemaGenerator.generateSchema(clazz);
	}

	public ResponseFormat createStrictResponseFormat(Class<?> clazz) {
		return createResponseFormat(clazz, null, null, true);
	}

	public ResponseFormat createStrictResponseFormat(Class<?> clazz, String name) {
		return createResponseFormat(clazz, name, null, true);
	}

	public ResponseFormat createStrictResponseFormat(Class<?> clazz, String name,
			String description) {
		return createResponseFormat(clazz, name, description, true);
	}

	public ResponseFormat createNonStrictResponseFormat(Class<?> clazz) {
		return createResponseFormat(clazz, null, null, false);
	}

	public ResponseFormat createNonStrictResponseFormat(Class<?> clazz, String name) {
		return createResponseFormat(clazz, name, null, false);
	}

	public ResponseFormat createNonStrictResponseFormat(Class<?> clazz, String name,
			String description) {
		return createResponseFormat(clazz, name, description, false);
	}

	public ResponseFormat createResponseFormat(Class<?> clazz, String name,
			String description, boolean strict) {
		String schemaName = name;
		if (schemaName == null || schemaName.isBlank()) {
			schemaName = clazz.getSimpleName();
		}
		ObjectNode schema;
		if (strict) {
			schema = generateStrictSchema(clazz);
		}
		else {
			schema = generateNonStrictSchema(clazz);
		}
		ResponseFormatJsonSchema responseFormatJsonSchema = ResponseFormatJsonSchema
				.builder().description(description).name(schemaName).schema(schema)
				.strict(strict).build();
		return ResponseFormat.jsonSchema(responseFormatJsonSchema);
	}

}
