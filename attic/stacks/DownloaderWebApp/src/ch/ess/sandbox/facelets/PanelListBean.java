package ch.ess.sandbox.facelets;

// JSE imports
import java.util.List;


/**
 * Abstract base bean that is extended by all 
 * JSF backing beans for the panel-list Facelets component.
 * Example 6.9 in the Facelets Shortcut.
 * @author Robert Swarr
 */
public abstract class PanelListBean {
 
    private List<PanelListItem> panelListItems;
    
    /**
     * default no argument constructor
     */
    public PanelListBean() {
        super();
    }
    
    /**
     * Accessor method for the panelListItems
     * @return List<PanelListItem> panelListItem.
     */
    public List<PanelListItem> getPanelListItems() {
        if (panelListItems == null) {
            panelListItems = createPanelListItems();
        }
        return panelListItems;
    }
    
    /**
     * Mutator method for the panelListItems property.
     * @param panelListItems The panelListItem to set.
     */
    public void setPanelListItems(List<PanelListItem> panelListItems) {
        this.panelListItems = panelListItems;
    }
    
    /**
     * Abstract method that is implemented by all child classes.
     * @return List<PanelListItem>
     */
    public abstract List<PanelListItem> createPanelListItems();
}
