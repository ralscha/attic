import java.util.*;
import common.util.*;

public class CombinationTest implements CombinationHook {
   
	public CombinationTest() {
		String zahlen[] = {"1", "2", "3", "4", "5", "6", "7"};
		
		Combination comb = new Combination(zahlen, 5, this);
		comb.combine();
	}
	
	public void newCombination(Object[] result) {
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]+",");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		new CombinationTest();	
	}

}
