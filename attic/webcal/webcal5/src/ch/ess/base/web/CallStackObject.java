package ch.ess.base.web;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



/**
 * @author sk
 *
 */
public class CallStackObject {
  private String path;
  private String id;
  private CrumbInfo crumbInfo;

  public String getPath() {
    return path;
  }

  public void setPath(String forward) {
    this.path = forward;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  @Override
  public boolean equals(Object obj) {
    CallStackObject other = (CallStackObject)obj;
    if (path != null) {
      return path.equals(other.getPath());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getPath().hashCode();
  }

  public CrumbInfo getCrumbInfo() {
    return crumbInfo;
  }

  public void setCrumbInfo(CrumbInfo crumbInfo) {
    this.crumbInfo = crumbInfo;
  }

  @Override
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
    builder.append(path).append(id);
    return builder.toString();
  }



}
