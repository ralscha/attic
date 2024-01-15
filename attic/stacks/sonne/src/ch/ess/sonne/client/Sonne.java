package ch.ess.sonne.client;

import java.util.List;

import ch.ess.sonne.client.common.DateItem;
import ch.ess.sonne.client.common.ImageInfo;
import ch.ess.sonne.client.common.PictureService;
import ch.ess.sonne.client.common.PictureServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sonne extends MouseListenerAdapter implements EntryPoint {

  // Statically configure RPC service
  private static PictureServiceAsync ws = (PictureServiceAsync)GWT.create(PictureService.class);

  static {
    ((ServiceDefTarget)ws).setServiceEntryPoint("ws");
  }

  public void onModuleLoad() {

    ws.getDateItemList(new AsyncCallback() {
      public void onSuccess(Object result) {
        final List dateItemList = (List)result;
        final ListBox lb = new ListBox();
        for (int i = 0; i < dateItemList.size(); i++) {
          lb.addItem(((DateItem)dateItemList.get(i)).getShowItemName());
        }
        lb.setVisibleItemCount(1);

        DateItem di = (DateItem)dateItemList.get(0);
        showPreviewImages(di.getFileNames());

        lb.addChangeListener(new ChangeListener() {
          public void onChange(Widget sender) {
            int selectedItem = lb.getSelectedIndex();

            RootPanel.get("imageDiv").clear();
            DateItem di1 = (DateItem)dateItemList.get(selectedItem);
            showPreviewImages(di1.getFileNames());
          }
        });

        HorizontalPanel hp = new HorizontalPanel();
        Label lbLabel = new Label("Datum: ");
        lb.setStyleName("lb");
        lbLabel.setStyleName("lb");
        hp.add(lbLabel);
        hp.add(lb);
        RootPanel.get("dateSelect").add(hp);

      }

      public void onFailure(Throwable caught) {
        Window.alert("Error: " + caught.getMessage());
      }
    });

  }

  public void onMouseEnter(Widget sender) {
    Image senderImage = (Image)sender;
    Image img = new Image(senderImage.getUrl());
    senderImage.addStyleName("selected");
    RootPanel.get("imageBig").clear();
    RootPanel.get("imageBig").add(img);
    
    Label dateTimeLabel = new Label(DOM.getAttribute(senderImage.getElement(), "datetime"));
    RootPanel.get("imageDateTime").clear();
    RootPanel.get("imageDateTime").add(dateTimeLabel);
  }

  public void onMouseLeave(Widget sender) {
    RootPanel.get("imageBig").clear();
    RootPanel.get("imageDateTime").clear();
    Image senderImage = (Image)sender;
    senderImage.removeStyleName("selected");
  }

  void showPreviewImages(ImageInfo[] fileNames) {

    HorizontalPanel hp = new HorizontalPanel();
    for (int i = 0; i < fileNames.length; i++) {
      Image image = new Image("img/" + fileNames[i].getFileName());      
      image.setStyleName("small");
      
      DOM.setAttribute(image.getElement(), "datetime", fileNames[i].getDateTime());
      hp.add(image);
      image.addMouseListener(this);
    }


    RootPanel.get("imageDiv").add(hp);

  }
}
