package ch.ralscha.springwicket.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import ch.ralscha.springwicket.domain.Lokationen;

public class Tables extends WebPage {

  //@SpringBean(name = "lokationenService")
  //private transient LokationenService lokationenService;

  public Tables() {

    //List<Lokationen> lokationen = lokationenService.findByCriteria(Order.asc("name"));

    ListView<Lokationen> articles = new ListView<Lokationen>("lokationen") {

      @Override
      protected void populateItem(ListItem<Lokationen> item) {
        CompoundPropertyModel<Lokationen> compoundPropertyModel = new CompoundPropertyModel<Lokationen>(item.getModelObject());
        item.setModel(compoundPropertyModel);
        item.add(new AnchoredBookmarkablePageLink("link", Tables.class, new Model<String>() {

          @Override
          public String getObject() {
            return "TEST";
          }
        }));
      }
//      
//      
//      @Override
//      protected void populateItem(ListItem item) {
//        final CompoundPropertyModel compoundPropertyModel = new CompoundPropertyModel(
//            item.getModelObject());
//        item.setModel(compoundPropertyModel);
//        item.add(new Label("title"));
//        item.add(new Link("edit", new Model("Edit")) {
//          @Override
//          public void onClick() {
//            setResponsePage(new EditArticlePage(
//                (Article) compoundPropertyModel.getObject(),
//                AuthorOverviewPage.this));
//
//          }
//        });
//        item.add(new Link("delete", new Model("Delete")) {
//          @Override
//          public void onClick() {
//            // This removes reference to the article from the blog
//            Article article = (Article) compoundPropertyModel
//                .getObject();
//            author.getBlog().getArticles().remove(article);
//            // to actually delete the article we also need to do
//            // this:
////            dao.remove(article);
//
//          }
//        });

    };
    add(articles);

  }
}
