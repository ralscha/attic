package test;

import java.text.NumberFormat;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class ShoppingCartPanel extends Panel {

  private Cart cart;

  public ShoppingCartPanel(String id, Cart cart) {
    super(id);
    this.cart = cart;
    add(new ListView<Cheese>("cart", new PropertyModel<List<Cheese>>(this, "cart.cheeses")) {

      @Override
      protected void populateItem(ListItem<Cheese> item) {
        Cheese cheese = item.getModelObject();
        item.add(new Label("name", cheese.getName()));
        item.add(new Label("price", "$" + cheese.getPrice()));

        //item.add(removeLink("remove", item));

        item.add(new AjaxFallbackLink<Cheese>("remove", item.getModel()) {

          @Override
          public void onClick(AjaxRequestTarget target) {
            Cheese selected = getModelObject();
            getCart().getCheeses().remove(selected);

            if (target != null) {
              target.addComponent(ShoppingCartPanel.this);
            }
          }

        });

      }
    });
    add(new Label("total", new Model<String>() {

      @Override
      public String getObject() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(getCart().getTotal());
      }
    }));
  }

  Cart getCart() {
    return cart;
  }
}
