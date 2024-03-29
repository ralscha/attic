/**

 * Redistribution and use of this software and associated documentation

 * ("Software"), with or without modification, are permitted provided

 * that the following conditions are met:

 *

 * 1. Redistributions of source code must retain copyright

 *    statements and notices.  Redistributions must also contain a

 *    copy of this document.

 *

 * 2. Redistributions in binary form must reproduce the

 *    above copyright notice, this list of conditions and the

 *    following disclaimer in the documentation and/or other

 *    materials provided with the distribution.

 *

 * 3. The name "Exolab" must not be used to endorse or promote

 *    products derived from this Software without prior written

 *    permission of Exoffice Technologies.  For written permission,

 *    please contact info@exolab.org.

 *

 * 4. Products derived from this Software may not be called "Exolab"

 *    nor may "Exolab" appear in their names without prior written

 *    permission of Exoffice Technologies. Exolab is a registered

 *    trademark of Exoffice Technologies.

 *

 * 5. Due credit should be given to the Exolab Project

 *    (http://www.exolab.org/).

 *

 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS

 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT

 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND

 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL

 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,

 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES

 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR

 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)

 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,

 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)

 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED

 * OF THE POSSIBILITY OF SUCH DAMAGE.

 *

 * Copyright 1999 (C) Exoffice Technologies Inc. All Rights Reserved.

 *

 * $Id: JSourceCode.java,v 1.1.1.1 2000/01/08 01:11:28 arkin Exp $

 */



package org.exolab.javasource;





import java.io.PrintWriter;



import java.util.Vector;



/**

 * @author <a href="mailto:kvisco@exoffice.com">Keith Visco</a>

 * @version $Revision: 1.1.1.1 $ $Date: 2000/01/08 01:11:28 $

**/

public class JSourceCode {







  /**
  
   * A list of JCodeStatements
  
  **/

  private Vector source = null;



  /**
  
   * The indent size
  
  **/

  private short indentSize = JSourceWriter.DEFAULT_SIZE;



  /**
  
   * The current indent size
  
  **/

  private short currentIndent = indentSize;



  /**
  
   * Creates an empty JSourceCode
  
  **/

  public JSourceCode() {

    super();

    source = new Vector();

  } //-- JSourceCode



  /**
  
   * Creates a JSourceCode and adds the given String
  
   * to it's contents
  
   * @param sourceCode the source to add
  
  **/

  public JSourceCode(String sourceCode) {

    this();
    add(sourceCode);
    //this.source.addElement(sourceCode);

  } //-- JSourceCode



  /**
  
   * Adds the given statement to this JSourceCode. The statement
  
   * will be added on a new line.
  
   * @param statement the statement to add
  
  **/

  public void add(String statement) {

    JCodeStatement jcs = new JCodeStatement(statement, currentIndent);

    source.addElement(jcs);

  } //-- add



  /**
  
   * Adds the given statement to this JSourceCode. The statement
  
   * will be added on a new line.
  
   * @param statement the statement to add
  
   * @param the indentSize is the size of the indentation to use
  
   * when printing this JSourceCode
  
   * @see print
  
  **/

  public void add(String statement, short indentSize) {

    JCodeStatement jcs = new JCodeStatement(statement, (short)(currentIndent+indentSize));

    source.addElement(jcs);

  } //-- add



  /**
  
   * Appends the given String to the last line in this
  
   * JSourceCode
  
   * @param segment the String to append
  
  **/

  public void append(String segment) {



    if (source.isEmpty())
      add(segment);

    else {

      JCodeStatement jcs = (JCodeStatement) source.lastElement();

      jcs.append(segment);

    }

  } //-- append(String)



  /**
  
   * Clears all the code statements from this JSourceCode
  
  **/

  public void clear() {

    source.removeAllElements();

  } //-- clear();



  /**
  
   * Copies the contents of this JSourceCode into the given JSourceCode
  
   * @param jsc the JSourceCode to copy this JSourceCode into
  
  **/

  public void copyInto(JSourceCode jsc) {

    for (int i = 0; i < source.size(); i++) {

      addCodeStatement((JCodeStatement) source.elementAt(i));

    }

  } //-- copyInto



  /**
  
   * Increases the current indent level by 1
  
  **/

  public void indent() {

    currentIndent += indentSize;

  } //-- indent();



  /**
  
   * Prints this JSourceCode to the given JSourceWriter
  
   * @param jsw the JSourceWriter to print to
  
  **/

  public void print(JSourceWriter jsw) {

    for (int i = 0; i < source.size(); i++)

      jsw.writeln(source.elementAt(i));

  } //-- print



  /**
  
   * Decreases the indent level by 1
  
  **/

  public void unindent() {

    currentIndent -= indentSize;

  } //-- unindent





  /**
  
   * Returns the String representation of this JSourceCode
  
   * @return the String representation of this JSourceCode
  
  **/

  public String toString() {

    StringBuffer sb = new StringBuffer();

    String lineSeparator = System.getProperty("line.separator");

    for (int i = 0; i < source.size(); i++) {

      sb.append(source.elementAt(i).toString());

      sb.append(lineSeparator);

    }

    return sb.toString();

  } //-- toString



  /**
  
   * Adds the given JCodeStatement to this JSourceCode
  
   * @param jcs the JCodeStatement to add
  
  **/

  private void addCodeStatement(JCodeStatement jcs) {

    short indent = (short)(jcs.getIndent() + currentIndent);

    source.addElement(new JCodeStatement(jcs.getStatement(), indentSize));

  } //-- addCodeStatement(JCodeStatement)



} //-- JSourceCode



/**

 * Represents a line of code, used by JSourceCode class

 * @author <a href="kvisco@exoffice.com">Keith Visco</a>

**/

class JCodeStatement {



  private StringBuffer value = null;

  private short indentSize = JSourceWriter.DEFAULT_SIZE;



  JCodeStatement() {

    super();

    value = new StringBuffer();

  } //-- JCodeStatement



  JCodeStatement(String statement) {

    this();

    this.value.append(statement);

  } //-- JCodeStatement



  JCodeStatement(String statement, short indentSize) {

    this(statement);

    this.indentSize = indentSize;

  } //-- JCodeStatement





  void append(String segment) {

    value.append(segment);

  }



  short getIndent() {

    return indentSize;

  } //-- getIndent



  String getStatement() {

    return value.toString();

  } //-- getStatement



  public String toString() {

    StringBuffer sb = new StringBuffer(indentSize + value.length());

    for (int i = 0; i < indentSize; i++)
      sb.append(JSourceWriter.DEFAULT_CHAR);

    sb.append(value.toString());

    return sb.toString();

  }

} //-- JCodeStatement

