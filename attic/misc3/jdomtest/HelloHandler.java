/*--

 Copyright (C) 2000 Brett McLaughlin. All rights reserved.

 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice,
    this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions, the disclaimer that follows these conditions,
    and/or other materials provided with the distribution.

 3. Products derived from this software may not be called "Java and XML", nor may
    "Java and XML" appear in their name, without prior written permission from
    Brett McLaughlin (brett@newInstance.com).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 JDOM PROJECT  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 This software was originally created by Brett McLaughlin <brett@newInstance.com>.
 For more  information on "Java and XML", please see <http://www.oreilly.com/catalog/javaxml/>
 or <http://www.newInstance.com>.

 */

/**
 * <b><code>HelloHandler</code></b> is a simple handler that can
 *   be registered with an XML-RPC server.
 *
 * @author Brett McLaughlin
 * @version 1.0
 */
public class HelloHandler {

	/**
	 * <p>
	 * This will take in a <code>String</code> and return
	 *   a hello message to the user indicated.
	 * </p>
	 *
	 * @param name <code>String</code> name of person to say Hello to.
	 * @return <code>String</code> - hello message.
	 */
	public String sayHello(String name) {
		return "Hello " + name;
	}

}