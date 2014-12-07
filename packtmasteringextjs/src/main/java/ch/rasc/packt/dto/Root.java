package ch.rasc.packt.dto;

import java.util.List;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelAssociation;
import ch.rasc.extclassgenerator.ModelAssociationType;
import ch.rasc.extclassgenerator.ModelField;

@Model(value = "Packt.model.menu.Root", readMethod = "menuService.read")
public class Root {
	@ModelField(useNull = true, convert = "null")
	private Long id;

	private String text;

	private String iconCls;

	private boolean leaf = false;

	@ModelField(useNull = true)
	private Boolean checked;

	@ModelField(useNull = true)
	private Boolean expanded;

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	@ModelAssociation(value = ModelAssociationType.HAS_MANY, model = Item.class)
	private List<Item> children;

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

	public List<Item> getChildren() {
		return children;
	}

	public void setChildren(List<Item> children) {
		this.children = children;
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

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

}
