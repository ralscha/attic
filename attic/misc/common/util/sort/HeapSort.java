

package common.util.sort;

import java.util.Comparator;

public class HeapSort extends AbstractSort {
	public void sort(Object[] list, Comparator comp) {
		for (int i = (list.length / 2); i >= 0; i--) {
			trickleDown(list, comp, list.length, i);
		}
		for (int i = list.length - 1; i >= 0; i--) {
			Object root = list[0];
			list[0] = list[i];
			trickleDown(list, comp, i, 0);
			list[i] = root;
		}
	}

	private Object removeMax(Object[] list, Comparator comp, int length) {
		Object root = list[0];
		list[0] = list[length];
		trickleDown(list, comp, length, 0);
		return root;
	}

	private void trickleDown(Object[] list, Comparator comp, int length, int index) {
		int max;
		Object top = list[index];
		while (index < length / 2) {
			int left = 2 * index + 1;
			int right = left + 1;
			if (right < length && comp.compare(list[left], list[right]) < 0) {
				max = right;
			} else
				max = left;
			if (comp.compare(top, list[max]) >= 0)
				break;
			list[index] = list[max];
			index = max;
		}
		list[index] = top;
	}
}