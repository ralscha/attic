import lotto.*;
import lotto.page.*;
import common.util.*;
import java.util.*;

public class CheckAusspielungen {

	public static void main(String args[]) {
		DbManager.initialize(AppProperties.getStringProperty("file.database"), false);
		
		com.odi.Transaction tr = DbManager.startReadTransaction();
		Ausspielungen auss = DbManager.getAusspielungen();
		
		Iterator it = auss.iterator();
		while(it.hasNext()) {
			Ziehung zie = (Ziehung)it.next();
			System.out.println("testing .. " + zie.getNr() + "/" + zie.getJahr());
			List result = compareWithAllOther(auss, zie);
			if (result.size() > 0) {
				for (int x = 0; x < result.size(); x++) {
					Ziehung z = (Ziehung)result.get(x);
					System.out.println(z.getNr() + " / " + z.getJahr());
				}
					
			} 
			
		}
			
		DbManager.commitTransaction(tr);
		DbManager.shutdown();
	}
	
	static List compareWithAllOther(Ausspielungen auss, Ziehung cz) {
		Iterator it = auss.iterator();
		List result = new ArrayList();
		
		while(it.hasNext()) {
			
			Ziehung zie = (Ziehung)it.next();
			if (!cz.equals(zie)) {
				int z1[] = cz.getZahlen();
				int z2[] = zie.getZahlen();
				
				int c = countSame(z1, z2);
				if (c >= 5)
					result.add(zie);
			}
			
		}
		return result;
	}
	
	static int countSame(int[] z1, int[] z2) {
		int c = 0;
		out: for (int i = 0; i < z1.length; i++) {
			for (int j = 0; j < z2.length; j++) {
				if (i != j) {
					if (z1[i] == z2[j]) {
						c++;
						continue out;
					}
				}
			}
		}
		return c;
	}
	
}