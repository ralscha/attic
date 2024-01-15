/*
 *
 * Keywords: java, development, JDBC
 *
 * Copyright (C) 2000 George Stewart
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *  
 * This is one of a set of classes that make up Osage - Persistence
 * Plus XML, a framework for object-to-relational persistence.
 *
 * The latest version of Osage is available at
 * <URL:http://osage.sourceforge.net>.
 *
 * Please send any comments, bugs, or enhancement requests to
 * George Stewart at georgestewartiv@yahoo.com
 *
 */

package net.sourceforge.osage.util.builder;

// JDK
import java.io.*;
import java.util.*;

// Castor
import net.sourceforge.osage.castor.SQLTypes;
import org.exolab.javasource.*;

// Osage
import net.sourceforge.osage.util.StringUtils;

/**
 *
 * @author George Stewart
 * @author Ralph Schaer
 *
 */

public class ClassGenerator extends Generator {

  private final static JType collectionType = new JType("Collection");

  private JClass source;
      
  protected ClassGenerator(String className, String packageName) {
    source = new JClass(className);
    source.getJDocComment().appendComment("This file was generated");

    source.setPackageName(packageName);
    source.addImport("java.math.BigDecimal");
    source.addImport("java.util.Collection");
    source.addImport("java.util.ArrayList");
		source.addImport("java.util.Date");
		source.addImport("java.sql.Time");
    source.addImport("java.sql.Timestamp");

    JMember member = new JMember(JType.String, "FOR_NAME");
    member.getModifier().setStatic(true);
    member.getModifier().makePublic();
    member.setInitString(packageName+"."+className);
    source.addMember(member);


  }


  public void addAttribute(String name, JType type, int length) {
    addAttribute(name, type, length, false);
  }

  public void addAttribute(String name, JType type, boolean isCollection) {
    addAttribute(name, type, 0, isCollection);
  }

  public void addAttribute(String name, JType type, int length, boolean isColleciton) {
    //class variables
    String sqlname = SQLTypes.javaToSqlName(name);
    JMember cmember = new JMember(JType.String, sqlname.toUpperCase());
    cmember.getModifier().setStatic(true);
    cmember.getModifier().makePublic();
    cmember.setInitString(name);
    source.addMember(cmember);

    //instance variables
    JMember member;
    if (isCollection)
      memmber = new JMember(collectionType, name);
    else 
      member = new JMember(type, name);
            
    member.getModifier().makePrivate();

    //get method
    JMethod getMethod;
    if (isCollection) 
      getMethod = new JMethod(collectionType, "get"+capitalize(name));
    else
      getMethod = new JMethod(type, "get"+capitalize(name));

    getMethod.getModifier().makePublic();
    getMethod.setSourceCode(new JSourceCode("return " + name + ";"));


    //set method
    JMethod setMethod = new JMethod(null, "set"+capitalize(name));

    JParameter param;
    if (isCollection)
      param = new JParameter(collectionType, name);
    else
      param = new JParameter(type, name);

    setMethod.addParameter(param);
    setMethod.getModifier().makePublic();

    if (length > 0) {
      JSourceCode sc = new JSourceCode();
      sc.add("this."+name+" = ");
      sc.add(name + ".substring(0, Math.min("+length+", "+name+".length()));", 2);
      setMethod.setSourceCode(sc);
    } else {
      setMethod.setSourceCode(new JSourceCode("this."+name+" = " + name + ";"));
    }


    //add method
    if (isCollection) {
      String varName = uncapitalize(StringUtils.afterLast(type.getName(), '.'));

      JMethod addMethod = new JMethod(null, "add"+capitalize(name));        
      addMethod.addParameter(new JParameter(type, varName));
      JSourceCode sc = new JSourceCode();
      sc.add("if (" + name + " == null) {");
      sc.add("this." + name + " = new ArrayList();", 2);
      sc.add("}");
      sc.add("this." + name + ".add(" + varName + ");");
      addMethod.setSourceCode(sc);
    }
  }

  void generate() {
    source.print();
  }

}
