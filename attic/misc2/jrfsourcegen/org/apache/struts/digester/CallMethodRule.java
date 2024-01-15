/*
 * $Header: /home/cvspublic/jakarta-struts/src/share/org/apache/struts/digester/CallMethodRule.java,v 1.4 2000/08/13 04:40:03 craigmcc Exp $
 * $Revision: 1.4 $
 * $Date: 2000/08/13 04:40:03 $
 *
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */ 


package org.apache.struts.digester;


import java.lang.reflect.Method;
import org.xml.sax.AttributeList;
import org.apache.struts.util.BeanUtils;


/**
 * Rule implementation that calls a method on the top (parent)
 * object, passing arguments collected from subsequent
 * <code>CallParamRule</code> rules or from the body of this
 * element.
 *
 * @author Craig McClanahan
 * @version $Revision: 1.4 $ $Date: 2000/08/13 04:40:03 $
 */

public final class CallMethodRule extends Rule {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a "call method" rule with the specified method name.  The
     * parameter types (if any) default to java.lang.String.
     *
     * @param digester The associated Digester
     * @param methodName Method name of the parent method to call
     * @param paramCount The number of parameters to collect, or
     *  zero for a single argument from the body of this element.
     */
    public CallMethodRule(Digester digester, String methodName,
    			  int paramCount) {

	this(digester, methodName, paramCount, null);

    }


    /**
     * Construct a "call method" rule with the specified method name.
     *
     * @param digester The associated Digester
     * @param methodName Method name of the parent method to call
     * @param paramCount The number of parameters to collect, or
     *  zero for a single argument from the body of ths element
     * @param paramTypes The Java class names of the arguments
     */
    public CallMethodRule(Digester digester, String methodName,
                          int paramCount, String paramTypes[]) {

	super(digester);
	this.methodName = methodName;
	this.paramCount = paramCount;
	if (paramTypes == null) {
	    if (this.paramCount == 0)
	        this.paramTypes = new Class[1];
	    else
	    	this.paramTypes = new Class[this.paramCount];
	    for (int i = 0; i < this.paramTypes.length; i++) {
		if (i == 0)
		    this.paramTypes[i] = "abc".getClass();
		else
		    this.paramTypes[i] = this.paramTypes[0];
	    }
	} else {
	    this.paramTypes = new Class[paramTypes.length];
	    for (int i = 0; i < this.paramTypes.length; i++) {
		try {
		    this.paramTypes[i] = Class.forName(paramTypes[i]);
		} catch (ClassNotFoundException e) {
		    this.paramTypes[i] = null;	// Will trigger NPE later
		}
	    }
        }

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The body text collected from this element.
     */
    private String bodyText = null;



    /**
     * The method name to call on the parent object.
     */
    private String methodName = null;


    /**
     * The number of parameters to collect from <code>MethodParam</code> rules.
     * If this value is zero, a single parameter will be collected from the
     * body of this element.
     */
    private int paramCount = 0;


    /**
     * The parameter types of the parameters to be collected.
     */
    private Class paramTypes[] = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Process the start of this element.
     *
     * @param attributes The attribute list for this element
     */
    public void begin(AttributeList attributes) throws Exception {

	// Push an array to capture the parameter values if necessary
	if (paramCount > 0) {
	    String parameters[] = new String[paramCount];
	    for (int i = 0; i < parameters.length; i++)
	        parameters[i] = null;
	    digester.push(parameters);
        }

    }


    /**
     * Process the body text of this element.
     *
     * @param bodyText The body text of this element
     */
    public void body(String bodyText) throws Exception {

	if (paramCount == 0)
	    this.bodyText = bodyText;

    }


    /**
     * Process the end of this element.
     */
    public void end() throws Exception {

	// Retrieve or construct the parameter values array
	String parameters[] = null;
	if (paramCount > 0)
	    parameters = (String[]) digester.pop();
	else {
	    parameters = new String[1];
	    parameters[0] = bodyText;
        }

	// Construct the parameter values array we will need
	Object paramValues[] = new Object[paramTypes.length];
	for (int i = 0; i < this.paramTypes.length; i++)
	    paramValues[i] =
	      BeanUtils.convert(parameters[i], this.paramTypes[i]);

	// Invoke the required method on the top object
	Object top = digester.peek();
	if (digester.getDebug() >= 1) {
	    StringBuffer sb = new StringBuffer("Call ");
	    sb.append(top.getClass().getName());
	    sb.append(".");
	    sb.append(methodName);
	    sb.append("(");
	    for (int i = 0; i < paramValues.length; i++) {
		if (i > 0)
		    sb.append(",");
		if (paramValues[i] == null)
		    sb.append("null");
		else
		    sb.append(paramValues[i].toString());
	    }
	    sb.append(")");
	    digester.log(sb.toString());
	}
	Method method = top.getClass().getMethod(methodName, paramTypes);
	method.invoke(top, paramValues);

    }


    /**
     * Clean up after parsing is complete.
     */
    public void finish() throws Exception {

	bodyText = null;
	methodName = null;
	paramTypes = null;

    }


}
