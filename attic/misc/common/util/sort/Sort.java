

package common.util.sort;

import java.util.List;
import java.util.Comparator;

public interface Sort {
	public void sort(List list);
	public void sort(Comparable[] list);
	public void sort(Object[] list, Comparator comp);
}