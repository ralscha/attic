
import java.util.*;
import common.util.*;

public class PermutationTest implements PermutationHook {
   
	public PermutationTest() {
		/*
		List abc = new ArrayList();
		
		char start = 'A';
		for (int i = 0; i < 26; i++)
			abc.add(new Character((char)(start+i)));
		
		*/
		String zahlen[] = {"1", "2", "3", "4", "5", "6", "7"};
		
		Permutation perm = new Permutation(zahlen, 5, this);
		perm.permute();
	}

	public void newPermutation(Object[] result) {
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]+",");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		new PermutationTest();
	}
}