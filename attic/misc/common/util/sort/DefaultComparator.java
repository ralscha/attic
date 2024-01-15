
package common.util.sort;

import java.util.Comparator;

public class DefaultComparator implements Comparator {
	public int compare(Object one, Object two) {
		if (!(one instanceof Comparable && two instanceof Comparable))
			throw new IllegalArgumentException("DefaultComparator requires Comparable elements");
		Comparable left = (Comparable) one;
		Comparable right = (Comparable) two;
		return left.compareTo(right);
	}

	public boolean equals(Object one, Object two) {
		return compare(one, two) == 0;
	}
}