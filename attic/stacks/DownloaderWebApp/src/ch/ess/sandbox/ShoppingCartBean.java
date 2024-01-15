package ch.ess.sandbox;

import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

@Name("cart")
@Scope(ScopeType.SESSION)
public class ShoppingCartBean {
  @Out
  private List<Product> contents = new ArrayList<Product>();
  
  public List<Product> getContents() {
    return contents;
  }
  
  public void addToCard(Product product) {
    contents.add(product);
  }
  
  
  @Destroy
  public void destroy() {
    System.out.println("DESTROY");
  }
}
