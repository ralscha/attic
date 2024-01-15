package ch.ess.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

/** A business entity class representing a TextResource
 * 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:19 $
 * 
 */

@Entity
@Proxy(lazy = false)
public class TextResource extends TranslateObject {

  private String name;

  @Column(length = 100, nullable = false, unique = true)
  public String getName() {
    return name;
  }

  public void setName(final String string) {
    name = string;
  }


}