

package common.sort;

import java.util.Comparator;

public class SortFactory {
	private static SortFactory singleton = new SortFactory();
	private static Sort bubble, insertion, selection, shell, merge, heap, quick;

	private SortFactory() {}

	public static Sort getInstance(String name) {
		if (name.equalsIgnoreCase("Bubble")) {
			if (singleton.bubble == null)
				singleton.bubble = new BubbleSort();
			return singleton.bubble;
		}
		if (name.equalsIgnoreCase("Insertion")) {
			if (singleton.insertion == null)
				singleton.insertion = new InsertionSort();
			return singleton.insertion;
		}
		if (name.equalsIgnoreCase("Selection")) {
			if (singleton.selection == null)
				singleton.selection = new SelectionSort();
			return singleton.selection;
		}
		if (name.equalsIgnoreCase("Merge")) {
			if (singleton.merge == null)
				singleton.merge = new MergeSort();
			return singleton.merge;
		}
		if (name.equalsIgnoreCase("Shell")) {
			if (singleton.shell == null)
				singleton.shell = new ShellSort();
			return singleton.shell;
		}
		if (name.equalsIgnoreCase("Heap")) {
			if (singleton.heap == null)
				singleton.heap = new HeapSort();
			return singleton.heap;
		}
		if (name.equalsIgnoreCase("Quick")) {
			if (singleton.quick == null)
				singleton.quick = new QuickSort();
			return singleton.quick;
		}
		throw new IllegalArgumentException("Sorting algorithm " + name + " is not supported");
	}
}