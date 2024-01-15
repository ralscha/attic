

package sort;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import common.util.sort.*;

public class SortingBenchmark {
	public static List randomList(int length) {
		List list = new ArrayList();
		for (int i = 0; i < length; i++) {
			list.add(new Integer((int)(Math.random() * 99)));
		}
		return list;
	}

	public static List copyList(List list) {
		List copy = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			int value = ((Integer) list.get(i)).intValue();
			copy.add(new Integer(value));
		}
		return copy;
	}

	public static long mark() {
		return System.currentTimeMillis();
	}

	public static long time(long mark) {
		return System.currentTimeMillis() - mark;
	}

	public static void main(String[] String_1darray1) {
		String[] algorithm = {"Bubble", "Insertion", "Selection", "Shell", "Merge", "Quick",
                      		"Heap"};
		int iterations = 5;
		int[] elements = { 100, 500, 1000, 5000, 10000, 50000 };
		long[][] averages = new long[elements.length][algorithm.length];

		for (int i = 0; i < iterations; i++) {
			System.out.print("\nIteration: " + (i + 1));
			for (int j = 0; j < elements.length; j++) {
				System.out.print("\n" + elements[j] + " elements");
				List list = randomList(elements[j]);
				int count = 0;
				while (count < algorithm.length) {
					List copy = copyList(list);
					Sort sort = SortFactory.getInstance(algorithm[count]);
					String name = sort.getClass().getName();

					if (!(name.startsWith("common.util.sort."+algorithm[count]))) {
						throw new IllegalArgumentException("Algorithm failed");
					} else {
						long mark = mark();

						sort.sort(copy);
						averages[j][count] += time(mark);
						System.out.print(".");
						count++;
					}
				}
			}
		}
		System.out.println();
		System.out.print("Elements: ");
		for (int i = 0; i < elements.length; i++) {
			if (i > 0)
				System.out.print(", ");
			System.out.print(elements[i]);
		}
		System.out.println();
		for (int i = 0; i < algorithm.length; i++) {
			System.out.print((String.valueOf(algorithm[i])) + "Sort: ");
			for (int j = 0; j < elements.length; j++) {
				long average = averages[j][i] / (long) iterations;
				if (j > 0)
					System.out.print(", ");
				System.out.print(average);
			}
			System.out.println();
		}
		System.out.println();
	}

}