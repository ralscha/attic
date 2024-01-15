package common.sort;

import java.util.Comparator;

public class ShellSort extends AbstractSort {
	public void sort(Object[] list, Comparator comp) {
		int h = 1;
		while (h <= list.length / 3)
			h = h * 3 + 1;
		while (h > 0) {
			for (int i = h; i < list.length; i++) {
				Object temp = list[i];
				int j = i;
				while (j > h - 1 && comp.compare(list[j - h], temp) > 0) {
					list[j] = list[j - h];
					j -= h;
				}
				list[j] = temp;
			}
			h = (h - 1) / 3;
		}
	}
}