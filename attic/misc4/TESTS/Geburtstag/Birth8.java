
import java.util.Random;
import com.objectspace.jgl.*;

public class Birth8 {

    public static void main(String args[]) {
        Random rand = new Random();
        
        int g;
        int verliebt = 0;
        int j = 0;
        for (; j < 10000000; j++) {

            int herz = 0;
            for (int i = 0; i < 7; i++) {
                g = Math.abs((rand.nextInt() % 36));
                if (g <= 8) {
                    herz++;
                }
            }
                        
            if (herz >= 3) {
                verliebt++;
            }
            
            
        }

        System.out.println("TOTAL    : " + j);
        System.out.println("VERLIEBT : " + verliebt);
        System.out.println("         : " + ((float)verliebt / (float)j) * 100f);
        
   }   
    
}