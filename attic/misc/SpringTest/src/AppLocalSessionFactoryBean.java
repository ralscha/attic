import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * @author sr
 * @version $Revision: 1.7 $ $Date: 2005/05/04 09:15:42 $
 */
public class AppLocalSessionFactoryBean extends LocalSessionFactoryBean {

  private Class[] annotatedClasses;
  private String[] annotatedPackages;

  public void setAnnotatedClasses(Class annotatedClasses[]) {
    this.annotatedClasses = annotatedClasses;
  }

  public void setAnnotatedPackages(String[] annotatedPackages) {
    this.annotatedPackages = annotatedPackages;
  }

  @Override
  protected Configuration newConfiguration() throws HibernateException {
    AnnotationConfiguration config = new AnnotationConfiguration();
    return config;
  }

  @Override
  protected void postProcessConfiguration(final Configuration config) throws HibernateException {

    super.postProcessConfiguration(config);

    if (this.annotatedPackages != null) {
      for (int i = 0; i < this.annotatedPackages.length; i++) {
        ((AnnotationConfiguration)config).addPackage(annotatedPackages[i]);
      }
    }

    if (this.annotatedClasses != null) {
      for (Class annClass : this.annotatedClasses) {
        ((AnnotationConfiguration)config).addAnnotatedClass(annClass);
      }
    }

  }

}
