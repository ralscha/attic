package common.util;

import java.util.*;

public class Combination {

	private int queue[];
	private List resultList;
	private Object[] items;
	private int m, n;
	
	//Kombinationen mit n-Elementen aus m	
	public Combination(Object[] input, int n)	 {
	
		m = input.length;
		this.n = n;
		
		this.items = input;
		
		queue = new int[m];

		resultList = new ArrayList();
	
		comb(0, 0);
	
	}
		
	public List getCombinations() {
		return resultList;
	}	
	
	private void comb(int low, int level) {
		for (int i = low; i <= m-n+level; i++) {
			queue[level] = i;
			if (level < n-1) 
				comb(i+1, level+1);
			else {
				Object[] result = new Object[n];
				for (int j = 0; j <= level; j++)
					result[j] = items[queue[j]];
				resultList.add(result);
			}
		}
	}
	

	public static void main(String[] args) {
		String zahlen[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
		
		List rl = new Combination(zahlen, 4).getCombinations();
		Iterator it = rl.iterator();
		while(it.hasNext()) {
			Object[] ia = (Object[])it.next();
			for (int i = 0; i < ia.length; i++) {
				System.out.print(ia[i]+",");
			}
			System.out.println();
		}	
		System.out.println("TOTAL : " + rl.size());
	
	}
}
	