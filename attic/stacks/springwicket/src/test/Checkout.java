package test;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class Checkout extends CheesrPage {

  public Checkout() {

    add(new FeedbackPanel("feedback"));
    
    Form<Object> form = new Form<Object>("form");
    add(form);

    Address address = getCart().getBillingAddress();

    TextField<String> nameTf = new TextField<String>("name", new PropertyModel<String>(address, "name"));
    nameTf.setRequired(true);
    nameTf.add(StringValidator.lengthBetween(5, 32));    
    form.add(nameTf);
    
    TextField<String> streetTf = new TextField<String>("street", new PropertyModel<String>(address, "street"));
    streetTf.setRequired(true);
    form.add(streetTf);
    
    TextField<Integer> zipcodeTf = new TextField<Integer>("zipcode", new PropertyModel<Integer>(address, "zipcode"));
    zipcodeTf.setRequired(true);
    form.add(zipcodeTf);
    
    TextField<String> cityTf = new TextField<String>("city", new PropertyModel<String>(address, "city"));
    cityTf.setRequired(true);
    form.add(cityTf);

    form.add(new Link<String>("cancel") {

      @Override
      public void onClick() {
        setResponsePage(Index.class);
      }
    });

    form.add(new Button("order") {

      @Override
      public void onSubmit() {
        Cart cart = getCart();
        cart.getCheeses().clear();
        setResponsePage(Index.class);
      }
    }

    );

    add(new ShoppingCartPanel("shoppingcart", getCart()).setOutputMarkupId(true));
    
  }
}
