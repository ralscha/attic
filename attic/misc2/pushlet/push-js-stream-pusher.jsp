<%
 /** Start a line of JavaScript with a function call to parent frame. */
 String jsFunPre = "<script language=JavaScript >parent.push('";

 /** End the line of JavaScript */
 String jsFunPost = "')</script> ";

 int i = 1;
 try {

 // Every three seconds a line of JavaScript is pushed to the client
 while (true) {

 // Push a line of JavaScript to the client
 out.print(jsFunPre+"Page "+(i++)+jsFunPost);
 out.flush();

 // Sleep three secs
 try {
 Thread.sleep(3000);
 } catch (InterruptedException e) {
 // Let client display exception
 out.print(jsFunPre+"InterruptedException: "+e+jsFunPost);
 }
 }
 } catch (Exception e) {
 // Let client display exception
 out.print(jsFunPre+"Exception: "+e+jsFunPost);
 }
 %>

