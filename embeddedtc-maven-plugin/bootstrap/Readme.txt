Example of a Boostrap class. Starting the Tomcat with this class removes all the Tomcat/Runner classes from 
the system classloader. Right now everything in the jar is in the system classloader and therefore visible 
in the webapplication. Web Application will looks first in the system classloader for new classes.
Maybe we need that someday.

http://frank.zinepal.com/embedded-tomcat-class-loading-trickery
http://blog.markturansky.com/archives/21