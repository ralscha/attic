

import java.net.URL;
import java.util.Date;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Bid implements Serializable {
   private String fId;
   private String fDesc;
   private URL fImage;
   private Date fTimeOfBid = new Date();
   private double fCurrBid;
   private transient ImageIcon fIcon;
   
   public Bid(String id, String desc, URL image, double price) {
      fId = id;
      fDesc = desc;
      fImage = image;
      fCurrBid = price;
   }
   
   public String getId() { return fId; }
   
   public String getDescription() { return fDesc; }   
   
   public Icon getIcon() { 
      if (fIcon == null) {
         fIcon = new ImageIcon(fImage);
      }
      
      return fIcon; 
   }
   
   public Date getTimeOfBid() { return fTimeOfBid; }
   
   public double getCurrentBid() { return fCurrBid; }
   
   public int comparePrice(Bid b) { 
      return comparePrice(b.fCurrBid);
   }
   
   public int comparePrice(double p) {
      //have to perform multiplication before subtraction because doing it after
      //returned no difference when comparing certain numbers like 5.61 & 5.62
      //go figure?!?
      return (int)(fCurrBid*100-p*100);
   }
   
   public void updateBid(double price) {
      if (comparePrice(price) > 0) {
         throw new IllegalArgumentException("New price must be > " + fCurrBid + 
                                            ": " + price);
      }
      
      fCurrBid = price;
      fTimeOfBid = new Date();
   }
   
   public boolean equals(Object o) {
      if (o instanceof Bid) {
         Bid b = (Bid)o;
         return fId.equals(b.fId);
      }
      return false;      
   }
   
   public String toString() {
      return fDesc;
   }
}