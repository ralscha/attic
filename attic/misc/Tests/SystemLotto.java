import java.util.*;
import common.util.log.*;
import ViolinStrings.*;

public class SystemLotto {

	private int queue[];
	private boolean flag[];
	private List resultList;
		
	public List permutation(int[] numbers, int n) {
		
		int len = numbers.length;
		
		flag = new boolean[len];
		queue = new int[len];
		
		for (int i = 0; i < len; i++)
			flag[i] = false;

		resultList = new ArrayList();
	
		perm(numbers, len, n, 0);
		
		return resultList;
	}

	private void perm(int numbers[], int m, int n, int level) {

		for (int i = 0; i < m; i++) {
			if (flag[i] == false) {
				flag[i] = true;
				queue[level] = i;
				if (level < n-1)
					perm(numbers, m, n, level + 1);
				else {
					int[] result = new int[n];
					for (int j = 0; j <= level; j++)
						result[j] = numbers[queue[j]];
					resultList.add(result);
				}
				flag[i] = false;
			}
		}
	}


	public void run() {
		int zahlen[] = {1,2,3,4,5,6};
		List rl = permutation(zahlen, 3);
		Iterator it = rl.iterator();
		while(it.hasNext()) {
			int[] ia = (int[])it.next();
			for (int i = 0; i < ia.length; i++)
				System.out.print(ia[i]+",");
			System.out.println();
		}	
	}

	public static void main(String[] args) {
		new SystemLotto().run();
	}
}


