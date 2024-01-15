package ch.ess.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.ClassProcessor;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SourceGenerator extends JFrame {

  private JTextField status;

  public SourceGenerator() {

    try {
      UIManager.setLookAndFeel(new PlasticXPLookAndFeel());

    } catch (Exception e) {
      // no action
    }

    setTitle("Source Generator");
    getContentPane().setLayout(new BorderLayout());

    FormLayout layout = new FormLayout("p, 3dlu, p, 3dlu, p:grow, 3dlu, p", 
        "p, 3dlu, fill:p:grow, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p");

    PanelBuilder builder = new PanelBuilder(layout);
    builder.setDefaultDialogBorder();

    CellConstraints cc = new CellConstraints();

    
    Set<String> locationFilters = new HashSet<String>();
    locationFilters.add("classes");
    BaseObjectProcessor boProcessor = new BaseObjectProcessor();
    OptionAnnotationProcessor optProcessor = new OptionAnnotationProcessor();
    
    ClassProcessor processor = new ClassProcessor(locationFilters);
    processor.addHierarchyProcessor(boProcessor);
    processor.addAnnotationProcessor(optProcessor);
    processor.process();

    final List<String> optionClasses = optProcessor.getClassList();
    
    ListIterator<String> li = boProcessor.getClassList().listIterator();
    while (li.hasNext()) {
      String className = li.next();
      if (className.startsWith("ch.ess.base.")) {
        li.remove();
      }
    }

    String[] classes = boProcessor.getClassList().toArray(new String[boProcessor.getClassList().size()]);
    final JTextField roleField = new JTextField(10);
    final JComboBox models = new JComboBox(classes);

    builder.add(models, cc.xy(1, 1));

    final PropertyDataModel model = new PropertyDataModel();

    JTable propertyTable = new JTable(model) {
      @Override
      public TableCellEditor getCellEditor(int row, int column) {
        int modelColumn = convertColumnIndexToModel(column);

        if (modelColumn == PropertyDataModel.COL_REFERENCE) {
          List<PropertyDescriptor> propList = ((PropertyDataModel)getModel()).getReferenceProperties(row);
          if (!propList.isEmpty()) {
            JComboBox cb = new JComboBox();
            cb.addItem("");
            for (PropertyDescriptor descr : propList) {
              cb.addItem(descr.getName());
            }
            return new DefaultCellEditor(cb);
          }
        } else if (modelColumn == PropertyDataModel.COL_MANYMANY) {
          
          List<PropertyDescriptor> propList = ((PropertyDataModel)getModel()).getManyReferenceProperties(row);
          if (!propList.isEmpty()) {
            JComboBox cb = new JComboBox();
            cb.addItem("");
            for (PropertyDescriptor descr : propList) {
              cb.addItem(descr.getName());
            }
            return new DefaultCellEditor(cb);
          }        
        }

        return super.getCellEditor(row, column);
      }
    };

    TableColumnModel tcm = propertyTable.getColumnModel();
    tcm.getColumn(PropertyDataModel.COL_NAME).setPreferredWidth(200);
    tcm.getColumn(PropertyDataModel.COL_TYP).setPreferredWidth(250);
    tcm.getColumn(PropertyDataModel.COL_CANDELETE).setPreferredWidth(70);
    tcm.getColumn(PropertyDataModel.COL_LISTE).setPreferredWidth(60);
    tcm.getColumn(PropertyDataModel.COL_FILTER).setPreferredWidth(70);
    tcm.getColumn(PropertyDataModel.COL_REFERENCE).setPreferredWidth(100);          
    tcm.getColumn(PropertyDataModel.COL_EDIT).setPreferredWidth(60);

    
    TableColumn filterColumn = propertyTable.getColumnModel().getColumn(PropertyDataModel.COL_LISTE);

    JComboBox comboBox = new JComboBox();
    comboBox.addItem("");
    for (int i = 1; i <= 20; i++) {
      comboBox.addItem(String.valueOf(i));
    }
    filterColumn.setCellEditor(new DefaultCellEditor(comboBox));

    TableColumn editColumn = propertyTable.getColumnModel().getColumn(PropertyDataModel.COL_EDIT);

    comboBox = new JComboBox();
    comboBox.addItem("");
    for (int i = 1; i <= 20; i++) {
      comboBox.addItem(String.valueOf(i));
    }
    editColumn.setCellEditor(new DefaultCellEditor(comboBox));
    
    
    final JButton create = new JButton("Create");
    create.setEnabled(false);

    JButton show = new JButton("Show");

    show.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          model.setSelectedClass((String)models.getSelectedItem());
          model.updateList(getDescriptors((String)models.getSelectedItem()));
          model.fireTableDataChanged();
          
          
          //int pos = model.getSelectedClass().indexOf(".model");
          //String name = model.getSelectedClass().substring(pos+7);
          //roleField.setText(name.toLowerCase());
         
          
          create.setEnabled(true);
        } catch (ClassNotFoundException e1) {
          e1.printStackTrace();
        }
      }
    });

    builder.add(show, cc.xy(3, 1));

    final JCheckBox generateCopyColumn = new JCheckBox("Generate Copy Column in List");
    final JCheckBox overwrite = new JCheckBox("Overwrite Files");
    final JCheckBox generateClearButton = new JCheckBox("Generate Clear Button in Search Filter");
    
    create.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          model.setCreateCopyColumn(generateCopyColumn.isSelected());
          model.setCreateClearButton(generateClearButton.isSelected());
          if (StringUtils.isNotBlank(roleField.getText())) {
            model.setRole(roleField.getText());
          } else {
            model.setRole(null);
          }
          model.setOverwrite(overwrite.isSelected());
          createFiles(model, optionClasses);
        } catch (IOException e1) {
          e1.printStackTrace();
        } catch (TemplateException e1) {
          e1.printStackTrace();
        } catch (SecurityException e1) {
          e1.printStackTrace();
        } catch (NoSuchMethodException e1) {
          e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
          e1.printStackTrace();
        }
      }
    });
    
    builder.add(create, cc.xy(7, 11));

    JScrollPane scrollPane = new JScrollPane(propertyTable);
    propertyTable.setPreferredScrollableViewportSize(new Dimension(600, 250));

    builder.add(scrollPane, cc.xyw(1, 3, 7));
    
    builder.add(generateCopyColumn, cc.xyw(1, 5, 3));
    builder.add(generateClearButton, cc.xyw(1, 7, 5));
    builder.add(overwrite, cc.xyw(1, 9, 3));
    
    JPanel p = new JPanel();
    p.setLayout(new FlowLayout(FlowLayout.LEFT));
    p.add(new JLabel("Role: "));
    p.add(roleField);
    builder.add(p, cc.xyw(1, 11, 3));

    getContentPane().add(builder.getPanel(), BorderLayout.CENTER);

    status = new JTextField("Ready");
    status.setEditable(false);
    getContentPane().add(status, BorderLayout.SOUTH);

    //Menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu();
    fileMenu.setText("File");

    JMenuItem exitItem = new JMenuItem();
    exitItem.setText("Exit");
    exitItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        System.exit(0);
      }
    });

    fileMenu.add(exitItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);

    //setSize(520, 350);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    pack();
  }

  List<PropertyDescriptor> getDescriptors(String qualifiedClassName) throws ClassNotFoundException {
    Class clazz = Class.forName(qualifiedClassName);
    PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);

    List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>();
    for (PropertyDescriptor descriptor : descriptors) {
      if (!"id".equals(descriptor.getName()) && !"version".equals(descriptor.getName())
          && !"class".equals(descriptor.getName())) {
        propList.add(descriptor);
      }
    }

    return propList;
  }

  void createFiles(PropertyDataModel model, List<String> optionClasses) throws IOException, TemplateException, SecurityException,
      NoSuchMethodException, ClassNotFoundException {

    String qualifiedClassName = model.getSelectedClass();
    Class clazz = Class.forName(qualifiedClassName);

    List<PropertyDescriptor> setList = new ArrayList<PropertyDescriptor>();
    List<PropertyDescriptor> propList = model.getPropertyList();

    List<OptionItem> optionList = new ArrayList<OptionItem>();
    List<PropertyItem> properties = new ArrayList<PropertyItem>();
    List<PropertyItem> listproperties = new ArrayList<PropertyItem>();
    List<PropertyItem> filterproperties = new ArrayList<PropertyItem>();

    boolean hasResetAttributes = false;
    boolean hasDates = false;
    boolean hasDatesInList = false;

    String titleReadMethod = "";
    String headProperty = "";

    int ix = 0;
    for (PropertyDescriptor descriptor : propList) {

      PropertyItem propertyItem = new PropertyItem();

      Method readMethod = clazz.getMethod(descriptor.getReadMethod().getName(), (Class[])null);

      propertyItem.setSize(40);

      if (readMethod.isAnnotationPresent(Column.class)) {
        Column column = readMethod.getAnnotation(Column.class);
        if (!column.nullable()) {
          propertyItem.setRequired(true);
        } else {
          propertyItem.setRequired(false);
        }
        propertyItem.setMaxLength(column.length());
      } else if (readMethod.isAnnotationPresent(JoinColumn.class)) {
        JoinColumn column = readMethod.getAnnotation(JoinColumn.class);
        if (!column.nullable()) {
          propertyItem.setRequired(true);
        } else {
          propertyItem.setRequired(false);
        }
      }

      if (descriptor.getPropertyType() == BigDecimal.class) {
        propertyItem.setMaxLength(10);
        propertyItem.setSize(10);
      } else if (descriptor.getPropertyType() == Integer.class) {
        propertyItem.setMaxLength(5);
        propertyItem.setSize(5);
      } else if (descriptor.getPropertyType() == Date.class) {
        propertyItem.setMaxLength(10);
        propertyItem.setSize(10);
      }

      propertyItem.setName(descriptor.getName());
      
  
      propertyItem.setReadMethod(descriptor.getReadMethod().getName());
      propertyItem.setWriteMethod(descriptor.getWriteMethod().getName());


      if (model.getCanDelete(ix)) {
        setList.add(descriptor);
      }
      
      if (StringUtils.isNotBlank(model.getRef(ix))) {
        propertyItem.setRefProperty(model.getRef(ix));
        
        List<PropertyDescriptor> refPropList = model.getReferenceProperties(ix);
        for (PropertyDescriptor item : refPropList) {
          if (item.getName().equals(propertyItem.getRefProperty())) {
            
            if (descriptor.getPropertyType() == Set.class) {
              Class genericTyp = model.getGenericType(descriptor, clazz);
              propertyItem.setTypOfSet(genericTyp.getSimpleName());
              
              if (StringUtils.isNotBlank(model.getManymanyref(ix))) {
                PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(item.getPropertyType());      
                PropertyDescriptor manyDescriptor = null;
                for (PropertyDescriptor descr : descriptors) {
                  if (descr.getName().equals(model.getManymanyref(ix))) {
                    manyDescriptor = descr;
                  }        
                }                
                propertyItem.setTypOfSet(item.getPropertyType().getSimpleName());
                propertyItem.setJoinReadMethod(manyDescriptor.getReadMethod().getName());   
                propertyItem.setJoinClassName(genericTyp.getSimpleName());
                propertyItem.setJoinImport(genericTyp.getName());
                genericTyp = manyDescriptor.getPropertyType();
                
                
                String modelImport = item.getPropertyType().getName();
                propertyItem.setRefImport(modelImport);
                
                int pos = modelImport.indexOf(".model");
                String daoImport = modelImport.substring(0, pos) + ".dao." + item.getPropertyType().getSimpleName() + "Dao";
                
                propertyItem.setRefDaoImport(daoImport);
                propertyItem.setRefReadProperty(item.getReadMethod().getName());
                
                OptionItem oi = new OptionItem();
                oi.setClassName(item.getPropertyType().getSimpleName());
                oi.setPackageName(modelImport.substring(0, pos));
                oi.setRefReadMethod(manyDescriptor.getReadMethod().getName());
                optionList.add(oi);                
              } else {
              
                String modelImport = genericTyp.getName();
                propertyItem.setRefImport(modelImport);
                
                int pos = modelImport.indexOf(".model");
                String daoImport = modelImport.substring(0, pos) + ".dao." + genericTyp.getSimpleName() + "Dao";
                
                propertyItem.setRefDaoImport(daoImport);
                propertyItem.setRefReadProperty(item.getReadMethod().getName());
                
                OptionItem oi = new OptionItem();
                oi.setClassName(genericTyp.getSimpleName());
                oi.setPackageName(modelImport.substring(0, pos));
                oi.setRefReadMethod(item.getReadMethod().getName());
                optionList.add(oi);
              }
            } else {
            
              String modelImport = descriptor.getPropertyType().getName();
              propertyItem.setRefImport(modelImport);
              
              int pos = modelImport.indexOf(".model");
              String daoImport = modelImport.substring(0, pos) + ".dao." + descriptor.getPropertyType().getSimpleName() + "Dao";
              
              propertyItem.setRefDaoImport(daoImport);
              propertyItem.setRefReadProperty(item.getReadMethod().getName());
                          
              OptionItem oi = new OptionItem();
              oi.setClassName(StringUtils.capitalize(propertyItem.getName()));
              oi.setPackageName(modelImport.substring(0, pos));
              oi.setRefReadMethod(item.getReadMethod().getName());
              optionList.add(oi);
            }
            
            break;
          }
        }
        
      } else {
        propertyItem.setRefProperty(null);
      }

      String typ = descriptor.getPropertyType().getName().replace("java.lang.", "");
      typ = typ.replace("java.util.", "");
      typ = typ.replace("java.math.", "");
      propertyItem.setTyp(typ);
      
      if (model.getFilter(ix)) {
        propertyItem.setFilter(true);
        filterproperties.add(propertyItem);
      }

      if (StringUtils.isNotBlank(model.getListe(ix))) {
        int sequenz = Integer.parseInt(model.getListe(ix));
        propertyItem.setListsequenz(sequenz);

        if (sequenz == 1) {
          titleReadMethod = descriptor.getReadMethod().getName();
        }

        if (descriptor.getPropertyType() == Date.class) {
          hasDatesInList = true;
        }

        listproperties.add(propertyItem);
      }

      if (StringUtils.isNotBlank(model.getEdit(ix))) {
        
        if (descriptor.getPropertyType() == Date.class) {
          hasDates = true;
        }
        
        if ((descriptor.getPropertyType() == Boolean.class) || (descriptor.getPropertyType() == boolean.class)) {
          hasResetAttributes = true;
          propertyItem.setFormTyp("boolean");
          propertyItem.setFormReadMethod("is" + descriptor.getName().substring(0, 1).toUpperCase()
              + descriptor.getName().substring(1));
        } else if (descriptor.getPropertyType() == Set.class) {
          propertyItem.setFormReadMethod(descriptor.getReadMethod().getName());
          propertyItem.setFormTyp("String[]");
          hasResetAttributes = true;
        } else {
          propertyItem.setFormReadMethod(descriptor.getReadMethod().getName());
          propertyItem.setFormTyp("String");
        }
        
        int sequenz = Integer.parseInt(model.getEdit(ix));
        
        propertyItem.setEditsequenz(sequenz);
        properties.add(propertyItem);
      }
      
      
      
      ix++;
    }
    
    Collections.sort(properties, new PropertyItemEditSequenzComparator());
    
    if (!properties.isEmpty()) {
      if (properties.get(0).isReference()) {
        headProperty = properties.get(0).getName() + "Id";
      } else {
        headProperty = properties.get(0).getName();
      }
    }
       
    Collections.sort(listproperties, new PropertyItemListSequenzComparator());

    int pos = qualifiedClassName.indexOf(".model");
    String packageName = qualifiedClassName.substring(0, pos);

    pos = qualifiedClassName.lastIndexOf(".");
    String className = qualifiedClassName.substring(pos + 1);
    String classNameSmall = StringUtils.uncapitalize(className);

    Configuration cfg = new Configuration();
    cfg.setClassForTemplateLoading(getClass(), "");

    cfg.setObjectWrapper(new DefaultObjectWrapper());

    Map<String, Object> root = new HashMap<String, Object>();

    root.put("package", packageName);
    root.put("className", className);
    root.put("listproperties", listproperties);
    root.put("filterproperties", filterproperties);
    root.put("properties", properties);
    root.put("hasResetAttributes", hasResetAttributes);
    root.put("titleReadMethod", titleReadMethod);
    root.put("hasDates", hasDates);
    root.put("hasDatesInList", hasDatesInList);
    root.put("jspExpressionStart", "${");
    root.put("jspExpressionEnd", "}");
    root.put("headProperty", headProperty);
    root.put("showCopyColumn", model.isCreateCopyColumn());
    root.put("showClearButton", model.isCreateClearButton());

    if (StringUtils.isNotBlank(model.getRole())) {
      root.put("role", "$"+model.getRole());
    }

    String canDeleteString = "";
    if (!setList.isEmpty()) {
      for (PropertyDescriptor descriptor : setList) {
        if (canDeleteString.length() > 0) {
          canDeleteString += " && ";
        }
        canDeleteString += "(size(" + classNameSmall + "." + descriptor.getReadMethod().getName() + "()) == 0)";
      }
      canDeleteString += ";";
    }

    root.put("canDelete", canDeleteString);

    Template template = cfg.getTemplate("dao.ftl");
    String outputDirString = packageName + ".dao";
    outputDirString = outputDirString.replace(".", "/");
    File outputDir = new File("./src/" + outputDirString);
    outputDir.mkdirs();
    File daoFile = new File(outputDir, className + "Dao.java");
    if (!daoFile.exists() || model.isOverwrite()) {
      BufferedWriter bw = new BufferedWriter(new FileWriter(daoFile));
      template.process(root, bw);
      bw.flush();
      bw.close();

    } else {
      System.out.println("Dao File existiert bereits");
    }

    template = cfg.getTemplate("listaction.ftl");
    outputDirString = packageName + ".web." + className.toLowerCase();
    outputDirString = outputDirString.replace(".", "/");
    outputDir = new File("./src/" + outputDirString);
    outputDir.mkdirs();
    File listFile = new File(outputDir, className + "ListAction.java");
    if (!listFile.exists() || model.isOverwrite()) {
      BufferedWriter bw = new BufferedWriter(new FileWriter(listFile));
      template.process(root, bw);
      bw.flush();
      bw.close();
    } else {
      System.out.println("ListAction existiert bereits");
    }

    template = cfg.getTemplate("form.ftl");
    File formFile = new File(outputDir, className + "Form.java");
    if (!formFile.exists() || model.isOverwrite()) {
      BufferedWriter bw = new BufferedWriter(new FileWriter(formFile));
      template.process(root, bw);
      bw.flush();
      bw.close();
    } else {
      System.out.println("Form existiert bereits");
    }

    template = cfg.getTemplate("editaction.ftl");
    File editFile = new File(outputDir, className + "EditAction.java");
    if (!editFile.exists() || model.isOverwrite()) {
      BufferedWriter bw = new BufferedWriter(new FileWriter(editFile));
      template.process(root, bw);
      bw.flush();
      bw.close();
    } else {
      System.out.println("EditAction existiert bereits");
    }

    template = cfg.getTemplate("editjsp.ftl");
    outputDir = new File("./web");
    outputDir.mkdirs();
    File editjspFile = new File(outputDir, className.toLowerCase() + "edit.jsp");
    if (!editjspFile.exists() || model.isOverwrite()) {
      BufferedWriter bw = new BufferedWriter(new FileWriter(editjspFile));
      template.process(root, bw);
      bw.flush();
      bw.close();
    } else {
      System.out.println("EditJSP existiert bereits");
    }

    template = cfg.getTemplate("listjsp.ftl");
    File listjspFile = new File(outputDir, className.toLowerCase() + "list.jsp");
    if (!listjspFile.exists() || model.isOverwrite()) {
      BufferedWriter bw = new BufferedWriter(new FileWriter(listjspFile));
      template.process(root, bw);
      bw.flush();
      bw.close();
    } else {
      System.out.println("ListJSP existiert bereits");
    }

    addFormToCleanSession(className + "Form");

    List<String> keys = new ArrayList<String>();
    keys.add(className.toLowerCase() + "list.title=" + className + "s");
    keys.add(className.toLowerCase() + "edit.title=" + className + " bearbeiten");
    keys.add(classNameSmall + ".menu=" + className + "s");
    keys.add(classNameSmall + "=" + className);
    keys.add(classNameSmall + "." + classNameSmall + "s=" + className + "s");

    for (PropertyItem prop : properties) {
      keys.add(classNameSmall + "." + prop.getName() + "=" + prop.getName().substring(0, 1).toUpperCase()
          + prop.getName().substring(1));
    }

    keys.add(classNameSmall + ".delete=" + className + " {0} löschen?");
    addToProperties(keys, className, new File("./src/ApplicationResources.properties"));
    addToProperties(keys, className, new File("./src/ApplicationResources_de.properties"));

    keys.clear();
    keys.add(className.toLowerCase() + "list.title=" + className + "s");
    keys.add(className.toLowerCase() + "edit.title=Edit " + className);
    keys.add(classNameSmall + ".menu=" + className + "s");
    keys.add(classNameSmall + "=" + className);
    keys.add(classNameSmall + "." + classNameSmall + "s=" + className + "s");

    for (PropertyItem prop : properties) {
      keys.add(classNameSmall + "." + prop.getName() + "=" + prop.getName().substring(0, 1).toUpperCase()
          + prop.getName().substring(1));
    }

    keys.add(classNameSmall + ".delete=Delete " + className + " {0}?");
    addToProperties(keys, className, new File("./src/ApplicationResources_en.properties"));

    
    
    //Options
    
    for (OptionItem oi : optionList) {
      outputDirString = oi.getPackageName() + ".web.options";      
      outputDirString = outputDirString.replace(".", "/");
      outputDir = new File("./src/" + outputDirString);
      outputDir.mkdirs();
      File optionFile = new File(outputDir, oi.getClassName() + "Options.java");
      
      if (!optionFile.exists() || model.isOverwrite()) {
        template = cfg.getTemplate("option.ftl");
        root.clear();
        root.put("className", oi.getClassName());
        root.put("package", oi.getPackageName());
        root.put("readMethod", oi.getRefReadMethod());
        BufferedWriter bw = new BufferedWriter(new FileWriter(optionFile));
        template.process(root, bw);
        bw.flush();
        bw.close();
      } else {
        System.out.printf("Option %s already exists", oi.getPackageName() + ".options."+oi.getClassName() + "Options");
      }
      
    }
    
  }

  private void addToProperties(List<String> keys, String className, File propertiesFile) throws IOException {
    if (propertiesFile.exists()) {

      BufferedReader br = new BufferedReader(new FileReader(propertiesFile));
      String line;
      boolean found = false;
      while ((line = br.readLine()) != null && !found) {
        if (line.contains(keys.get(0))) {
          found = true;
        }
      }
      br.close();

      if (!found) {

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(propertiesFile, true)));
        pw.println();
        pw.println("#" + className);

        for (String key : keys) {
          pw.println(key);
        }

        pw.close();
      }
    }
  }

  private void addFormToCleanSession(String formName) throws IOException {
    File cleanSessionFile = new File("./src/cleansession.txt");
    if (cleanSessionFile.exists()) {
      BufferedReader br = new BufferedReader(new FileReader(cleanSessionFile));
      String line;
      boolean found = false;
      while ((line = br.readLine()) != null && !found) {
        if (line.contains(formName)) {
          found = true;
        }
      }
      br.close();
  
      if (!found) {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(cleanSessionFile, true)));
        pw.println();
        pw.println(formName);
        pw.close();
  
      }
    }
  }

  public static void main(String[] args) {
    new SourceGenerator().setVisible(true);
  }

}
