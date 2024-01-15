package ch.ess.sandbox;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.bpm.Actor;

@Name("search")
@Scope(ScopeType.CONVERSATION)
public class SearchAction {

  static final int PAGE_SIZE = 10;

  int currentPage = 0;
  boolean hasMore = false;

  @In
  private Session hibernateSession;

  @DataModel
  private List<Product> products;
  
  @DataModelSelection
  private Product selectedProduct;

  @In(create=true)
  private ShoppingCartBean cart;
  
  public String select() {
    cart.addToCard(selectedProduct);
    return null;
  }
  
  @Out(scope=ScopeType.BUSINESS_PROCESS, required=false)
  private String email;
    
  @SuppressWarnings("unchecked")
  @Begin(join = true)
  @Factory("products")
  public void loadProducts() {
    

    
    Criteria criteria = hibernateSession.createCriteria(Product.class);
    criteria.setMaxResults(PAGE_SIZE + 1);
    criteria.setFirstResult(PAGE_SIZE * currentPage);

    List<Product> items = criteria.list();

    if (items.size() > PAGE_SIZE) {
      products = new ArrayList<Product>(items.subList(0, PAGE_SIZE));
      hasMore = true;
    } else {
      products = items;
      hasMore = false;
    }
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public boolean isFirstPage() {
    return currentPage == 0;
  }

  public boolean isLastPage() {
    return !hasMore;
  }

  public String nextPage() {
    if (!isLastPage()) {
      currentPage++;
      loadProducts();
    }
    return null;
  }

  public String prevPage() {
    if (!isFirstPage()) {
      currentPage--;
      loadProducts();
    }
    return null;
  }
  
  @CreateProcess(definition="OrderManagement")
  public String purchase() {
    System.out.println("purchase");
    email = "sr@ess.ch";

    return null;
  }

}
