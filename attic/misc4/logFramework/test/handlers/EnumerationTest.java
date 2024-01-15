//
// Copyright (C) 1999 Grace Software
//
// The contents of this file are subject to the Grace Software Public
// License Version 1.0 (the "GracePL"); you may not use this file
// except in compliance with the GracePL. You may obtain a copy of the
// GracePL at http://www.homestead.com/javalog/files/license.html
//
// Software distributed under the GracePL is distributed on an "AS IS"
// basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
// the GracePL for the specific language governing rights and
// limitations under the GracePL.
//
// The Original Code is Grace Software's JavaLog code, released March
// 21, 1999.
//
// The Initial Developer of the Original Code is Grace Software.
// Portions created by Grace Software are Copyright (C) 1999 Grace
// Software. All Rights Reserved.
//
// Contributor(s):
//
//
// Copyright (C) 1999 Grace Software
//
// The contents of this file are subject to the Grace Software Public
// License Version 1.0 (the "GracePL"); you may not use this file
// except in compliance with the GracePL. You may obtain a copy of the
// GracePL at http://www.homestead.com/javalog/files/license.html
//
// Software distributed under the GracePL is distributed on an "AS IS"
// basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
// the GracePL for the specific language governing rights and
// limitations under the GracePL.
//
// The Original Code is Grace Software's JavaLog code, released March
// 21, 1999.
//
// The Initial Developer of the Original Code is Grace Software.
// Portions created by Grace Software are Copyright (C) 1999 Grace
// Software. All Rights Reserved.
//
// Contributor(s):
//
package grace.io.test.handlers;

import java.util.*;

import grace.log.Log;

public class EnumerationTest extends grace.test.Base {
    public static void main(String[] args) {
	new EnumerationTest().run();
    }

    public void run() {
	try {
	    Vector vector = new Vector();
	    vector.addElement("1");
	    vector.addElement("2");
	    vector.addElement("3");
	    vector.addElement("4");
	    Log.trace("vector.elements()", vector.elements());
	}
	catch (Exception e) {
	    Log.error("aborting test", e);
	}
    }
}

	

	
	
