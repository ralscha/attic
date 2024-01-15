package ch.ess.base.web.tag;

import javax.servlet.jsp.tagext.TagSupport;

import ch.ess.base.Constants;

public class HasFeatureTag extends TagSupport {

  private String feature;

  public String getFeature() {
    return feature;
  }

  public void setFeature(String feature) {
    this.feature = feature;
  }

  @Override
  public int doStartTag() {
    if (Constants.hasFeatures()) {
      if (Constants.hasFeature(getFeature())) {
        return EVAL_BODY_INCLUDE;
      }
    }
    
    return SKIP_BODY;
  }

}
