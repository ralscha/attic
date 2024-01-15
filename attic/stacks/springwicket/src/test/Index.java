package test;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

public class Index extends CheesrPage {

  ShoppingCartPanel shoppingcart;
  Link<Void> checkoutLink;
  
  public Index() {

    PageableListView<Cheese> cheeses = new PageableListView<Cheese>("cheeses", getCheeses(), 2) {

      @Override
      protected void populateItem(ListItem<Cheese> item) {

        Cheese cheese = item.getModelObject();

        item.add(new Label("name", cheese.getName()));
        item.add(new Label("description", cheese.getDescription()));
        item.add(new Label("price", "$" + cheese.getPrice()));

        item.add(new AjaxFallbackLink<Cheese>("add", item.getModel()) {

          @Override
          public void onClick(AjaxRequestTarget target) {
            Cheese selected = getModelObject();
            getCart().getCheeses().add(selected);
            
            if (target != null) {
              target.addComponent(shoppingcart);
              target.addComponent(checkoutLink);
            }
          }
        });

      }

    };

    add(cheeses);
    add(new PagingNavigator("navigation", cheeses));

    shoppingcart = new ShoppingCartPanel("shoppingcart", getCart());
    shoppingcart.setOutputMarkupId(true);
    add(shoppingcart);

    checkoutLink = new Link<Void>("checkout") {

      @Override
      public void onClick() {
        setResponsePage(new Checkout());
      }

      @Override
      public boolean isVisible() {
        return !getCart().getCheeses().isEmpty();
      }
    };
    checkoutLink.setOutputMarkupId(true);
    checkoutLink.setOutputMarkupPlaceholderTag(true);
    add(checkoutLink);
  }
}
