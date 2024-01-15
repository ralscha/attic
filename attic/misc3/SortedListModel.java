// Copyright 1999 MageLang Institute
// $Id: //depot/main/src/edu/modules/Collections/magercises/JList/Solution/SortedListModel.java#2 $
import javax.swing.*;
import java.util.*;

public class SortedListModel extends AbstractListModel {

	private SortedSet model;

	public SortedListModel() {
		model = new TreeSet();
	}

	// ListModel methods
	public int getSize() {
		return model.size();
	}

	public Object getElementAt(int index) {
		Object[] array = model.toArray();
		return array[index];
	}

	// Other methods
	public void add(Object element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void addAll(Object elements[]) {
		Collection c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(Object element) {
		return model.contains(element);
	}

	public Object firstElement() {
		return model.first();
	}

	public Iterator iterator() {
		return model.iterator();
	}

	public Object lastElement() {
		return model.last();
	}

	public boolean removeElement(Object element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}
}