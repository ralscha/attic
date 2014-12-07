package ch.rasc.packt.dto;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelAssociation;
import ch.rasc.extclassgenerator.ModelAssociationType;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Model(value = "Packt.model.menu.Item")
public class Item {

	@ModelField(useNull = true, convert = "null")
	private Long id;

	private String text;

	private String iconCls;

	private String className;

	private Long root_id;

	private boolean leaf = true;

	@ModelField(useNull = true)
	private Boolean checked;

	@ModelAssociation(ModelAssociationType.BELONGS_TO)
	@JsonIgnore
	private Root root;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getRoot_id() {
		return root_id;
	}

	public void setRoot_id(Long root_id) {
		this.root_id = root_id;
	}

	public Root getRoot() {
		return root;
	}

	public void setRoot(Root root) {
		this.root = root;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

}
