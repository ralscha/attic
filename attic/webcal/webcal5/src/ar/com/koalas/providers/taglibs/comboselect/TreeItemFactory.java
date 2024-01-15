/**
 * Clase nodo de arboles para utilizar en el taglib comboselect.
 * El contenido de este arbol se traduce a código JavaScript mediante
 * comboselect que permite el manejo de n combos select html, siendo
 * n la profundidad del árbol formado por nodos TreeItem.
 *
 * @author Guillermo Meyer
 **/
package ar.com.koalas.providers.taglibs.comboselect;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class TreeItemFactory implements java.io.Serializable {
  private static TreeItemFactory instance = null;

  /**
   * Recibe la descripción y id. Esto será el contenido de un OPTION
   * HTML, donde id será el value del OPTION, y descrip la descripción.
   **/
  private TreeItemFactory() {
    //  noaction
  }

  public static TreeItemFactory getInstance() {
    if (instance == null)
      instance = new TreeItemFactory();
    return instance;
  }

  @SuppressWarnings("unchecked")
  public TreeItem createFromCollection(HttpServletRequest request, Collection dbCol, String[] props) throws Exception {
    TreeItem ti = new TreeItem("root", "root");
    if (dbCol.size() == 0)
      return ti;

    Object clave;
    Object descrip;

    Iterator registros = dbCol.iterator();
    while (registros.hasNext()) {
      Object db = registros.next();

      TreeItem treeAux = ti;
      for (int i = 0; i < props.length; i = i + 2) {
        String prop1 = props[i];
        String prop2 = props[i + 1];
        clave = BeanUtils.getProperty(db, prop1);
        descrip = BeanUtils.getProperty(db, prop2);
        String strClave = (clave != null ? clave.toString() : "");
        String strDescrip = (descrip != null ? descrip.toString() : "");
        TreeItem nodo = treeAux.getNodoHijo(strClave);
        if (nodo == null) {
          nodo = new TreeItem(strDescrip, strClave);
          treeAux.add(nodo);
        }
        treeAux = nodo;
      }
    }
    return ti;
  }

  @SuppressWarnings("unchecked")
  public TreeItem createFromCollectionWithAllLabel(HttpServletRequest request, Collection dbCol, String[] props)
      throws Exception {
    return createFromCollectionWithAllLabel(request, dbCol, props, 1);
  }

  @SuppressWarnings("unchecked")
  public TreeItem createFromCollectionWithAllLabel(HttpServletRequest request, Collection dbCol, String[] props,
      int nivelComienzaTodos) throws Exception {
    TreeItem ti = createFromCollection(request, dbCol, props);
    addTodos(request, ti, nivelComienzaTodos, 1);
    return ti;
  }

  @SuppressWarnings("unchecked")
  public TreeItem createFromCollectionWithSelectLabel(HttpServletRequest request, Collection dbCol, String[] props)
      throws Exception {
    return createFromCollectionWithSelectLabel(request, dbCol, props, 1, false);
  }

  /**
   * addSeleccioneAnyway indica que agrege seleccione en el ultimo nivel si o si.
   * 
   * @param dbCol
   * @param props
   * @param addSeleccioneAnyway
   * @return
   * @throws X71Exception
   */
  @SuppressWarnings("unchecked")
  public TreeItem createFromCollectionWithSelectLabel(HttpServletRequest request, Collection dbCol, String[] props,
      boolean addSeleccioneAnyway) throws Exception {
    return createFromCollectionWithSelectLabel(request, dbCol, props, 1, addSeleccioneAnyway);
  }

  @SuppressWarnings("unchecked")
  public TreeItem createFromCollectionWithSelectLabel(HttpServletRequest request, Collection dbCol, String[] props,
      int nivelComienzaSelecccione, boolean addSeleccioneAnyway) throws Exception {
    TreeItem ti = createFromCollection(request, dbCol, props);
    addSeleccione(request, ti, nivelComienzaSelecccione, 1, props.length / 2, addSeleccioneAnyway);
    return ti;
  }

  /**
   *
   * @param ti
   * @param firstLevel indica el nivel del arbol a partir del cual se agrega el nodo <Todos>
   * @param currentLevel
   * @throws X71Exception
   */
  @SuppressWarnings("unchecked")
  private void addTodos(HttpServletRequest request, TreeItem ti, int firstLevel, int currentLevel) throws Exception {
    //Si el nodo no tiene hijos no hago nada
    if (ti.rel.size() > 0) {
      //Creo un nodo vacio para agregar como hijo del arbol
      TreeItem nodoCeroHijo = new TreeItem(getTodosLabel(request), "0");
      //Recorro los hijos del arbol, son los hermanos del nodo vacio
      Iterator it = ti.rel.iterator();
      while (it.hasNext()) {
        TreeItem hermano = (TreeItem)it.next();
        addTodos(request, hermano, firstLevel, currentLevel + 1);//Proceso cada hermano
        //Si estoy en un nivel >= al nivel a partir del cual debo poner el nodo vacio
        if (firstLevel <= currentLevel) {
          //Recorro los hijos del hermano del nodo vacio
          Iterator itHermano = hermano.rel.iterator();
          while (itHermano.hasNext()) {//Copio los hijos del hermano
            TreeItem hijoHermano = (TreeItem)itHermano.next();
            //Hago una copia del nodo, no me sirve usar el mismo nodo porque lo tengo que modificar
            TreeItem t = (TreeItem)hijoHermano.clone();
            TreeItem t1 = nodoCeroHijo.getNodoHijo(t.getId());
            //Si el nodo vacio ya tiene un hijo con la misma clave
            //debo fusionar los subarboles, sino se lo agrego
            if (t1 != null)
              fusionarTrees(t1, t);
            else
              nodoCeroHijo.add(t);
          }
        }
      }
      //Si estoy en un nivel >= al nivel a partir del cual debo poner el nodo vacio
      if (firstLevel <= currentLevel)
        ti.add(0, nodoCeroHijo);
    }
  }

  /**
   *
   * @param ti
   * @param firstLevel indica el nivel del arbol a partir del cual se agrega el nodo <Todos>
   * @param currentLevel
   * @throws X71Exception
   */
  @SuppressWarnings("unchecked")
  private void addSeleccione(HttpServletRequest request, TreeItem ti, int firstLevel, int currentLevel, int lastLevel,
      boolean addSeleccioneAnyway) throws Exception {
    if (ti.rel.size() > 0) {
      //Si estoy en un nivel >= al nivel a partir del cual debo poner 
      //el nodo <Seleccione> y ademas hay mas de un elemento
      boolean hasAddedSeleccione = false;
      if ((currentLevel >= firstLevel) && (ti.rel.size() > 1)) {
        //Creo un nodo Seleccione para agregar como hijo del arbol
        hasAddedSeleccione = true;
        TreeItem nodoSeleccione = new TreeItem(getSeleccioneLabel(request), "0");
        TreeItem nodoActual = nodoSeleccione;
        int profundidadTree = ti.getProfundidad();
        for (int i = 0; i <= profundidadTree; i++) {
          //Creo un nodo Vacio para agregar como hijo del arbol
          TreeItem nodoVacio = new TreeItem("        ", "0");
          nodoActual.add(0, nodoVacio);
          nodoActual = nodoVacio;
        }
        ti.add(0, nodoSeleccione);
      }
      //Recorro los hijos del arbol, son los hermanos del nodo <Seleccione>
      Iterator it = ti.rel.iterator();
      while (it.hasNext()) {
        TreeItem hermano = (TreeItem)it.next();
        addSeleccione(request, hermano, firstLevel, currentLevel + 1, lastLevel, addSeleccioneAnyway);//Proceso cada hermano
      }
      //agrega seleccione siempre en el último nivel (esto podría parametrizarse, pero me sirve para los combps de cuentas
      //y no se usa en muchos lugares mas.
      if (addSeleccioneAnyway && !hasAddedSeleccione && currentLevel == lastLevel) {
        TreeItem nodoSeleccione = new TreeItem(getSeleccioneLabel(request), "0");
        ti.add(0, nodoSeleccione);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void fusionarTrees(TreeItem t1, TreeItem t2) {
    Iterator t2Iterator = t2.rel.iterator();
    while (t2Iterator.hasNext()) {
      TreeItem hijoTree2 = (TreeItem)t2Iterator.next();
      TreeItem hijoTree1 = t1.getNodoHijo(hijoTree2.getId());
      if (hijoTree1 == null)
        t1.add(hijoTree2);
      else { //Ya hay un nodo con la misma clave
        fusionarTrees(hijoTree1, hijoTree2);
      }
    }
  }

  private String getSeleccioneLabel(HttpServletRequest request) {
    //@TODO obtener del key el texto   	
    return "<Select>";
  }

  private String getTodosLabel(HttpServletRequest request) {
    //		@TODO obtener del key el texto
    return "<All>";
  }
}