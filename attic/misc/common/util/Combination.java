package common.util;

import java.util.*;

public class Combination {

	private int queue[];
	private Object[] items;
	private int m, n;
	private CombinationHook hook;
	
	//Kombinationen mit n-Elementen aus m	
	public Combination(Object[] input, int n, CombinationHook cHook)	 {	
		m = input.length;
		this.n = n;		
		this.items = input;		
		queue = new int[m];
		hook = cHook;
	}
	
	public void combine() {
		comb(0, 0);
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
				hook.newCombination(result);
			}
		}
	}
	


}
	