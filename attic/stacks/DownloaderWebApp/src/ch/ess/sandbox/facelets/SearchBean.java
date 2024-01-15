package ch.ess.sandbox.facelets;

// JSE imports
import java.util.ArrayList;
import java.util.List;
// JSF imports
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import org.jboss.seam.annotations.Name;

/**
 * JSF backing bean for the search page implementation
 * of a panel-list composition component. 
 * Example 6-10 in the Faces Shortcut.
 * @author Robert Swarr
 *
 */
@Name("searchBean")
public class SearchBean extends PanelListBean {
    
    private String recipeName;
    private String foodGroup;
    private String ingredient;
    
    /**
     * default no argument constructor
     */
    public SearchBean() {
        super();
    }
    
    /**
     * Action method bound to the search button of the panel-list 
     * component.
     * @return String action
     */
    public String searchButtonAction() {
        return "";
    }
    
    /**
     * Accessor method for the foodGroup property.
     * @return String foodGroup
     */
    public String getFoodGroup() {
        return foodGroup;
    }

    /**
     * Mutator method for the foodGroup property.
     * @param foodGroup The foodGroup to set.
     */
    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    /**
     * Accesssor method for the ingredient property.
     * @return Returns the ingredient.
     */
    public String getIngredient() {
        return ingredient;
    }

    /**
     * Mutator method for the ingredient property.
     * @param ingredient The ingredient to set.
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Accessor method for the recipeName property.
     * @return Returns the recipeName.
     */
    public String getRecipeName() {
        return recipeName;
    }

    /**
     * Mutator method for the recipeName property.
     * @param recipeName The recipeName to set.
     */
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    /**
     * Method required for a class that extends the PanelListBean class.
     */
    @Override
    public List<PanelListItem> createPanelListItems() {
        System.out.println("<<< executing createPanelListItems() >>>");
        List<PanelListItem> items = new ArrayList<PanelListItem>();
        // first item is heading
        PanelListItem item = new PanelListItem();
        item.setType(HtmlOutputText.COMPONENT_TYPE);
        item.setText("Enter Recipe Search Criteria");
        items.add(item);
        // second item is recipe name
        item = new PanelListItem();
        item.setType(HtmlInputText.COMPONENT_TYPE);
        item.setText("Recipe Name:");
        item.setName("recipeName");
        items.add(item);
        // third item food group
        item = new PanelListItem();
        item.setType(HtmlInputText.COMPONENT_TYPE);
        item.setText("Food Group:");
        item.setName("foodGroup");
        items.add(item);
        // fourth item is ingredient
        item = new PanelListItem();
        item.setType(HtmlInputText.COMPONENT_TYPE);
        item.setText("Ingredient:");
        item.setName("ingredient");
        items.add(item);
        // fourth item is a command button
        item = new PanelListItem();
        item.setType(HtmlCommandButton.COMPONENT_TYPE);
        item.setText("Search");
        item.setAction("searchButtonAction");
        items.add(item);
        System.out.println("<<< exiting createPanelListItems() >>>");
        return items;
    }
}
