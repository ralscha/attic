/*
 * Created on Jul 10, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ar.com.koalas.providers;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author DZ156H
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ProviderFactory {
  private static ProviderFactory factory = null;
  private static Map<String, Provider> providers = new Hashtable<String, Provider>();
  public static final String PROVIDERS_CONFIG_FILE = "/providers-config.xml";
  public static final String PROVIDERS_CONFIG_FILE_SYSTEM_PARAM = "ar.com.koalas.providers.ProvidersConfigurationFile";

  public static ProviderFactory getInstance() throws IOException, SAXException {
    if (ProviderFactory.factory == null) {
      ProviderFactory.factory = new ProviderFactory();
    }

    return ProviderFactory.factory;
  }

  private ProviderFactory() throws IOException, SAXException {
    Digester digester = new Digester();
    digester.push(this);

    AbstractObjectCreationFactory factory1 = new AbstractObjectCreationFactory() {
      @Override
      public Object createObject(Attributes arg) {
        String str = arg.getValue("className");
        try {
          return Class.forName(str).newInstance();
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
      }
    };

    digester.addFactoryCreate("providers/provider", factory1);//new ProviderFactory() ); //new SkinFactory(xkLoading));
    digester.addSetProperties("providers/provider");
    digester.addSetProperty("providers/provider/set-property", "property", "value");
    digester.addCallMethod("providers/provider/param", "addParam", 2);
    digester.addCallParam("providers/provider/param/param-name", 0);
    digester.addCallParam("providers/provider/param/param-value", 1);

    digester.addFactoryCreate("providers/provider/value", factory1);//digester.addObjectCreate("providers/provider/value", BasicBean.class.getName() );
    digester.addSetProperties("providers/provider/value");
    digester.addSetNext("providers/provider/value", "addBean");

    digester.addFactoryCreate("providers/provider/filter", factory1);//digester.addObjectCreate("providers/provider/filter", Filter.class.getName() );
    digester.addSetProperties("providers/provider/filter");
    digester.addSetNext("providers/provider/filter", "addFilter");

    digester.addSetNext("providers/provider", "addProvider");
    String strConfigFile = PROVIDERS_CONFIG_FILE;
    if (System.getProperty(PROVIDERS_CONFIG_FILE_SYSTEM_PARAM) != null)
      strConfigFile = System.getProperty(PROVIDERS_CONFIG_FILE_SYSTEM_PARAM);

    URL configFile = this.getClass().getResource(strConfigFile);
    digester.parse(configFile.openStream());
  }

  public void addProvider(Provider provider) {
    ProviderFactory.providers.put(provider.getName(), provider);
  }

  public Provider getProvider(String providerName) {
    return this.getProvider(null, providerName);
  }

  public Provider getProvider(PageContext pageContext, String providerName) {
    Provider prv = ProviderFactory.providers.get(providerName);
    if (pageContext != null && prv instanceof JSPProvider) {
      ((JSPProvider)prv).setPageContext(pageContext);
    }
    return prv;
  }
}