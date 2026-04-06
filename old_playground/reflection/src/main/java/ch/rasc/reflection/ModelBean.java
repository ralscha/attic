package ch.rasc.reflection;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModelBean {
	private String name;

	private String idProperty;

	private Map<String, ModelFieldBean> fields = new LinkedHashMap<>();

	private boolean pageing;

	private String readMethod;

	private String createMethod;

	private String updateMethod;

	private String destroyMethod;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdProperty() {
		return this.idProperty;
	}

	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}

	public Map<String, ModelFieldBean> getFields() {
		return this.fields;
	}

	public void setFields(Map<String, ModelFieldBean> fields) {
		this.fields = fields;
	}

	public void addFields(List<ModelFieldBean> modelFields) {
		for (ModelFieldBean bean : modelFields) {
			this.fields.put(bean.getName(), bean);
		}
	}

	public ModelFieldBean getField(String fieldName) {
		return this.fields.get(fieldName);
	}

	public void addField(ModelFieldBean bean) {
		this.fields.put(bean.getName(), bean);
	}

	public boolean isPageing() {
		return this.pageing;
	}

	public void setPageing(boolean pageing) {
		this.pageing = pageing;
	}

	public String getReadMethod() {
		return this.readMethod;
	}

	public void setReadMethod(String readMethod) {
		this.readMethod = readMethod;
	}

	public String getCreateMethod() {
		return this.createMethod;
	}

	public void setCreateMethod(String createMethod) {
		this.createMethod = createMethod;
	}

	public String getUpdateMethod() {
		return this.updateMethod;
	}

	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	public String getDestroyMethod() {
		return this.destroyMethod;
	}

	public void setDestroyMethod(String destroyMethod) {
		this.destroyMethod = destroyMethod;
	}

}
