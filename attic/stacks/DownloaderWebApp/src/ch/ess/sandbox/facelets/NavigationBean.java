package ch.ess.sandbox.facelets;

// J2SE imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

/**
 * JSF backing bean for the left-nav.xhtml view. 
 * @author Robert Swarr
 */
@Name("navigationBean")
public class NavigationBean {

  private Map<String, String> siteMap;
  private Map<String, String> viewMap;

  @Logger
  private Log log;

  @In
  private FacesContext facesContext;

  public NavigationBean() {
    siteMap = new HashMap<String, String>();
    siteMap.put("homepage", "HOME");
    siteMap.put("searchRecipePage", "SEARCH_RECIPES");
    siteMap.put("communityPage", "COMMUNITY");
    siteMap.put("newsFeedPage", "VIEW_NEWSFEEDS");
    siteMap.put("notImplementPage", "NOT_IMPLEMENTED");

    viewMap = new HashMap<String, String>();
    viewMap.put("/homepage.xhtml", "searchRecipePage,communityPage,newsFeedPage");
    viewMap.put("/homepage_chp3.xhtml", "searchRecipePage,communityPage,newsFeedPage");
    viewMap.put("/homepage_chp4.xhtml", "searchRecipePage,communityPage,newsFeedPage");

  }

  /**
   * Retrieve a list of all Navigation Links.
   * A ui:repeat statement in the left-nav view 
   * retrieves this List with a value binding.
   * @return List<SiteLinkType>
   */
  public List<SiteLinkType> getNavigationLinks() {
    log.debug("<<< entered getNavigationLinks() >>>");

    List<SiteLinkType> siteLinkList = new ArrayList<SiteLinkType>();
    String currentViewId = facesContext.getViewRoot().getViewId();
    log.debug("<<< in getNavigationLinks(), current viewId: " + currentViewId);
    if (getViewMap().get(currentViewId) != null) {
      String[] page = (getViewMap().get(facesContext.getViewRoot().getViewId())).split(",");
      for (int i = 0; i < page.length; i++) {
        String linkName = getSiteMap().get(page[i]);
        SiteLinkType siteLinkType = SiteLinkType.getSiteLink(linkName);
        siteLinkList.add(siteLinkType);
      }
    } else {
      siteLinkList.add(SiteLinkType.NOT_IMPLEMENTED);
    }

    log.debug("<<< exiting getNavigationLinks() >>>");
    return siteLinkList;
  }

  /**
   * Accessor method for the siteMap property.
   * This HashMap is declared in the faces-config.xml file
   * and passed to the bean as a managed property.
   * @return HashMap siteMap.
   */
  public Map<String, String> getSiteMap() {
    log.debug("<<< entered getSiteMap() >>>");
    return siteMap;
  }

  /**
   * Mutator method for the siteMap property.
   * This HashMap is declared in the faces-config.xml file
   * and passed to the bean as a managed property.
   * @param siteMap HashMap The siteMap to set.
   */
  public void setSiteMap(HashMap<String, String> siteMap) {
    log.debug("<<< entered setSiteMap() >>>");
    this.siteMap = siteMap;
    log.debug("<<< viewMap: " + siteMap + " >>>");
  }

  /**
   * Accessor method for the viewMap property.
   * This HashMap is declared in the faces-config.xml file
   * and passed to the bean as a managed property.
   * @return HashMap viewMap.
   */
  public Map<String, String> getViewMap() {
    log.debug("<<< entered getViewMap() >>>");
    return viewMap;
  }

  /**
   * Mutator method for the viewMap property.
   * This HashMap is declared in the faces-config.xml file
   * and passed to the bean as a managed property.
   * @param viewMap HashMap
   */
  public void setViewMap(Map<String, String> viewMap) {
    log.debug("<<< entered setViewMap() >>>");
    this.viewMap = viewMap;
    log.debug("<<< viewMap: " + viewMap + " >>>");
  }
}
