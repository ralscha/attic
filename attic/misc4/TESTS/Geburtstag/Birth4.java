
import java.awt.*;
import java.util.Random;

public class Birth4 {

    public static void main(String args[]) {
        Random rand = new Random();
        int h, gerade, sym, versch, total;
        gerade = sym = versch = total = 0;
        int x, y, z;
        
        
        for (int j = 0; j < 10000000; j++) {
            
            x = Math.abs((rand.nextInt() % 9))+1;
            y = Math.abs(rand.nextInt() % 10);
            z = Math.abs(rand.nextInt() % 10);
            if (x == 0) System.out.println("AUTSCH");
            total++;
            h = (x*100) + (y*10) + z;
            if (x == z) sym++;
            if ((x != y) && (y != z) && (x != z)) {
                versch++;
                if (isEven(z)) gerade++;
            }

        }
        /*
        float z;
        int p,n;
        int btotal = 0;
        int j;
        boolean hansfaust, irenefaust;
        for (j = 0; j < 1000000; j++) {
            irenefaust = hansfaust = false;
            if (Math.abs(rand.nextFloat()) < 0.6) irenefaust=true;        
            if (Math.abs(rand.nextFloat()) < 0.4) hansfaust=true;        
            
            if (irenefaust == hansfaust) btotal++;
        }
        System.out.println("TOTAL: "+j);
        System.out.println("BBBB : "+btotal);
        /*
        int h, gerade, sym, versch, total;
        gerade = sym = versch = total = 0;
        for (int x = 1; x <= 9; x++) {
            for (int y = 0; y <= 9; y++) {
                for (int z = 0; z <= 9; z++) {
                    total++;
                    h = (x*100) + (y*10) + z;
                    if (x == z) sym++;
                    if ((x != y) && (y != z) && (x != z)) {
                        versch++;
                        if (isEven(z)) gerade++;
                    }
                }
            }
        }
        */
        System.out.println("Total         :  "+total);
        System.out.println("Symmetrisch   :  "+sym);
        System.out.println("Verschieden   :  "+versch);
        System.out.println("Gerade+versch :  "+gerade);
    }   
    
    private static boolean isEven(int a) {
	    return (a % 2 == 0);
	}
}