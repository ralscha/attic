

package common.util.sort;

import java.util.Comparator;

public class SelectionSort extends AbstractSort {
	public void sort(Object[] list, Comparator comp) {
		for (int i = 0; i < list.length; i++) {
			int key = i;
			for (int j = i + 1; j < list.length; j++) {
				if (comp.compare(list[j], list[key]) <= 0)
					key = j;
			}
			Object temp = list[key];
			list[key] = list[i];
			list[i] = temp;
		}
	}
}