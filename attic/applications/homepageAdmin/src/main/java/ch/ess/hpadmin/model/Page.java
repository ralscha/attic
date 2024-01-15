package ch.ess.hpadmin.model;


public class Page {
  private Integer id;
  private String name;
  private String title;
  private Integer parent;
  private String parentName;
  private Integer priority;
  private Boolean visibility;
  private Integer templateId;
  private String templateMain;
  private String templateRight;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public Integer getParent() {
    return parent;
  }
  
  public void setParent(Integer parent) {
    this.parent = parent;
  }
  
  public Integer getPriority() {
    return priority;
  }
  
  public void setPriority(Integer priority) {
    this.priority = priority;
  }
  
  public Boolean getVisibility() {
    return visibility;
  }
  
  public void setVisibility(Boolean visibility) {
    this.visibility = visibility;
  }
  
  public Integer getTemplateId() {
    return templateId;
  }
  
  public void setTemplateId(Integer templateId) {
    this.templateId = templateId;
  }
  
  public String getTemplateMain() {
    return templateMain;
  }
  
  public void setTemplateMain(String templateMain) {
    this.templateMain = templateMain;
  }
  
  public String getTemplateRight() {
    return templateRight;
  }
  
  public void setTemplateRight(String templateRight) {
    this.templateRight = templateRight;
  }

  
  public String getParentName() {
    return parentName;
  }

  
  public void setParentName(String parentName) {
    this.parentName = parentName;
  }
  
  
  
}
