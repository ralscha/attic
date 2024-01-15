package ar.com.koalas.providers.taglibs.comboselect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.struts.taglib.TagUtils;

public class ComboSelectTag extends BodyTagSupport {
  private static final String ID_COMBO = ComboSelectTag.class.getName() + ".idCombo";
  private String property = null;
  private String name = null;
  private String metadata;
  private String[] metadataArray;
  private String[] cmbnames;
  private String[] cmbvalues;
  private String[] cmbonchange;
  private int id;
  private String addAllLabel = null;
  private String addSelectLabel = null;
  private List<Ddl> v = null;
  private Map<String, SelectData> selectData = null;

  public void addDdl(Ddl d) {
    v.add(d);
  }

  public void putSelectData(String property1, SelectData data) {
    this.selectData.put(property1, data);
  }

  @SuppressWarnings("unchecked")
  private Iterator getFilteredIterator(final String parentKey, Map<String, Object> item, SelectData data) {
    if (parentKey == null)
      return data.getIterator();

    Iterator sdItInput = data.getIterator();
    //pasa el iterator a una coleccion.
    Collection colAux = CollectionUtils.collect(sdItInput, new Transformer() {
      public Object transform(Object arg0) {
        return arg0;
      }
    });
    data.setIterator(colAux.iterator());
    //filtra solo los que coincidan con el key del padre
    final Object parentKeyValue = item.get(parentKey);
    Collection filtered = CollectionUtils.select(colAux, new Predicate() {
      public boolean evaluate(Object obj) {
        try {
          Object key = BeanUtils.getProperty(obj, parentKey);
          //TODO controlar null, tipos, etc...
          return parentKeyValue.equals(key);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return false;
      }
    });

    return filtered.iterator();
  }

  @SuppressWarnings("unchecked")
  private void populateMatrix(Collection<HashMap<String, Object>> col, Map<String, Object> item, Ddl ddl, int[] level, String parentKey) {
    SelectData data = this.selectData.get(ddl.getProperty());
    Iterator sdIt = this.getFilteredIterator(parentKey, item, data);

    while (sdIt.hasNext()) {
      Object obj = sdIt.next();
      try {
        Object key = BeanUtils.getProperty(obj, data.getKey());
        Object description = BeanUtils.getProperty(obj, data.getDescription());
        item.put(data.getKey(), key);
        item.put(data.getDescription(), description);
      } catch (Exception e) {
        e.printStackTrace();
      }
      level[0]++;
      if (level[0] < v.size()) {
        Ddl ddlnext = v.get(level[0]);
        this.populateMatrix(col, item, ddlnext, level, data.getKey());
        level[0]--;
      } else {
        level[0]--;
        col.add(new HashMap<String, Object>(item));
      }
    }
  }

  @SuppressWarnings("unchecked")
  private Collection<HashMap<String, Object>> getPropertyFromContext() {
    //primero me fijo si tengo selectData
    if (this.selectData.size() > 0) {
      Ddl ddlroot = v.get(0); //obtiene el primero
      Collection<HashMap<String, Object>> col = new ArrayList<HashMap<String, Object>>();
      this.populateMatrix(col, new HashMap<String, Object>(), ddlroot, new int[]{0}, null);
      return col;
    }
    // Identify the bean containing our collection
    Object bean = null;
    try {
      if (name != null)
        if (property != null)
          bean = TagUtils.getInstance().lookup(pageContext, name, property, "request");
        else
          bean = TagUtils.getInstance().lookup(pageContext, name, "request");
      else
        bean = TagUtils.getInstance().lookup(pageContext, property, "request");
    } catch (Exception ex) {
      // noaction
    }
    if (bean == null) {
      try {
        if (name != null)
          if (property != null)
            bean = TagUtils.getInstance().lookup(pageContext, name, property, "session");
          else
            bean = TagUtils.getInstance().lookup(pageContext, name, "session");
        else
          bean = TagUtils.getInstance().lookup(pageContext, property, "session");
      } catch (Exception ex) {
        // noaction
      }
    }
    if (bean == null && this.property != null)
      bean = pageContext.findAttribute(this.property);

    if (bean == null)
      return null;

    Collection<HashMap<String, Object>> col = (Collection<HashMap<String, Object>>)bean;
    if (col.size() == 0) {
      return null;
    }
    return col;

  }

  public void setProperty(String c) {
    this.property = c;
  }

  public void setName(String c) {
    this.name = c;
  }

  public String getName() {
    return this.name;
  }

  public void setMetadata(String c) {
    this.metadata = c;
  }

  private void loadSelectData() throws JspException {
    //obtiene el vector de Ddl's
    if (v == null) {
      throw new JspException("No se informaron selects");
    }
    cmbnames = new String[v.size()];
    cmbvalues = new String[v.size()];
    cmbonchange = new String[v.size()];
    for (int i = 0; i < v.size(); i++) {
      cmbnames[i] = v.get(i).getName();
      cmbvalues[i] = v.get(i).getValue();
      cmbonchange[i] = v.get(i).getOnChange();
    }
  }

  @Override
  public int doStartTag() {
    this.v = new ArrayList<Ddl>();
    this.selectData = new HashMap<String, SelectData>();
    return (EVAL_BODY_INCLUDE);
  }

  @Override
  public int doEndTag() throws JspException {
    metadataArray = null;
    this.validate();
    HttpServletRequest request = ((HttpServletRequest)pageContext.getRequest());
    Collection<HashMap<String, Object>> elementos = this.getPropertyFromContext();
    if (elementos == null)
      return EVAL_PAGE;

    //Determina el Id según variables del request.
    Integer value = (Integer)((HttpServletRequest)pageContext.getRequest()).getAttribute(ID_COMBO);
    if (value == null) {
      //es el primer tag utilizado en el request.
      id = 0;
    } else {
      id = value.intValue();
      id++;
    }
    request.setAttribute(ID_COMBO, new Integer(id));

    this.loadSelectData();
    TreeItem ddl = null;
    //Obtiene el TreeItem desde una colleccion de DynamicBeans
    try {
      if ("true".equalsIgnoreCase(this.getAddAllLabel()))
        ddl = TreeItemFactory.getInstance().createFromCollectionWithAllLabel(request, elementos,
            this.getMetadataArray());
      else if ("true".equalsIgnoreCase(this.getAddSelectLabel()))
        ddl = TreeItemFactory.getInstance().createFromCollectionWithSelectLabel(request, elementos,
            this.getMetadataArray());
      else
        ddl = TreeItemFactory.getInstance().createFromCollection(request, elementos, this.getMetadataArray());
    } catch (Exception e) {
      throw new JspException("Error al obtener el arbol: " + e.toString());
    }

    StringBuffer sb = new StringBuffer("");
    sb.append(this.generateObjects());
    sb.append("<script type=\"text/javascript\">\n");
    sb.append(this.generateDeclaration());
    sb.append(this.generateTree(ddl));
    sb.append(this.generateRefillFunction());
    sb.append("refill");
    sb.append(id);
    sb.append("(-1, 0);\n");
    //limpia los valores iniciales.
    for (int i = 0; i < cmbvalues.length; i++) {
      sb.append("selvalue");
      sb.append(id);
      sb.append("[");
      sb.append(i);
      sb.append("]=\"\";\n");
    }

    sb.append("</script>");
    //System.out.println(sb.toString());
    try {
      pageContext.getOut().write(sb.toString());
    } catch (Exception e) {
      throw new JspException("IO Error: " + e.getMessage());
    }
    return EVAL_PAGE;
  }

  private String generateDeclaration() {
    StringBuffer sb = new StringBuffer("");

    //Nombres, valores y onchange de los selects
    sb.append("var selname");
    sb.append(id);
    sb.append("=new Array(");
    sb.append(cmbnames.length);
    sb.append(");\n");

    sb.append("var selvalue");
    sb.append(id);
    sb.append("=new Array(");
    sb.append(cmbvalues.length);
    sb.append(");\n");

    for (int i = 0; i < cmbnames.length; i++) {
      sb.append("select").append(id).append(i).append("=").append(cmbnames[i]).append(";\n");
      sb.append("selname").append(id).append("[").append(i).append("]=\"").append("select").append(id).append(i)
          .append("\";\n");
      sb.append("func").append(id).append(i).append("=new Function();\n");
      sb.append("if(select").append(id).append(i).append(".onchange) {\n");
      sb.append("func").append(id).append(i).append("=select").append(id).append(i).append(".onchange;\n}\n");

      sb.append("function refill").append(id).append(i).append("() {\n");
      sb.append("select").append(id).append("=").append(cmbnames[i]).append(";\n");
      sb.append("\nrefill").append(id).append("(").append(i).append(", ").append("select").append(id).append(
          ".selectedIndex); ");
      sb.append(cmbonchange[i] != null ? cmbonchange[i] : "");
      sb.append("\nfunc").append(id).append(i).append("();\n");
      sb.append("}\n");

      sb.append("select");
      sb.append(id);
      sb.append(i);
      sb.append(".onchange=refill");
      sb.append(id);
      sb.append(i);
      sb.append(";\n");

      sb.append("selvalue");
      sb.append(id);
      sb.append("[");
      sb.append(i);
      sb.append("]=\"");
      sb.append(cmbvalues[i]);
      sb.append("\";\n");
    }

    return sb.toString();
  }

  private String generateTree(TreeItem ddl) {
    StringBuffer sb = new StringBuffer("");
    sb.append("\nvar ddl");
    sb.append(id);
    sb.append("=new TreeItem(\"ROOT\", \"ROOT\");\n");

    //Recorre el arbol de ítems para generar el código.
    generate(sb, ddl, "ddl" + id);

    return sb.toString();
  }

  @SuppressWarnings("unchecked")
  private void generate(StringBuffer sb, TreeItem ddl, String parent) {
    Iterator it = ddl.rel.iterator();
    int i = 0;
    while (it.hasNext()) {
      TreeItem ddlaux = (TreeItem)it.next();
      String newParent = parent + "_" + i;
      sb.append(newParent);
      sb.append("=");
      sb.append(parent);
      sb.append(".add(\"");
      //MM: Reemplazo ' por " para evitar errores de JScript.
      sb.append(ddlaux.getDescrip().replace('"', '\''));
      sb.append("\", \"");
      sb.append(ddlaux.getId());
      sb.append("\");\n");
      i++;
      generate(sb, ddlaux, newParent);
    }
  }

  private String generateRefillFunction() {
    StringBuffer sb = new StringBuffer("");
    sb.append("function refill");
    sb.append(id);
    sb.append("(sel, index) { selCount=selname");
    sb.append(id);
    sb.append(".length;\n");
    sb.append("if(sel==-1) selCount-- ;\n");
    sb.append("for(selidx=sel;selidx<selCount;selidx++) {\n");
    sb.append("level=selidx+1;select=eval(selname");
    sb.append(id);
    sb.append("[level]);\nif(select) {\n");
    sb.append("saux=\"ddl");
    sb.append(id);
    sb.append("\";\n");
    sb.append("for(i=0;i<level;i++) { saux=saux + \".rel\"; if(i<level) { saux=saux + \"[\" + selname");
    sb.append(id);
    sb.append("[i] + \".selectedIndex]\";} else {saux=saux + \"[index]\";}}\n");
    sb.append("aux=eval(saux);	for (m=select.options.length-1;m>=0;m--) {select.options[m]=null;}\n");
    sb
        .append("indice=0;\nfor (i=0;i<aux.rel.length;i++){	select.options[i]=new Option(aux.rel[i].descrip, aux.rel[i].id);\nif(selvalue");
    sb.append(id);
    sb.append("[level] == aux.rel[i].id) indice=i;\n} select.options[indice].selected=true;} }}\n");

    return sb.toString();
  }

  public String generateObjects() {
    StringBuffer sb = new StringBuffer("");
    //verifica si en el request ya no apareció la declaración.
    Object value = ((HttpServletRequest)pageContext.getRequest()).getAttribute("genScript");
    if (value == null) {
      sb.append("<script type=\"text/javascript\">\n");
      sb.append("function addItem(descrip, id) {\n");
      sb.append("  ddlaux=new TreeItem(descrip, id);\n");
      sb.append("  this.rel[this.rel.length]=ddlaux;\n");
      sb.append("  return ddlaux;\n}\n");
      sb.append("function TreeItem(descrip, id) {\n");
      sb.append("  this.id=id;\n");
      sb.append("  this.descrip=descrip;\n");
      sb.append("  this.rel=new Array();\n");
      sb.append("  this.add=addItem;\n}\n");
      sb.append("</script>\n");
      ((HttpServletRequest)pageContext.getRequest()).setAttribute("genScript", "TRUE");
    }
    return sb.toString();
  }

  private void validate() throws JspException {
    if (this.getMetadataArray().length % 2 != 0)
      throw new JspException("Metadata must have an even number of properties");

    if ((name != null || property != null) && this.getMetadataArray() == null)
      throw new JspException("Metadata must be supplied");

  }

  /**
   * Returns the addAllLabel.
   * @return String
   */
  public String getAddAllLabel() {
    return addAllLabel;
  }

  /**
   * Sets the addAllLabel.
   * @param addAllLabel The addAllLabel to set
   */
  public void setAddAllLabel(String addAllLabel) {
    this.addAllLabel = addAllLabel;
  }

  /**
   * @return
   */
  public String getAddSelectLabel() {
    return addSelectLabel;
  }

  /**
   * @param string
   */
  public void setAddSelectLabel(String string) {
    addSelectLabel = string;
  }

  @Override
  public void release() {
    property = null;
    name = null;
    metadata = null;
    metadataArray = null;
    cmbnames = null;
    cmbvalues = null;
    cmbonchange = null;
    addAllLabel = null;
    addSelectLabel = null;
    v = null;
  }

  /**
   * @return Returns the metadata.
   */
  public String[] getMetadataArray() {
    if (metadataArray == null) {
      if (this.metadata != null) {
        StringTokenizer st = new StringTokenizer(this.metadata, ",");
        metadataArray = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
          metadataArray[i] = st.nextToken().trim();
          i++;
        }
      } else if (this.selectData.size() > 0) {
        metadataArray = new String[this.selectData.size() * 2];
        Iterator<String> it = this.selectData.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
          Object key = it.next();
          metadataArray[i] = this.selectData.get(key).getKey();
          i++;
          metadataArray[i] = this.selectData.get(key).getDescription();
          i++;
        }
      }
    }
    return metadataArray;
  }
}
