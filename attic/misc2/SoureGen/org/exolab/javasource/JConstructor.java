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
 * $Id: JConstructor.java,v 1.1.1.1 2000/01/08 01:11:28 arkin Exp $
 */

package org.exolab.javasource;


import java.util.Vector;
import java.io.PrintWriter;

/**
 * A class for handling source code for a constructor of a JClass
 * @author <a href="mailto:kvisco@exoffice.com">Keith Visco</a>
 * @version $Revision: 1.1.1.1 $ $Date: 2000/01/08 01:11:28 $
**/
public class JConstructor {

    



    /**

     * The set of modifiers for this JMethod

    **/

    private JModifiers modifiers = null;

    

    

    /**

     * List of parameters for this Constructor

    **/

    private JNamedMap params       = null;

    

    /**

     * The Class in this JMember has been declared

    **/

    private JClass declaringClass = null;

    

    private JSourceCode sourceCode = null;

    

    /**

     * Creates a new method with the given name and returnType.

     * For "void" return types, simply pass in null as the returnType

    **/

    protected JConstructor(JClass declaringClass) {

        

        this.declaringClass = declaringClass;

        this.modifiers = new JModifiers();

        this.params = new JNamedMap();

        this.sourceCode = new JSourceCode();

    }

    

    /**

     * Adds the given parameter to this Methods list of parameters

     * @param parameter the parameter to add to the this Methods

     * list of parameters.

     * @exception IllegalArgumentException when a parameter already

     * exists for this Method with the same name as the new parameter

    **/

    public void addParameter(JParameter parameter) 

        throws IllegalArgumentException

    {

        

        if (parameter == null) return;

        //-- check current params

        if (params.get(parameter.getName()) != null) {

            StringBuffer err = new StringBuffer();

            err.append("A parameter already exists for the constructor, ");

            err.append(this.declaringClass.getName());

            err.append(", with the name: ");

            err.append(parameter.getName());

            throw new IllegalArgumentException(err.toString());

        }

        

        params.put(parameter.getName(), parameter);

        

        //-- be considerate and add the class name to the
        //-- declaring class's list of imports
        if (declaringClass != null) {
            JType jType = parameter.getType();
            if (!jType.isPrimitive()) {
                declaringClass.addImport( jType.getName() );
            }
        }
    } //-- addParameter


    /**
     * Returns the class in which this JMember has been declared
     * @return the class in which this JMember has been declared
    **/
    public JClass getDeclaringClass() {
        return this.declaringClass;
    } //-- getDeclaringClass

    /**
     * Returns the modifiers for this JConstructor
     * @return the modifiers for this JConstructor
    **/
    public JModifiers getModifiers() {
        return this.modifiers;
    } //-- getModifiers

        
    /**
     * Returns an array of JParameters consisting of the parameters
     * of this Method in declared order
     * @return a JParameter array consisting of the parameters
     * of this Method in declared order
    **/
    public JParameter[] getParameters() {

        
        JParameter[] jpArray = new JParameter[params.size()];
        for (int i = 0; i < jpArray.length; i++) {
            jpArray[i] = (JParameter)params.get(i);
        }
        return jpArray;

    } //-- getParameters

    public JSourceCode getSourceCode() {
        return this.sourceCode;
    } //-- getSourceCode

    public void print(JSourceWriter jsw) {
        
        if (modifiers.isPrivate()) jsw.write("private");
        else if (modifiers.isProtected()) jsw.write("protected");
        else jsw.write("public");
        jsw.write(' ');
        jsw.write(declaringClass.getLocalName());
        jsw.write('(');
        
        //-- print parameters
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) jsw.write(", ");
            jsw.write(params.get(i));
        }
        jsw.writeln(") {");
        //jsw.indent();
        sourceCode.print(jsw);
        //jsw.unindent();
        if (!jsw.isNewline()) jsw.writeln();
        jsw.write("}");
        jsw.writeln();
    } //-- print
    
    public void setModifiers(JModifiers modifiers) {
        this.modifiers = modifiers.copy();
        this.modifiers.setFinal(false);
    } //-- setModifiers
    
    
    public void setSourceCode(String sourceCode) {
        this.sourceCode = new JSourceCode(sourceCode);
    } //-- setSourceCode
    
    public void setSourceCode(JSourceCode sourceCode) {
        this.sourceCode = sourceCode;
    } //-- setSourceCode
    
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(declaringClass.getName());
        sb.append('(');
        
        //-- print parameters
        for (int i = 0; i < params.size(); i++) {
            JParameter jp = (JParameter)params.get(i);
            if (i > 0) sb.append(", ");
            sb.append(jp.getType().getName());
        }
        sb.append(')');
        return sb.toString();
    } //-- toString
   
} //-- JConstructor