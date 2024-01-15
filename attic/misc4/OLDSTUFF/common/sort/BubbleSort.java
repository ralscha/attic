

package common.sort;

import java.util.Comparator;

public class BubbleSort extends AbstractSort {
	public void sort(Object[] list, Comparator comp) {
		for (int i = list.length; i >= 0; i--) {
			boolean swap = false;
			for (int j = 0; j < i - 1; j++) {
				if (comp.compare(list[j], list[j + 1]) > 0) {
					Object temp = list[j];
					list[j] = list[j + 1];
					list[j + 1] = temp;
					swap = true;
				}
			}
			if (!swap)
				break;
		}
	}
}