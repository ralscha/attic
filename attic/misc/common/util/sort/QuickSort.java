

package common.util.sort;

import java.util.Comparator;

public class QuickSort extends AbstractSort {
	public void sort(Object[] list, Comparator comp) {
		sort(list, comp, 0, list.length - 1);
	}

	private void sort(Object[] list, Comparator comp, int first, int last) {
		if (first >= last)
			return;
		int lo = first, hi = last;
		Object mid = list[(first + last) / 2];
		Object temp;
		do {
			while (comp.compare(list[lo], mid) < 0)
				lo++;
			while (comp.compare(list[hi], mid) > 0)
				hi--;
			if (lo <= hi) {
				temp = list[lo];
				list[lo++] = list[hi];
				list[hi--] = temp;
			}
		} while (lo <= hi)
			;
		sort(list, comp, first, hi);
		sort(list, comp, lo, last);
	}

}