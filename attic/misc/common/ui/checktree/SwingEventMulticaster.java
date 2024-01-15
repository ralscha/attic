package common.ui.checktree;

import java.awt.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class SwingEventMulticaster
  extends AWTEventMulticaster
  implements
    AncestorListener,
    CaretListener,
    CellEditorListener,
    ChangeListener,
    DocumentListener,
    HyperlinkListener,
    InternalFrameListener,
    ListDataListener,
    ListSelectionListener,
    MenuDragMouseListener,
    MenuKeyListener,
    MenuListener,
    PopupMenuListener,
    TableColumnModelListener,
    TableModelListener,
    TreeExpansionListener,
    TreeModelListener,
    TreeSelectionListener,
    TreeWillExpandListener,
    UndoableEditListener
{
  protected SwingEventMulticaster(EventListener a, EventListener b)
  {
    super(a, b);
  }

// ---------------------------------------------------
//   AncestorListener
// ---------------------------------------------------


  public void ancestorAdded(AncestorEvent event)
  {
    ((AncestorListener)a).ancestorAdded(event);
    ((AncestorListener)b).ancestorAdded(event);
  }
  
  public void ancestorRemoved(AncestorEvent event)
  {
    ((AncestorListener)a).ancestorRemoved(event);
    ((AncestorListener)b).ancestorRemoved(event);
  }
  
  public void ancestorMoved(AncestorEvent event)
  {
    ((AncestorListener)a).ancestorMoved(event);
    ((AncestorListener)b).ancestorMoved(event);
  }
  
  public static AncestorListener add(
    AncestorListener a, AncestorListener b)
  {
    return (AncestorListener)addInternal(a, b);
  }

  public static AncestorListener remove(
    AncestorListener l, AncestorListener oldl)
  {
    return (AncestorListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   CaretListener
// ---------------------------------------------------

  public void caretUpdate(CaretEvent event)
  {
    ((CaretListener)a).caretUpdate(event);
    ((CaretListener)b).caretUpdate(event);
  }
      
  public static CaretListener add(
    CaretListener a, CaretListener b)
  {
    return (CaretListener)addInternal(a, b);
  }

  public static CaretListener remove(
    CaretListener l, CaretListener oldl)
  {
    return (CaretListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   CellEditorListener
// ---------------------------------------------------
  
  public void editingCanceled(ChangeEvent event)
  {
    ((CellEditorListener)a).editingCanceled(event);
    ((CellEditorListener)b).editingCanceled(event);
  }
  
  public void editingStopped(ChangeEvent event)
  {
    ((CellEditorListener)a).editingStopped(event);
    ((CellEditorListener)b).editingStopped(event);
  }

  public static CellEditorListener add(
    CellEditorListener a, CellEditorListener b)
  {
    return (CellEditorListener)addInternal(a, b);
  }

  public static CellEditorListener remove(
    CellEditorListener l, CellEditorListener oldl)
  {
    return (CellEditorListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   ChangeListener
// ---------------------------------------------------

  public void stateChanged(ChangeEvent event)
  {
    ((ChangeListener)a).stateChanged(event);
    ((ChangeListener)b).stateChanged(event);
  }
  
  public static ChangeListener add(
    ChangeListener a, ChangeListener b)
  {
    return (ChangeListener)addInternal(a, b);
  }

  public static ChangeListener remove(
    ChangeListener l, ChangeListener oldl)
  {
    return (ChangeListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   DocumentListener
// ---------------------------------------------------

  public void insertUpdate(DocumentEvent event)
  {
    ((DocumentListener)a).insertUpdate(event);
    ((DocumentListener)b).insertUpdate(event);
  }

  public void removeUpdate(DocumentEvent event)
  {
    ((DocumentListener)a).removeUpdate(event);
    ((DocumentListener)b).removeUpdate(event);
  }
  
  public void changedUpdate(DocumentEvent event)
  {
    ((DocumentListener)a).changedUpdate(event);
    ((DocumentListener)b).changedUpdate(event);
  }
  
  public static DocumentListener add(
    DocumentListener a, DocumentListener b)
  {
    return (DocumentListener)addInternal(a, b);
  }

  public static DocumentListener remove(
    DocumentListener l, DocumentListener oldl)
  {
    return (DocumentListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   HyperlinkListener
// ---------------------------------------------------

  public void hyperlinkUpdate(HyperlinkEvent event)
  {
    ((HyperlinkListener)a).hyperlinkUpdate(event);
    ((HyperlinkListener)b).hyperlinkUpdate(event);
  }
  
  public static HyperlinkListener add(
    HyperlinkListener a, HyperlinkListener b)
  {
    return (HyperlinkListener)addInternal(a, b);
  }

  public static HyperlinkListener remove(
    HyperlinkListener l, HyperlinkListener oldl)
  {
    return (HyperlinkListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   InternalFrameListener
// ---------------------------------------------------

  public void internalFrameOpened(InternalFrameEvent event)
  {
    ((InternalFrameListener)a).internalFrameOpened(event);
    ((InternalFrameListener)b).internalFrameOpened(event);
  }

  public void internalFrameClosing(InternalFrameEvent event)
  {
    ((InternalFrameListener)a).internalFrameClosing(event);
    ((InternalFrameListener)b).internalFrameClosing(event);
  }

  public void internalFrameClosed(InternalFrameEvent event)
  {
    ((InternalFrameListener)a).internalFrameClosed(event);
    ((InternalFrameListener)b).internalFrameClosed(event);
  }

  public void internalFrameIconified(InternalFrameEvent event)
  {
    ((InternalFrameListener)a).internalFrameIconified(event);
    ((InternalFrameListener)b).internalFrameIconified(event);
  }
  
  public void internalFrameDeiconified(InternalFrameEvent event)
  {
    ((InternalFrameListener)a).internalFrameDeiconified(event);
    ((InternalFrameListener)b).internalFrameDeiconified(event);
  }

  public void internalFrameActivated(InternalFrameEvent event)
  {
    ((InternalFrameListener)a).internalFrameActivated(event);
    ((InternalFrameListener)b).internalFrameActivated(event);
  }
  
  public void internalFrameDeactivated(InternalFrameEvent event)
  {
    ((InternalFrameListener)a).internalFrameDeactivated(event);
    ((InternalFrameListener)b).internalFrameDeactivated(event);
  }
    
  public static InternalFrameListener add(
    InternalFrameListener a, InternalFrameListener b)
  {
    return (InternalFrameListener)addInternal(a, b);
  }

  public static InternalFrameListener remove(
    InternalFrameListener l, InternalFrameListener oldl)
  {
    return (InternalFrameListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   ListDataListener
// ---------------------------------------------------

  public void intervalAdded(ListDataEvent event)
  {
    ((ListDataListener)a).intervalAdded(event);
    ((ListDataListener)b).intervalAdded(event);
  }
    
  public void intervalRemoved(ListDataEvent event)
  {
    ((ListDataListener)a).intervalRemoved(event);
    ((ListDataListener)b).intervalRemoved(event);
  }
  
  public void contentsChanged(ListDataEvent event)
  {
    ((ListDataListener)a).contentsChanged(event);
    ((ListDataListener)b).contentsChanged(event);
  }
    
  public static ListDataListener add(
    ListDataListener a, ListDataListener b)
  {
    return (ListDataListener)addInternal(a, b);
  }

  public static ListDataListener remove(
    ListDataListener l, ListDataListener oldl)
  {
    return (ListDataListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   ListSelectionListener
// ---------------------------------------------------

  public void valueChanged(ListSelectionEvent event)
  {
    ((ListSelectionListener)a).valueChanged(event);
    ((ListSelectionListener)b).valueChanged(event);
  }
  
  public static ListSelectionListener add(
    ListSelectionListener a, ListSelectionListener b)
  {
    return (ListSelectionListener)addInternal(a, b);
  }

  public static ListSelectionListener remove(
    ListSelectionListener l, ListSelectionListener oldl)
  {
    return (ListSelectionListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   MenuDragMouseListener
// ---------------------------------------------------

  public void menuDragMouseEntered(MenuDragMouseEvent event)
  {
    ((MenuDragMouseListener)a).menuDragMouseEntered(event);
    ((MenuDragMouseListener)b).menuDragMouseEntered(event);
  }

  public void menuDragMouseExited(MenuDragMouseEvent event)
  {
    ((MenuDragMouseListener)a).menuDragMouseExited(event);
    ((MenuDragMouseListener)b).menuDragMouseExited(event);
  }

  public void menuDragMouseDragged(MenuDragMouseEvent event)
  {
    ((MenuDragMouseListener)a).menuDragMouseDragged(event);
    ((MenuDragMouseListener)b).menuDragMouseDragged(event);
  }
  
  public void menuDragMouseReleased(MenuDragMouseEvent event)
  {
    ((MenuDragMouseListener)a).menuDragMouseReleased(event);
    ((MenuDragMouseListener)b).menuDragMouseReleased(event);
  }
  
  public static MenuDragMouseListener add(
    MenuDragMouseListener a, MenuDragMouseListener b)
  {
    return (MenuDragMouseListener)addInternal(a, b);
  }

  public static MenuDragMouseListener remove(
    MenuDragMouseListener l, MenuDragMouseListener oldl)
  {
    return (MenuDragMouseListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   MenuKeyListener
// ---------------------------------------------------

  public void menuKeyTyped(MenuKeyEvent event)
  {
    ((MenuKeyListener)a).menuKeyTyped(event);
    ((MenuKeyListener)b).menuKeyTyped(event);
  }

  public void menuKeyPressed(MenuKeyEvent event)
  {
    ((MenuKeyListener)a).menuKeyPressed(event);
    ((MenuKeyListener)b).menuKeyPressed(event);
  }

  public void menuKeyReleased(MenuKeyEvent event)
  {
    ((MenuKeyListener)a).menuKeyReleased(event);
    ((MenuKeyListener)b).menuKeyReleased(event);
  }
    
  public static MenuKeyListener add(
    MenuKeyListener a, MenuKeyListener b)
  {
    return (MenuKeyListener)addInternal(a, b);
  }

  public static MenuKeyListener remove(
    MenuKeyListener l, MenuKeyListener oldl)
  {
    return (MenuKeyListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   MenuListener
// ---------------------------------------------------

  public void menuSelected(MenuEvent event)
  {
    ((MenuListener)a).menuSelected(event);
    ((MenuListener)b).menuSelected(event);
  }

  public void menuDeselected(MenuEvent event)
  {
    ((MenuListener)a).menuDeselected(event);
    ((MenuListener)b).menuDeselected(event);
  }
  
  public void menuCanceled(MenuEvent event)
  {
    ((MenuListener)a).menuCanceled(event);
    ((MenuListener)b).menuCanceled(event);
  }
    
  public static MenuListener add(
    MenuListener a, MenuListener b)
  {
    return (MenuListener)addInternal(a, b);
  }

  public static MenuListener remove(
    MenuListener l, MenuListener oldl)
  {
    return (MenuListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   PopupMenuListener
// ---------------------------------------------------

  public void popupMenuWillBecomeVisible(PopupMenuEvent event)
  {
    ((PopupMenuListener)a).popupMenuWillBecomeVisible(event);
    ((PopupMenuListener)b).popupMenuWillBecomeVisible(event);
  }

  public void popupMenuWillBecomeInvisible(PopupMenuEvent event)
  {
    ((PopupMenuListener)a).popupMenuWillBecomeInvisible(event);
    ((PopupMenuListener)b).popupMenuWillBecomeInvisible(event);
  }
  
  public void popupMenuCanceled(PopupMenuEvent event)
  {
    ((PopupMenuListener)a).popupMenuCanceled(event);
    ((PopupMenuListener)b).popupMenuCanceled(event);
  }
  
  public static PopupMenuListener add(
    PopupMenuListener a, PopupMenuListener b)
  {
    return (PopupMenuListener)addInternal(a, b);
  }

  public static PopupMenuListener remove(
    PopupMenuListener l, PopupMenuListener oldl)
  {
    return (PopupMenuListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   TableColumnModelListener
// ---------------------------------------------------

  public void columnAdded(TableColumnModelEvent event)
  {
    ((TableColumnModelListener)a).columnAdded(event);
    ((TableColumnModelListener)b).columnAdded(event);
  }

  public void columnRemoved(TableColumnModelEvent event)
  {
    ((TableColumnModelListener)a).columnRemoved(event);
    ((TableColumnModelListener)b).columnRemoved(event);
  }
  
  public void columnMoved(TableColumnModelEvent event)
  {
    ((TableColumnModelListener)a).columnMoved(event);
    ((TableColumnModelListener)b).columnMoved(event);
  }
  
  public void columnMarginChanged(ChangeEvent event)
  {
    ((TableColumnModelListener)a).columnMarginChanged(event);
    ((TableColumnModelListener)b).columnMarginChanged(event);
  }
  
  public void columnSelectionChanged(ListSelectionEvent event)
  {
    ((TableColumnModelListener)a).columnSelectionChanged(event);
    ((TableColumnModelListener)b).columnSelectionChanged(event);
  }
  
  public static TableColumnModelListener add(
    TableColumnModelListener a, TableColumnModelListener b)
  {
    return (TableColumnModelListener)addInternal(a, b);
  }

  public static TableColumnModelListener remove(
    TableColumnModelListener l, TableColumnModelListener oldl)
  {
    return (TableColumnModelListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   TableModelListener
// ---------------------------------------------------

  public void tableChanged(TableModelEvent event)
  {
    ((TableModelListener)a).tableChanged(event);
    ((TableModelListener)b).tableChanged(event);
  }
  
  public static TableModelListener add(
    TableModelListener a, TableModelListener b)
  {
    return (TableModelListener)addInternal(a, b);
  }

  public static TableModelListener remove(
    TableModelListener l, TableModelListener oldl)
  {
    return (TableModelListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   TreeExpansionListener
// ---------------------------------------------------

  public void treeExpanded(TreeExpansionEvent event)
  {
    ((TreeExpansionListener)a).treeExpanded(event);
    ((TreeExpansionListener)b).treeExpanded(event);
  }

  public void treeCollapsed(TreeExpansionEvent event)
  {
    ((TreeExpansionListener)a).treeCollapsed(event);
    ((TreeExpansionListener)b).treeCollapsed(event);
  }

  public static TreeExpansionListener add(
    TreeExpansionListener a, TreeExpansionListener b)
  {
    return (TreeExpansionListener)addInternal(a, b);
  }

  public static TreeExpansionListener remove(
    TreeExpansionListener l, TreeExpansionListener oldl)
  {
    return (TreeExpansionListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   TreeModelListener
// ---------------------------------------------------

  public void treeNodesChanged(TreeModelEvent event)
  {
    ((TreeModelListener)a).treeNodesChanged(event);
    ((TreeModelListener)b).treeNodesChanged(event);
  }

  public void treeNodesInserted(TreeModelEvent event)
  {
    ((TreeModelListener)a).treeNodesInserted(event);
    ((TreeModelListener)b).treeNodesInserted(event);
  }

  public void treeNodesRemoved(TreeModelEvent event)
  {
    ((TreeModelListener)a).treeNodesRemoved(event);
    ((TreeModelListener)b).treeNodesRemoved(event);
  }

  public void treeStructureChanged(TreeModelEvent event)
  {
    ((TreeModelListener)a).treeStructureChanged(event);
    ((TreeModelListener)b).treeStructureChanged(event);
  }
    
  public static TreeModelListener add(
    TreeModelListener a, TreeModelListener b)
  {
    return (TreeModelListener)addInternal(a, b);
  }

  public static TreeModelListener remove(
    TreeModelListener l, TreeModelListener oldl)
  {
    return (TreeModelListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   TreeSelectionListener
// ---------------------------------------------------

  public void valueChanged(TreeSelectionEvent event)
  {
    ((TreeSelectionListener)a).valueChanged(event);
    ((TreeSelectionListener)b).valueChanged(event);
  }
  
  public static TreeSelectionListener add(
    TreeSelectionListener a, TreeSelectionListener b)
  {
    return (TreeSelectionListener)addInternal(a, b);
  }

  public static TreeSelectionListener remove(
    TreeSelectionListener l, TreeSelectionListener oldl)
  {
    return (TreeSelectionListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   TreeWillExpandListener
// ---------------------------------------------------

  public void treeWillExpand(TreeExpansionEvent event)
    throws ExpandVetoException
  {
    ((TreeWillExpandListener)a).treeWillExpand(event);
    ((TreeWillExpandListener)b).treeWillExpand(event);
  }

  public void treeWillCollapse(TreeExpansionEvent event)
    throws ExpandVetoException
  {
    ((TreeWillExpandListener)a).treeWillCollapse(event);
    ((TreeWillExpandListener)b).treeWillCollapse(event);
  }
    
  public static TreeWillExpandListener add(
    TreeWillExpandListener a, TreeWillExpandListener b)
  {
    return (TreeWillExpandListener)addInternal(a, b);
  }

  public static TreeWillExpandListener remove(
    TreeWillExpandListener l, TreeWillExpandListener oldl)
  {
    return (TreeWillExpandListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   UndoableEditListener
// ---------------------------------------------------

  public void undoableEditHappened(UndoableEditEvent event)
  {
    ((UndoableEditListener)a).undoableEditHappened(event);
    ((UndoableEditListener)b).undoableEditHappened(event);
  }
  
  public static UndoableEditListener add(
    UndoableEditListener a, UndoableEditListener b)
  {
    return (UndoableEditListener)addInternal(a, b);
  }

  public static UndoableEditListener remove(
    UndoableEditListener l, UndoableEditListener oldl)
  {
    return (UndoableEditListener)removeInternal(l, oldl);
  }

// ---------------------------------------------------
//   Internal Methods
// ---------------------------------------------------
  
  protected static EventListener addInternal(EventListener a, EventListener b)
  {
    if (a == null)  return b;
    if (b == null)  return a;
    return new SwingEventMulticaster(a, b);
  }

  protected static EventListener removeInternal(EventListener l, EventListener oldl)
  {
    if (l == oldl || l == null)
    {
      return null;
    }
    else if (l instanceof SwingEventMulticaster)
    {
      return ((SwingEventMulticaster)l).remove(oldl);
    }
    else
    {
      return l;
    }
  }

}

