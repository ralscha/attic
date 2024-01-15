package ch.ralscha.springwicket.web;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;

public class AnchoredBookmarkablePageLink extends BookmarkablePageLink<String> {

  private static final long serialVersionUID = 1L;

  private IModel<String> stringAnchor;

  public AnchoredBookmarkablePageLink(String id, Class<? extends WebPage> pageClass, IModel<String> anchor) {
    super(id, pageClass);
    this.stringAnchor = anchor;
  }

  public AnchoredBookmarkablePageLink(String id, Class<? extends WebPage> pageClass, PageParameters params, IModel<String> anchor) {
    super(id, pageClass, params);
    this.stringAnchor = anchor;
  }

  @Override
  protected CharSequence appendAnchor(ComponentTag tag, CharSequence url) {
    url = url + "#" + stringAnchor.getObject().toString();
    return url;
  }

}