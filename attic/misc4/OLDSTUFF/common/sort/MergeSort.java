

package common.sort;

import java.util.Comparator;

public class MergeSort extends AbstractSort {
	public void sort(Object[] list, Comparator comp) {
		Object[] workspace = new Object[list.length];
		merge(list, workspace, comp, 0, list.length - 1);
	}

	private void merge(Object[] list, Object[] workspace, Comparator comp, int lo, int hi) {
		if (lo == hi)
			return;
		int mid = (lo + hi) / 2;
		merge(list, workspace, comp, lo, mid);
		merge(list, workspace, comp, mid + 1, hi);
		merge(list, workspace, comp, lo, mid + 1, hi);
	}

	private void merge(Object[] list, Object[] workspace, Comparator comp, int lo, int hi,
                   	int max) {
		int j = 0;
		int low = lo;
		int mid = hi - 1;
		int count = max - low + 1;
		while (lo <= mid && hi <= max) {
			if (comp.compare(list[lo], list[hi]) < 0)
				workspace[j++] = list[lo++];
			else
				workspace[j++] = list[hi++];
		}
		while (lo <= mid)
			workspace[j++] = list[lo++];
		while (hi <= max)
			workspace[j++] = list[hi++];
		for (j = 0; j < count; j++) {
			list[low + j] = workspace[j];
		}
	}
}