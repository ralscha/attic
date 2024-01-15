
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import common.ui.borders.*;
import java.util.*;
import javax.swing.event.*;

public class TableCheckEditor implements TableCellEditor, ActionListener{

	/** Event listeners */
	protected EventListenerList listenerList = new EventListenerList();
	transient protected ChangeEvent changeEvent = null;

	protected JTriStateCheckBox editorComponent;
	protected int clickCountToStart = 1;

	public Component getTableCellEditorComponent(JTable table, Object value,
        	boolean isSelected, int row, int column) {

		FileNode node = (FileNode) value;

		if (isSelected)
			editorComponent.setBackground(UIManager.getColor("textHighlight"));
		else 
			editorComponent.setBackground(Color.lightGray);

		editorComponent.setSelected(node.getState());

		return editorComponent;
	}


	public TableCheckEditor() {

		editorComponent = new JTriStateCheckBox();
		editorComponent.setBackground(Color.lightGray);
		editorComponent.setHorizontalAlignment(SwingConstants.CENTER);
		editorComponent.addActionListener(this);
	}

	public void setClickCountToStart(int count) {
		clickCountToStart = count;
	}

	public int getClickCountToStart() {
		return clickCountToStart;
	}

	public Object getCellEditorValue() {
		return new Integer(editorComponent.getState());
	}

	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
		}
		return true;
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	public boolean stopCellEditing() {
		fireEditingStopped();
		return true;
	}

	public void cancelCellEditing() {
		fireEditingCanceled();
	}

	public void addCellEditorListener(CellEditorListener l) {
		listenerList.add(CellEditorListener.class, l);
	}

	public void removeCellEditorListener(CellEditorListener l) {
		listenerList.remove(CellEditorListener.class, l);
	}

	protected void fireEditingStopped() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CellEditorListener.class) {
				// Lazily create the event:
				if (changeEvent == null)
					changeEvent = new ChangeEvent(this);
				((CellEditorListener) listeners[i + 1]).editingStopped(changeEvent);
			}
		}
	}

	protected void fireEditingCanceled() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CellEditorListener.class) {
				// Lazily create the event:
				if (changeEvent == null)
					changeEvent = new ChangeEvent(this);
				((CellEditorListener) listeners[i + 1]).editingCanceled(changeEvent);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		fireEditingStopped();
	}


	
}
