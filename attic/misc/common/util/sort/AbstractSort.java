
package common.util.sort;

import java.util.List;
import java.util.Comparator;

public abstract class AbstractSort implements Sort {
	private static DefaultComparator defaultComparator = new DefaultComparator();

	public void sort(List list) {
		int size = list.size();
		Comparable[] array = new Comparable[size];
		for (int i = 0; i < size; i++) {
			array[i] = (Comparable) list.get(i);
		}
		sort(array);
		for (int i = 0; i < size; i++) {
			list.set(i, array[i]);
		}
	}

	public void sort(Comparable[] array) {
		sort(array, defaultComparator);
	}

	public abstract void sort(Object[] list, Comparator comp);
}