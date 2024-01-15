
import java.awt.*;
import java.util.Random;

public class Birth3 {

    public static void main(String args[]) {
        Random rand = new Random();
        int r,i,a,z;
        int rrrr = 0;
        boolean ar[] = new boolean[8];
        
        for (i = 0; i < 10000000; i++) {
            r = 0; a = 0; 
            for (int x = 0; x < 8; x++) ar[x] = false;
            while(a < 4) {
                z = Math.abs(rand.nextInt() % 8);
                if (!ar[z]) { 
                    ar[z] = true;
                    a++;
                }
            }
            for (int y = 0; y < 3; y++) {
                if (ar[y]) r++;
            }
            
            if (r == 3) 
                rrrr++;
        }
        System.out.println("Versuche : "+i);
        System.out.println("RRRR     : "+rrrr);        
    }   
}