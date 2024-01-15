package ar.com.koalas.providers.taglibs.struts;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import ar.com.koalas.providers.Provider;
import ar.com.koalas.providers.ProviderFactory;
import ar.com.koalas.providers.taglibs.comboselect.ComboSelectTag;
import ar.com.koalas.providers.taglibs.comboselect.SelectData;

/**
 * Genera los Options necesarios para un combo Select
 * @author gmeyer
 */
public class OptionsTag extends org.apache.struts.taglib.html.OptionsTag {
  private String provider = null;
  Provider providerImpl = null;

  class OptionsPredicate implements Predicate {
    @SuppressWarnings("synthetic-access")
    public boolean evaluate(Object arg) {
      Object context = OptionsTag.this.pageContext.getRequest();
      return OptionsTag.this.providerImpl.evaluateFilter(arg, context);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Iterator getIterator(String arg0, String arg1) throws JspException {

    if (this.getProvider() == null)
      return super.getIterator(arg0, arg1);

    try {
      ProviderFactory factory = ProviderFactory.getInstance();
      this.providerImpl = factory.getProvider(this.pageContext, this.getProvider());
      this.property = this.providerImpl.getKeyName();
      this.labelProperty = this.providerImpl.getDescription();
      Collection filteredCollection = CollectionUtils.select(this.providerImpl.getCollection(), new OptionsPredicate());
      return filteredCollection.iterator();
    } catch (Exception e) {
      e.printStackTrace();
      throw new JspException("Error en el provider " + this.getProvider() + ". : " + e.getMessage());
    }
  }

  /**
   * @return
   */
  public String getProvider() {
    return provider;
  }

  /**
   * @param string
   */
  public void setProvider(String string) {
    provider = string;
    //for options tag to work properly
    this.collection = string;
  }

  @Override
  public void release() {
    super.release();
    this.provider = null;
    this.providerImpl = null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public int doEndTag() throws JspException {
    //si esta anidado en un comboselect, le notifica el contenido y skipea 
    SelectTag st = (SelectTag)findAncestorWithClass(this, SelectTag.class);
    ComboSelectTag cst = (ComboSelectTag)findAncestorWithClass(this, ComboSelectTag.class);
    if (cst != null && st != null) {
      Iterator it = null;
      if (this.collection != null) {
        it = getIterator(this.collection, null);
      } else {
        it = getIterator(this.name, this.property);
      }
      cst.putSelectData(st.getProperty(), new SelectData(it, this.property, this.labelProperty));
      return EVAL_PAGE;
    }

    return super.doEndTag();
  }
}
