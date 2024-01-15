import java.util.Arrays;

public class Test2 {

  public static void main(String[] args) {

    Long[] l = new Long[5];
    l[0] = null;
    l[1] = new Long(4);
    l[2] = new Long(4);
    l[3] = new Long(4);
    l[4] = null;
    
    int[] span = getNotEmptySpan(l);
        

    for (int i = 0; i < span.length; i++) {
      System.out.println(span[i]);
    }
  }
  
  
  private static int[] getNotEmptySpan(Object[] l) {
    int[] span = new int[l.length];
    Arrays.fill(span, 0);
    
    for (int i = 0; i < l.length; i++) {
      if (l[i] == null) {
        span[i] = 1;
        
      } else {
        int j = i + 1;
        
        int count = 1;
        while (j < l.length) {          
          if ((l[j] != null) && (l[j].equals(l[i]))) {

            count++;
            j++;
          } else {              
            break;
          }
        }
        span[i] = count;
        i = j-1;
      }
    }
    
    
    return span;
  }
/*
  private static int[] getSpan(Object[] l) {
    
    int[] span = new int[l.length];
    Arrays.fill(span, 0);

    for (int i = 0; i < l.length; i++) {
      if (l[i] != null) {
        span[i] = 1;
        
      } else {
        int j = i + 1;
        
        int count = 1;
        while (j < l.length) {          
          if (l[j] == null) {

            count++;
            j++;
          } else {              
            break;
          }
        }
        span[i] = count;
        i = j-1;
      }
    }
    
    return span;
  }
  */

}
