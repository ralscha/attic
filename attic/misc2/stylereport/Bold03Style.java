import inetsoft.report.*;
import inetsoft.report.style.*;
import java.awt.*;


public class Bold03Style extends TableStyle {

   public Bold03Style() {
   }
   
   public Bold03Style(TableLens table) {
      super(table);
   }
    

   protected TableLens createStyle(TableLens tbl) {
      return new Style();
   }
   
 
   class Style extends Transparent {
     
      public Font getFont(int r, int c) {
        System.out.println(r);
        System.out.println(c);
	      Font font = table.getFont(r, c); 
        if ((c == 0) || (c == 3)) {
          return createFont(font, Font.BOLD);  
        } else {
          return font;
        }        
      }
   }
}
