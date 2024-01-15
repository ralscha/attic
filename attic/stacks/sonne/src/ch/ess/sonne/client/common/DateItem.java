package ch.ess.sonne.client.common;

import com.google.gwt.user.client.rpc.IsSerializable;


public class DateItem implements IsSerializable  {
  private ImageInfo[] fileNames;
  private String showItemName;

  public DateItem() {
    //no action here
  }
  
  public DateItem(ImageInfo[] fileNames, String showItemName) {
    this.fileNames = fileNames;
    this.showItemName = showItemName;
  }

  public ImageInfo[] getFileNames() {
    return fileNames;
  }

  public String getShowItemName() {
    return showItemName;
  }


}
