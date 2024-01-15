package ch.ess.base.annotation;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ClassProcessor {
  private static final Log log = LogFactory.getLog(ClassProcessor.class);


  private List<AnnotationProcessor> annotationProcessors;
  private List<NameProcessor> nameProcessors;
  private List<HierarchyProcessor> hierarchyProcessors;
  
  private Set<String> locationFilters;
  private Set<String> packageFilters;
  
  
  public void addAnnotationProcessor(AnnotationProcessor annotationProcessor) {
    if (annotationProcessors == null) {
      annotationProcessors = new ArrayList<AnnotationProcessor>();
    }
    annotationProcessors.add(annotationProcessor);
  }

  public void addNameProcessor(NameProcessor nameProcessor) {
    if (nameProcessors == null) {
      nameProcessors = new ArrayList<NameProcessor>();
    }
    nameProcessors.add(nameProcessor);
  }

  public void addHierarchyProcessor(HierarchyProcessor hierarchyProcessor) {
    if (hierarchyProcessors == null) {
      hierarchyProcessors = new ArrayList<HierarchyProcessor>();
    }
    hierarchyProcessors.add(hierarchyProcessor); 
  }
  
  @SuppressWarnings("unchecked")
  public ClassProcessor() {
    this(Collections.EMPTY_SET, Collections.EMPTY_SET);  
  }

  @SuppressWarnings("unchecked")
  public ClassProcessor(Set<String> locationFilters) {
    this(locationFilters, Collections.EMPTY_SET);  
  }
  
  public ClassProcessor(Set<String> locationFilters, Set<String> packageFilters) {
    this.locationFilters = locationFilters;
    this.packageFilters = packageFilters;
  }

  
  public void process() {
    
    ClassLoader loader = Thread.currentThread().getContextClassLoader();

    // If it's not a URLClassLoader, we can't deal with it!
    if (!(loader instanceof URLClassLoader)) {
      log.error("The current ClassLoader is not castable to a URLClassLoader. ClassLoader " + "is of type ["
          + loader.getClass().getName() + "]");
    } else {
      URLClassLoader urlLoader = (URLClassLoader)loader;
      URL[] urls = urlLoader.getURLs();

      for (URL url : urls) {
        String path = url.getFile();
        File location = new File(path);

        // Only process the URL if it matches one of our filter strings
        if (matchesAny(path, locationFilters)) {
          
          log.info("process: " + location);
          
          if (location.isDirectory()) {
            processClassesInDirectory(null, location);
          } else {
            processClassesInJar(location);
          }
        }
      }
    }
  
  }
  
  private static boolean matchesAny(String text, Set<String> filters) {
    if (filters.size() == 0) {
      return true;
    }
    for (String filter : filters) {
      if (text.indexOf(filter) != -1) {
        return true;
      }
    }
    return false;
  }

  private void processClassesInDirectory(String parent, File location) {

    File[] files = location.listFiles();
    StringBuilder builder = null;

    for (File file : files) {
      builder = new StringBuilder(100);
      builder.append(parent).append("/").append(file.getName());
      String packageOrClass = (parent == null ? file.getName() : builder.toString());

      if (file.isDirectory()) {
        processClassesInDirectory(packageOrClass, file);
      } else if (file.getName().endsWith(".class")) {
        if (matchesAny(packageOrClass, packageFilters)) {
          processNames(packageOrClass);
          processAnnotations(packageOrClass);
          processHierarchies(packageOrClass);
        }
      }
    }

  }

  private void processClassesInJar(File location) {

    try {
      JarFile jar = new JarFile(location);
      Enumeration< ? > entries = jar.entries();

      while (entries.hasMoreElements()) {
        ZipEntry entry = (ZipEntry)entries.nextElement();
        String name = entry.getName();
        if (!entry.isDirectory() && name.endsWith(".class")) {
          if (matchesAny(name, packageFilters)) {
            processNames(name);
            processAnnotations(name);
            processHierarchies(name);
          }
        }
      }
    } catch (IOException ioe) {
      log.error("Could not search jar file '" + location);
    }
  }

  
  @SuppressWarnings("unchecked")
  private void processNames(String name) {
    if ((nameProcessors != null) && !nameProcessors.isEmpty()) {
      for (NameProcessor processor : nameProcessors) {        
        try {
          ClassLoader loader = Thread.currentThread().getContextClassLoader();
          String externalName = name.substring(0, name.indexOf('.')).replace('/', '.');
    
          if (processor.matches(externalName)) {
            Class type = loader.loadClass(externalName);
            processor.process(type);
          }
        } catch (Throwable t) {
          //log.warn("Could not examine class '" + name + "'", t);
        }
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  private void processAnnotations(String name) {
    if ((annotationProcessors != null) && !annotationProcessors.isEmpty()) {
      for (AnnotationProcessor processor : annotationProcessors) {        
        try {
          ClassLoader loader = Thread.currentThread().getContextClassLoader();
          String externalName = name.substring(0, name.indexOf('.')).replace('/', '.');
    
          Class type = loader.loadClass(externalName);
          if (type.isAnnotationPresent(processor.getAnnotation())) {
            processor.process(type);
          }
        } catch (Throwable t) {
          //log.warn("Could not examine class '" + name + "'", t);
        }
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  private void processHierarchies(String name) {
    if ((hierarchyProcessors != null) && !hierarchyProcessors.isEmpty()) {
      for (HierarchyProcessor processor : hierarchyProcessors) {        
        try {
          ClassLoader loader = Thread.currentThread().getContextClassLoader();
          String externalName = name.substring(0, name.indexOf('.')).replace('/', '.');
    
          Class type = loader.loadClass(externalName);
          if (processor.assignableTo().isAssignableFrom(type)) {
            processor.process(type);
          }
        } catch (Throwable t) {
          //log.warn("Could not examine class '" + name + "'", t);
        }
      }
    }
  }
}
