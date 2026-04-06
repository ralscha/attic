package ch.rasc.reflection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModelGenerator {

	public static String generateJavascript(Class<?> clazz, OutputFormat format) {
		ModelBean model = createModel(clazz);
		return generateJavascript(model, format);
	}

	public static ModelBean createModel(Class<?> clazz) {
		Model modelAnnotation = clazz.getAnnotation(Model.class);

		final ModelBean model = new ModelBean();

		if (modelAnnotation != null && StringUtils.hasText(modelAnnotation.value())) {
			model.setName(modelAnnotation.value());
		}
		else {
			model.setName(clazz.getName());
		}

		if (modelAnnotation != null) {
			model.setIdProperty(modelAnnotation.idProperty());
			model.setPageing(modelAnnotation.paging());
			model.setCreateMethod(modelAnnotation.createMethod());
			model.setReadMethod(modelAnnotation.readMethod());
			model.setUpdateMethod(modelAnnotation.updateMethod());
			model.setDestroyMethod(modelAnnotation.destroyMethod());
		}

		final Set<String> hasReadMethod = new HashSet<>();

		BeanInfo bi;
		try {
			bi = Introspector.getBeanInfo(clazz);
		}
		catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}

		for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
			if (pd.getReadMethod() != null) {
				hasReadMethod.add(pd.getName());
			}
		}

		final List<ModelFieldBean> modelFields = new ArrayList<>();

		ReflectionUtils.doWithFields(clazz, new FieldCallback() {
			private final Set<String> fields = new HashSet<>();

			@Override
			public void doWith(Field field)
					throws IllegalArgumentException, IllegalAccessException {
				if (Modifier.isPublic(field.getModifiers())
						|| hasReadMethod.contains(field.getName())) {
					if (this.fields.contains(field.getName())) {
						// ignore superclass declarations of fields already
						// found in a subclass
					}
					else {
						this.fields.add(field.getName());

						Class<?> type = field.getType();

						ModelType mt = null;
						for (ModelType t : ModelType.values()) {
							if (t.supports(type)) {
								mt = t;
								break;
							}
						}

						if (mt != null) {
							ModelFieldBean modelField = new ModelFieldBean(
									field.getName(), mt);

							ModelField mf = field.getAnnotation(ModelField.class);
							if (mf != null) {
								if (StringUtils.hasText(mf.dateFormat())) {
									modelField.setDateFormat(mf.dateFormat());
								}

								if (StringUtils.hasText(mf.defaultValue())) {
									if (mt == ModelType.BOOLEAN) {
										modelField.setDefaultValue(
												Boolean.valueOf(mf.defaultValue()));
									}
									else if (mt == ModelType.INTEGER) {
										modelField.setDefaultValue(
												Long.valueOf(mf.defaultValue()));
									}
									else if (mt == ModelType.FLOAT) {
										modelField.setDefaultValue(
												Double.valueOf(mf.defaultValue()));
									}
									else {
										modelField.setDefaultValue(mf.defaultValue());
									}
								}

								if (mf.useNull()) {
									modelField.setUseNull(Boolean.TRUE);
								}
							}

							modelFields.add(modelField);
						}

					}
				}
			}
		});

		model.addFields(modelFields);
		return model;
	}

	public static String generateJavascript(ModelBean model, OutputFormat format) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
		Map<String, Object> modelObject = new LinkedHashMap<>();
		modelObject.put("extend", "Ext.data.Model");

		Map<String, Object> configObject = new LinkedHashMap<>();

		configObject.put("fields", model.getFields().values());

		if (StringUtils.hasText(model.getIdProperty())
				&& !model.getIdProperty().equals("id")) {
			configObject.put("idProperty", model.getIdProperty());
		}

		Map<String, Object> proxyObject = new LinkedHashMap<>();
		proxyObject.put("type", "direct");

		Map<String, Object> apiObject = new LinkedHashMap<>();

		if (StringUtils.hasText(model.getReadMethod())
				&& !StringUtils.hasText(model.getCreateMethod())
				&& !StringUtils.hasText(model.getUpdateMethod())
				&& !StringUtils.hasText(model.getDestroyMethod())) {
			proxyObject.put("directFn", model.getReadMethod());

		}
		else {

			if (StringUtils.hasText(model.getReadMethod())) {
				apiObject.put("read", model.getReadMethod());
			}

			if (StringUtils.hasText(model.getCreateMethod())) {
				apiObject.put("create", model.getCreateMethod());
			}

			if (StringUtils.hasText(model.getUpdateMethod())) {
				apiObject.put("update", model.getUpdateMethod());
			}

			if (StringUtils.hasText(model.getDestroyMethod())) {
				apiObject.put("destroy", model.getDestroyMethod());
			}

			if (!apiObject.isEmpty()) {
				proxyObject.put("api", apiObject);
			}
		}

		if (model.isPageing()) {
			Map<String, Object> readerObject = new LinkedHashMap<>();
			readerObject.put("root", "records");
			proxyObject.put("reader", readerObject);
		}

		if (!apiObject.isEmpty() || proxyObject.containsKey("directFn")) {
			configObject.put("proxy", proxyObject);
		}

		if (format == OutputFormat.EXTJS) {
			modelObject.putAll(configObject);
		}
		else {
			modelObject.put("config", configObject);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Ext.define('").append(model.getName()).append("',\n");

		String configObjectString;
		try {
			configObjectString = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(modelObject);
		}
		catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		}
		catch (JsonMappingException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		configObjectString = configObjectString.replace("\"", "'");
		configObjectString = configObjectString.replaceAll("directFn : '([^']+)'",
				"directFn : $1");
		configObjectString = configObjectString.replaceAll("read : '([^']+)'",
				"read : $1");
		configObjectString = configObjectString.replaceAll("create : '([^']+)'",
				"create : $1");
		configObjectString = configObjectString.replaceAll("update : '([^']+)'",
				"update : $1");
		configObjectString = configObjectString.replaceAll("destroy : '([^']+)'",
				"destroy : $1");
		sb.append(configObjectString);
		sb.append(");");

		return sb.toString();
	}

}
