package common.util;

import java.util.*;

public class Permutation {

	private int queue[];
	private boolean flag[];
	private List resultList;
	private Object[] items;
	private int m, n;
	
	//Permutationen mit n-Elementen aus m	
	public Permutation(Object[] input, int n)	 {
	
		m = input.length;
		this.n = n;
		
		this.items = input;
		
		flag = new boolean[m];
		queue = new int[m];
		
		for (int i = 0; i < m; i++)
			flag[i] = false;

		resultList = new ArrayList();
	
		perm(0);
	
	}
		
	public List getPermutations() {
		return resultList;
	}	
	
	private void perm(int level) {

		for (int i = 0; i < m; i++) {
			if (flag[i] == false) {
				flag[i] = true;
				queue[level] = i;
				if (level < n-1)
					perm(level + 1);
				else {
					Object[] result = new Object[n];
					for (int j = 0; j <= level; j++)
						result[j] = items[queue[j]];
					resultList.add(result);
				}
				flag[i] = false;
			}
		}
	}

	public static void main(String[] args) {
		String zahlen[] = {"M", "I", "S", "S"};
		
		List rl = new Permutation(zahlen, 4).getPermutations();
		System.out.println(rl.size());
		
		Iterator it = rl.iterator();
		while(it.hasNext()) {
			Object[] ia = (Object[])it.next();
			for (int i = 0; i < ia.length; i++) {
				System.out.print(ia[i]+",");
			}
			System.out.println();
		}	
		
	}
}