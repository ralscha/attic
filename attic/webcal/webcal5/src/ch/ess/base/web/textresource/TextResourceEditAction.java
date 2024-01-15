package ch.ess.base.web.textresource;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.MessageResources;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.model.TextResource;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.xml.textresource.Resource;
import ch.ess.base.xml.textresource.TextResources;
import ch.ess.base.xml.textresource.Variable;

import com.cc.framework.adapter.struts.ActionContext;

@Role("$admin")
public class TextResourceEditAction extends AbstractEditAction<TextResource> {

  private TextResources textResources;
  private TranslationService translationService;

  public void setTextResources(final TextResources textResources) {
    this.textResources = textResources;
  }


  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public void formToModel(final ActionContext ctx, TextResource textResource) {
    
    TextResourceForm textResourceForm = (TextResourceForm)ctx.form();

    translationService.addTranslation(textResource, textResourceForm.getEntry());
    
  }

  @Override
  public void modelToForm(final ActionContext ctx, final TextResource textResource) {
    TextResourceForm textResourceForm = (TextResourceForm)ctx.form();

    MessageResources messages = getResources(ctx.request());
    Locale locale = getLocale(ctx.request());

    textResourceForm.setEntry(translationService.getNameEntry(messages, locale, textResource));

    Map<String, String> variables = new TreeMap<String, String>();

    Resource res = textResources.getResources().get(textResource.getName());

    textResourceForm.setName(res.getDescription().get(locale));

    List<Variable> list = res.getVariableList();
    if (list != null) {
      for (Variable var : list) {
        variables.put(var.getName(), var.getDescription().get(locale));
      }
    }

    textResourceForm.setVariables(variables);

  }
}
