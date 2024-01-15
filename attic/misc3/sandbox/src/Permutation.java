



public class Permutation {

	private int queue[];
	private boolean flag[];
	
	private Object[] items;
	private int m, n;
	private PermutationHook hook;
	
	//Permutationen mit n-Elementen aus m	
	public Permutation(Object[] input, int n, PermutationHook pHook)	 {
	
		m = input.length;
		this.n = n;
		
		this.items = input;
		
		flag = new boolean[m];
		queue = new int[m];
		
		for (int i = 0; i < m; i++)
			flag[i] = false;
		
		hook = pHook;
	}
		
	
	public void permute() {
		perm(0);
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
					hook.newPermutation(result);
				}
				flag[i] = false;
			}
		}
	}

}