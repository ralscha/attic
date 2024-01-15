package ch.ess.cal.web.task;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.base.model.Document;
import ch.ess.base.web.AbstractFormListControl;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.model.Attachment;
import ch.ess.cal.model.BaseEvent;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

public class AttachmentFormListControl extends AbstractFormListControl<BaseEvent, Attachment> {

  @Override
  public void newRowItem(ActionContext ctx, DynaBean dynaBean, Attachment attachment) {
    dynaBean.set("fileName", attachment.getDocument().getFileName());
    dynaBean.set("fileSize", attachment.getDocument().getContentSize());
  }

  @Override
  protected Attachment newModelItem(DynaBean dynaBean, BaseEvent event, Map<String, Object> parameters) {
    Attachment attachment = new Attachment();
    attachment.setEvent(event);
    Document doc = (Document)dynaBean.get("document");
    attachment.setDocument(doc);
    return attachment;
  }

  public void add(final FormActionContext ctx) throws IOException {

    TaskForm taskForm = (TaskForm)ctx.form();
    if ((taskForm.getAttachmentFormFile() != null) && (taskForm.getAttachmentFormFile().getFileSize() > 0)) {

      SimpleListDataModel dataModel = (SimpleListDataModel)getDataModel();

      int max = getMaxIndex(dataModel);

      DynaBean dynaBean = new LazyDynaBean();
      Document document = new Document(taskForm.getAttachmentFormFile());
      dynaBean.set("id", String.valueOf(max + 1));
      dynaBean.set("document", document);
      dynaBean.set("fileName", document.getFileName());
      dynaBean.set("fileSize", document.getContentSize());
      dynaBean.set("dbId", null);

      dataModel.add(dynaBean);

      taskForm.getAttachmentFormFile().destroy();
    }
    ctx.forwardToInput();
  }
}
