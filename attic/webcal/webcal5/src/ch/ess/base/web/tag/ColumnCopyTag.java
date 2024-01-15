package ch.ess.base.web.tag;

import ch.ess.base.web.WebConstants;

import com.cc.framework.taglib.controls.ColumnBaseTag;
import com.cc.framework.ui.model.ColumnDesignModel;
import com.cc.framework.ui.model.imp.ColumnCommandDesignModelImp;

public class ColumnCopyTag extends ColumnBaseTag {

  @Override
  public ColumnDesignModel doCreateDesignModel() {
    return new ColumnCommandDesignModelImp(WebConstants.ACTION_COPY, "ico.copy", "40");
  }

}
