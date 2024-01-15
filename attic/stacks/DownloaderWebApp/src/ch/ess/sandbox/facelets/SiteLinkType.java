package ch.ess.sandbox.facelets;

import java.util.List;
import java.util.ArrayList;
import java.util.EnumSet;


public enum SiteLinkType {
    HOME,
    SEARCH_RECIPES,
    COMMUNITY,
    VIEW_NEWSFEEDS,
    NOT_IMPLEMENTED;
    
    private static List<SiteLinkType> siteLinkList 
        = new ArrayList<SiteLinkType>(EnumSet.allOf(SiteLinkType.class));
    
    public String getLinkName() {
        String linkTitle = null;
        switch (this) {
            case HOME:
                linkTitle = "Home";
                break;
            case SEARCH_RECIPES:
                linkTitle = "Search RecipeBase";
                break;
            case COMMUNITY:
                linkTitle = "Food Appreciation Community";
                break;
            case VIEW_NEWSFEEDS:
                linkTitle = "Food NewsFeeds";
                break;
            default:
                linkTitle = "Not Implemented";
        }
        return linkTitle;
    }
    
    
    public String  getLinkAction() {
        String action = null;
        switch (this) {
            case HOME:
                action = "home";
                break;
            case SEARCH_RECIPES:
                action = "searchRecipes";
                break;
            case COMMUNITY:
                action = "community";
                break;
            case VIEW_NEWSFEEDS:
                action = "newsfeeds";
                break;
            default:
                action = "notImplemented";
        }
        return action;
    }
    
    public static List<SiteLinkType> listOf() {
        return siteLinkList;
    }
    
    public static SiteLinkType getSiteLink(String linkName) {
        SiteLinkType returnSiteLinkType = null;
        for (SiteLinkType aSiteLinkType: listOf()) {
            if (aSiteLinkType.name().equalsIgnoreCase(linkName)) {
                returnSiteLinkType = aSiteLinkType;
            }
        }
        return returnSiteLinkType != null ?  returnSiteLinkType : NOT_IMPLEMENTED;
    }
    
}
