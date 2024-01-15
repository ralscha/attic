package ch.ess.base.web.applink;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.web.AbstractForm;

public class AppLinkForm extends AbstractForm {

  private String linkName;
  private String appLink;
  private String[] permissionIds;

  @Override
  public void reset(final ActionMapping mapping, final HttpServletRequest request) {
    permissionIds = null;
  }

  @ValidatorField(key="applink.linkname", required=true)  
  public void setLinkName(final String linkName) {
    this.linkName = linkName;
  }

  public String getLinkName() {
    return linkName;
  }

  public String[] getPermissionIds() {
    return (String[])ArrayUtils.clone(permissionIds);
  }

  public void setPermissionIds(final String[] permissionId) {
    this.permissionIds = (String[])ArrayUtils.clone(permissionId);
  }

  @ValidatorField(key="applink.applink", required=true) 
	public void setAppLink(String appLink) {
		this.appLink = appLink;
	}

	public String getAppLink() {
		return appLink;
	}

}
