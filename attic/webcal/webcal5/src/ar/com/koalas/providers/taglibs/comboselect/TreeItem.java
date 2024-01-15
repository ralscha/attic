/**
 * Clase nodo de arboles para utilizar en el taglib comboselect.
 * El contenido de este arbol se traduce a código JavaScript mediante
 * comboselect que permite el manejo de n combos select html, siendo
 * n la profundidad del árbol formado por nodos TreeItem.
 *
 * @author Guillermo Meyer
 **/
package ar.com.koalas.providers.taglibs.comboselect;

import java.util.Iterator;
import java.util.Vector;

public class TreeItem implements java.io.Serializable {
  private String id = "";
  private String descrip = "";
  /**
   * Vector público de los elementos TreeItem relacionados.
   **/
  public Vector<TreeItem> rel = null;

  /**
   * Recibe la descripción y id. Esto será el contenido de un OPTION
   * HTML, donde id será el value del OPTION, y descrip la descripción.
   **/
  public TreeItem(String descrip, String id) {
    this.descrip = descrip;
    this.id = id;
    rel = new Vector<TreeItem>();
  }

  /**
   * Devuelve el id. del Item (OPTION HTML)
   **/
  public String getId() {
    return id;
  }

  /**
   * Devuelve la descripción del Item (OPTION HTML)
   **/
  public String getDescrip() {
    return descrip;
  }

  public TreeItem add(int posicion, String descrip1, String id1) {
    TreeItem d = new TreeItem(descrip1, id1);
    this.add(posicion, d);
    return d;
  }

  /**
   * Agrega un elemento TreeItem a los relacionados del actual.
   **/
  public TreeItem add(String descrip1, String id1) {
    TreeItem d = new TreeItem(descrip1, id1);
    this.add(d);
    return d;
  }

  public TreeItem add(int posicion, TreeItem d) {
    rel.add(posicion, d);
    return d;
  }

  /**
   * Agrega un elemento TreeItem a los relacionados del actual.
   **/
  public TreeItem add(TreeItem d) {
    rel.add(d);
    return d;
  }

  public TreeItem search(String id1) {
    if (this.getId().equals(id1))
      return this;

    for (int i = 0; i < this.rel.size(); i++) {
      TreeItem item = this.rel.elementAt(i);
      TreeItem retValue = item.search(id1);

      if (retValue != null)
        return retValue;
    }

    return null;
  }

  public TreeItem getNodoHijo(String clave) {
    TreeItem tree = null;
    Iterator<TreeItem> it = rel.iterator();
    while (it.hasNext()) {
      tree = it.next();
      if (tree.getId().equals(clave))
        return tree;
    }
    return null;
  }

  @Override
  protected Object clone() {
    TreeItem ti = new TreeItem(this.getDescrip(), this.getId());
    Iterator<TreeItem> it = this.rel.iterator();
    while (it.hasNext()) {
      TreeItem hijo = it.next();
      ti.add((TreeItem)hijo.clone());
    }
    return ti;
  }

  public int getProfundidad() {
    if (rel.size() > 0) {
      int profundidadHijoMaxima = 0;
      Iterator<TreeItem> it = rel.iterator();
      while (it.hasNext()) {
        TreeItem treeHiijo = it.next();
        int profundidadHijo = treeHiijo.getProfundidad();
        if (profundidadHijo > profundidadHijoMaxima) {
          profundidadHijoMaxima = profundidadHijo;
        }
      }
      return profundidadHijoMaxima + 1;
    }
    return 0;

  }

}
