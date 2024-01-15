
package common.sort;

import java.util.List;
import java.util.ArrayList;

public class SortingTest {
	public static List randomList(int length) {
		List list = new ArrayList();
		for (int i = 0; i < length; i++) {
			list.add(new Integer((int)(Math.random() * 99)));
		}
		return list;
	}

	public static void printList(List list) {
		for (int i = 0; i < list.size(); i++) {
			Object element = list.get(i);
			System.out.print(element + " ");
		}
		System.out.println();
	}

	public static List copyList(List list) {
		List copy = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			int value = ((Integer) list.get(i)).intValue();
			copy.add(new Integer(value));
		}
		return copy;
	}

	public static boolean sortedList(List list) {
		for (int i = 0; i < list.size() - 1; i++) {
			Comparable item = (Comparable) list.get(i);
			Comparable next = (Comparable) list.get(i + 1);
			if (item.compareTo(next) > 0)
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String[] algorithms = {"Bubble", "Insertion", "Selection", "Shell", "Merge", "Quick","Heap"};
		List list = randomList(20);
		boolean success = true;
		int count = 20;
		for (int j = 0; j < count; j++) {
			for (int i = 0; i < algorithms.length; i++) {
				List copy = copyList(list);
				Sort sorter = SortFactory.getInstance(algorithms[i]);
				String className = sorter.getClass().getName();
				if (!className.startsWith("common.sort."+algorithms[i]))
					throw new IllegalArgumentException("Algorithm failed");
				sorter.sort(copy);

				System.out.println();
				System.out.println("TESTING  : " + className);
				System.out.print("Original : ");
				printList(list);
				System.out.print("Sorted   : ");
				printList(copy);
				System.out.print("RESULT   : ");
				boolean result = sortedList(copy);
				System.out.println(result ? "SUCCESS" : "FAILURE");
				if (!result)
					success = false;
			}
			System.out.print("\nLOOP " + (j + 1) + " RESULT: ");
			System.out.println(success ? "SUCCESS" : "FAILURE");
		}
		System.out.print("\nTOTAL " + count + " LOOP TEST RESULT: ");
		System.out.println(success ? "SUCCESS" : "FAILURE");
	}
}