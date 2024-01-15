package ch.ess.ex4u.client.data;

import ch.ess.ex4u.client.PanelFactory;
import com.smartgwt.client.widgets.tree.TreeNode;

public class NavigationTreeNode extends TreeNode {

  public NavigationTreeNode(String name, String nodeID, String parentNodeID, String icon, PanelFactory factory, boolean enabled,
      String idSuffix) {
    if (enabled) {
      setName(name);
    } else {
      setName("<span style='color:808080'>" + name + "</span>");
    }
    setNodeID(nodeID.replace("-", "_") + idSuffix);
    setThumbnail("thumbnails/" + nodeID.replace("-", "_") + ".gif");
    setParentNodeID(parentNodeID.replace("-", "_") + idSuffix);
    setIcon(icon);

    setFactory(factory);

    if (nodeID.equals("featured-category")) {
      setIsOpen(true);
    }
  }

  public void setFactory(PanelFactory factory) {
    setAttribute("factory", factory);
  }

  public PanelFactory getFactory() {
    return (PanelFactory)getAttributeAsObject("factory");
  }

  public void setNodeID(String value) {
    setAttribute("nodeID", value);
  }

  public String getNodeID() {
    return getAttribute("nodeID");
  }

  public void setParentNodeID(String value) {
    setAttribute("parentNodeID", value);
  }

  @Override
  public void setName(String name) {
    setAttribute("name", name);
  }

  @Override
  public String getName() {
    return getAttributeAsString("name");
  }

  @Override
  public void setIcon(String icon) {
    setAttribute("icon", icon);
  }

  @Override
  public String getIcon() {
    return getAttributeAsString("icon");
  }

  public void setThumbnail(String thumbnail) {
    setAttribute("thumbnail", thumbnail);
  }

  public String getThumbnail() {
    return getAttributeAsString("thumbnail");
  }

  public void setIsOpen(boolean isOpen) {
    setAttribute("isOpen", isOpen);
  }

  public void setIconSrc(String iconSrc) {
    setAttribute("iconSrc", iconSrc);
  }

  public String getIconSrc() {
    return getAttributeAsString("iconSrc");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    NavigationTreeNode that = (NavigationTreeNode)o;

    if (!getName().equals(that.getName()))
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }
}
