package ch.ess.sandbox.facelets;

/**
 * Contains an item used to build an HTML list.
 * Used in the RightSidebarBean from Chapter 5 of 
 * the Facelets Shortcut.
 * @author Robert Swarr
 */
public class ListItem {
    private String label;
    private String name;
    private String type;
    private String valueBinding;
    private String action;
    private String link;
  
  
    /**
     * Default no argument constructor
     */
    public ListItem() {
        super();
    }

    /**
     * Accessor method for the action property.
     * @return String action
     */
    public String getAction() {
        return action;
    }

    /**
     * Mutatoror method for the action property.
     * @param action String
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Accessor method for the label property.
     * @return String label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Mutator method for the label property
     * @param label String
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Accessor method for the link property.
     * @return String
     */
    public String getLink() {
        return link;
    }

    /**
     * Mutator method for the link property
     * @param link String
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Accessor method for the name property.
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator method for the name property.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor method for the type property.
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Mutator method for the type property.
     * @param type String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Accessor method for the valudeBinding property.
     * @return Returns the valueBinding.
     */
    public String getValueBinding() {
        return valueBinding;
    }

    /**
     * Mutator method for the valueBinding property.
     * @param valueBinding The valueBinding to set.
     */
    public void setValueBinding(String valueBinding) {
        this.valueBinding = valueBinding;
    }
}
