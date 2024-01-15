package ch.ess.cal.web.textresource;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.MessageResources;

import ch.ess.cal.model.TextResource;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractEditAction;
import ch.ess.cal.xml.textresource.Resource;
import ch.ess.cal.xml.textresource.TextResources;
import ch.ess.cal.xml.textresource.Variable;

import com.cc.framework.adapter.struts.ActionContext;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $ 
 * 
 * @struts.action path="/editTextResource" name="textResourceForm" input="/textresourceedit.jsp" scope="session" validate="false" roles="$textresource" 
 * @struts.action-forward name="back" path="/textresourcelist.jsp" redirect="true"
 * 
 * @spring.bean name="/editTextResource" lazy-init="true" 
 * @spring.property name="dao" reflocal="textResourceDao"
 * 
 **/
public class TextResourceEditAction extends AbstractEditAction<TextResource> {

  private TextResources textResources;
  private TranslationService translationService;

  /**    
   * @spring.property reflocal="textResources"
   */
  public void setTextResources(final TextResources textResources) {
    this.textResources = textResources;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public TextResource formToModel(final ActionContext ctx, TextResource textResource) {
    
    if (textResource == null) {
      textResource = new TextResource();
    }
    
    TextResourceForm textResourceForm = (TextResourceForm)ctx.form();

    translationService.addTranslation(textResource, textResourceForm.getEntry());
    
    return textResource;
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
