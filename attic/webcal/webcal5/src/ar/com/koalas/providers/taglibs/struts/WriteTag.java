package ar.com.koalas.providers.taglibs.struts;

import javax.servlet.jsp.JspException;

import ar.com.koalas.providers.Provider;
import ar.com.koalas.providers.ProviderFactory;

/**
 * Tag para mostrar descripciones dada la clave.
 * @author Guille
 */
public class WriteTag extends org.apache.struts.taglib.bean.WriteTag {
  private String provider = null;
  private Provider providerImpl = null;

  @Override
  protected String formatValue(Object valueToFormat) throws JspException {
    try {
      ProviderFactory factory = ProviderFactory.getInstance();
      this.providerImpl = factory.getProvider(this.pageContext, this.getProvider());
      Object obj = this.providerImpl.getDescription(valueToFormat);
      if (obj == null)
        obj = valueToFormat;

      return super.formatValue(obj);
    } catch (Exception e) {
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
  }

  @Override
  public void release() {
    super.release();
    this.provider = null;
    this.providerImpl = null;
  }
}
