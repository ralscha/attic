
import java.util.Random;
import com.objectspace.jgl.*;

public class Birth6 {

    public static void main(String args[]) {
        Random rand = new Random();
        boolean[] karten = new boolean[36];
        
        int g;
        int verliebt = 0;
        int j = 0;
        for (; j < 9000000; j++) {
        
            int auswahl = 0;
            for (int i = 0; i < karten.length; i++) {
                karten[i] = false;
            }
        
            while (auswahl < 7) {
                g = Math.abs((rand.nextInt() % 36));
                if (karten[g] == false) {
                    karten[g] = true;
                    auswahl++;
                }
            }
            
            int herz = 0;
            
            for (int n = 0; n <= 8; n++) {
                if (karten[n] == true) {
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