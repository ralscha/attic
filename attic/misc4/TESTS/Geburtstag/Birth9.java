
import java.util.Random;
import com.objectspace.jgl.*;

public class Birth9 {

    public static void main(String args[]) {
        Random rand = new Random();
        
        int zahl;              
        int j = 0;
        int a,b,c,d;
        a = b = c = d = 0;
        for (; j < 10000000; j++) {

            zahl = 0;
            for (int i = 0; i < 20; i++) {
                if (rand.nextDouble() < 0.5) {
                    zahl++;                    
                }
            }               
            
            if (zahl == 10)
                a++;
                
            if (zahl <= 10)
                b++;
                
            if (zahl >= 10)
                c++;
                
            if ((zahl <= 7) || (zahl >= 13))    
                d++;
            
            
            
        }

        System.out.println("TOTAL         : " + j);
        System.out.println("a) genau      : " + ((float)a / (float)j) * 100f);
        System.out.println("b) höchstens  : " + ((float)b / (float)j) * 100f);
        System.out.println("c) wenigstens : " + ((float)c / (float)j) * 100f);
        System.out.println("d) mehr als 5 : " + ((float)d / (float)j) * 100f);
        
        
   }   
    
}