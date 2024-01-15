

package common.util.sort;

import java.util.Comparator;

public class InsertionSort extends AbstractSort {
	public void sort(Object[] list, Comparator comp) {
		for (int j = 1; j < list.length; j++) {
			Object key = list[j];
			int i = j - 1;
			while ((i >= 0) && (comp.compare(list[i], key) > 0)) {
				list[i + 1] = list[i];
				i--;
			}
			list[i + 1] = key;
		}
	}
}