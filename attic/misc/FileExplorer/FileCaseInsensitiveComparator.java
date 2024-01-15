

import java.util.*;
import java.io.*;

public class FileCaseInsensitiveComparator implements Comparator {

	public int compare(Object element1, Object element2) {
		String lowerE1 = ((FileNode)element1).getFile().getPath().toLowerCase();
		String lowerE2 = ((FileNode)element2).getFile().getPath().toLowerCase();
		return lowerE1.compareTo(lowerE2);
	}
}