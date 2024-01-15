/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

// Copyright 1999 MageLang Institute
// <version>$Id: //depot/main/src/edu/modules/Collections/magercises/ComboBox/Solution/ArrayListComboBoxModel.java#1 $</version>
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Class declaration
 *
 *
 * @author
 * @version %I%, %G%
 */
public class ArrayListComboBoxModel extends AbstractListModel 
    implements MutableComboBoxModel {
    private Object selectedItem;
    private List   list;

    /**
     * Constructor declaration
     *
     *
     * @param list
     *
     * @see
     */
    public ArrayListComboBoxModel(List list) {
	this.list = new ArrayList(list);
    }

    // ListModel

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getSize() {
	return list.size();
    } 

    /**
     * Method declaration
     *
     *
     * @param i
     *
     * @return
     *
     * @see
     */
    public Object getElementAt(int i) {
	return list.get(i);
    } 

    // ComboBoxModel

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public Object getSelectedItem() {
	return selectedItem;
    } 

    /**
     * Method declaration
     *
     *
     * @param newValue
     *
     * @see
     */
    public void setSelectedItem(Object newValue) {
	selectedItem = newValue;
    } 

    // MutableComboBoxModel

    /**
     * Method declaration
     *
     *
     * @param element
     *
     * @see
     */
    public void addElement(Object element) {
	list.add(element);

	// Added at end, notify ListDataListener objects
	int length = getSize();

	fireIntervalAdded(this, length - 1, length - 1);
    } 

    /**
     * Method declaration
     *
     *
     * @param element
     * @param index
     *
     * @see
     */
    public void insertElementAt(Object element, int index) {
	list.add(index, element);

	// Added in middle, notify ListDataListener objects
	fireIntervalAdded(this, index, index);
    } 

    /**
     * Method declaration
     *
     *
     * @param element
     *
     * @see
     */
    public void removeElement(Object element) {

	// Find out position
	int index = list.indexOf(element);

	if (index != -1) {

	    // Remove an element
	    list.remove(index);

	    // Removed from middle, notify ListDataListener objects
	    fireIntervalRemoved(this, index, index);
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param index
     *
     * @see
     */
    public void removeElementAt(int index) {
	if (getSize() >= index) {
	    list.remove(index);

	    // Remove an element at the specified position
	    // Removed from index, notify ListDataListener objects
	    fireIntervalRemoved(this, index, index);
	} 
    } 

}



/*--- formatting done in "Sun Java Convention" style on 01-09-2000 ---*/

