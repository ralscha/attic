package ch.ess.sandbox.facelets;


/**
 * Object that contains list items in PanelListBean 
 * @author Robert Swarr
 *
 */
public class PanelListItem {
    private String text;
    private String name;
    private String type;
    private String action;
    private String link;
    
  
    /**
     * Default no argument constructor
     */
    public PanelListItem() {
        super();
    }


    /**
     * Accessor method for the action property.
     * @return Returns the action.
     */
    public String getAction() {
        return action;
    }


    /**
     * Mutator method for the action property.
     * @param action The action to set.
     */
    public void setAction(String action) {
        this.action = action;
    }


    /**
     * Accessor method for the text property.
     * @return String text
     */
    public String getText() {
        return text;
    }


    /**
     * Mutator method for the text property.
     * @param text String
     */
    public void setText(String text) {
        this.text = text;
    }


    /**
     * Accessor method for the link property.
     * @return String link.
     */
    public String getLink() {
        return link;
    }


    /**
     * Mutator method for the link property.
     * @param link String
     */
    public void setLink(String link) {
        this.link = link;
    }


    /**
     * Accessor method for the name property
     * @return String name
     */
    public String getName() {
        return name;
    }


    /**
     * Mutator method for the name property
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Accessor method for the type property.
     * @return String type
     */
    public String getType() {
        return type;
    }


    /**
     * Mutator method for the type property.
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
}
