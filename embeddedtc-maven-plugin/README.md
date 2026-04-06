# embeddedtc-maven-plugin

[![Build Status](https://api.travis-ci.org/ralscha/embeddedtc-maven-plugin.png)](https://travis-ci.org/ralscha/embeddedtc-maven-plugin)

**embeddedtc-maven-plugin** is a [Maven](http://maven.apache.org/) plugin that bundles one or multiple war files and a [Apache Tomcat 7](http://tomcat.apache.org/) into one _executable_ jar.
On the target machine a simple `java -jar myproject.jar` starts Tomcat and deploys the included war file. 

The official [Tomcat Maven plugin](http://tomcat.apache.org/maven-plugin.html) also provides an _executable_ jar creator. 
The main difference from this plugin to the one from Apache is the runtime configuration.   
* The Apache plugin uses xml files (context.xml, server.xml) and command line parameters to control the runtime behavior.
* This plugin uses one [YAML](http://en.wikipedia.org/wiki/YAML) configuration file that controls everything.    

If the Apache plugin fits your needs then there is no reason to switch to this plugin.    


### See [Wiki](https://github.com/ralscha/embeddedtc-maven-plugin/wiki/Quick-Start) for more information.
