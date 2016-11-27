package ch.ralscha.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.util.FileUtils;

/**
 * The EclipseClasspath task will generate an Eclipse .classpath file from 
 * the supplied template and a classpath defined within build.xml 
 * 
 * Usage:
 * 
 * <taskdef name="eclipseClasspath" classname="com.coopertechnical.ant.task.EclipseClasspath" classpath="classes"/>
 * 
 * <eclipseClasspath templateFile=".classpath.tmpl" destFile=".classpath">
 *    <classpath refid="project.class.path"/>
 * </eclipseClasspath>
 * 
 * The template file will look something like this
 * 
 * <?xml version="1.0" encoding="UTF-8"?>
 * <classpath>
 * 	<classpathentry kind="src" path="src"/>
 * 	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
 * @CLASSPATHENTRIES@
 * 	<classpathentry kind="output" path="build"/>
 * </classpath>
 * 
 */
public class EclipseClasspathTask extends Task {

  private List<Path> classpathList = new ArrayList<Path>();
  private File templateFile = null;
  private File destFile = null;
  private String removeFilePart = null;
  private String replaceToken = "CLASSPATHENTRIES";

  // The method executing the task
  @Override
  public void execute() throws BuildException {
    if (classpathList.isEmpty()) {
      throw new BuildException("classpath not set.");
    }
    if (templateFile == null) {
      throw new BuildException("templateFile not set.");
    }
    if (!templateFile.exists()) {
      throw new BuildException("templateFile '" + templateFile.getName() + "' not found.");
    }
    if (destFile == null) {
      throw new BuildException("destFile not set.");
    }
    log("Outputting to " + destFile.getName());

    // Process the classpath and build the <classpathentry/> xml
    StringBuilder buffer = new StringBuilder();

    for (Path path : classpathList) {
      String[] paths = path.list();
      for (int ndx = 0; ndx < paths.length; ndx++) {
        buffer.append(getClassPathEntryXml(paths[ndx]));
      }
    }

    // Setup the filter 
    FilterSet fs = new FilterSet();
    fs.addFilter(replaceToken, buffer.toString());
    FilterSetCollection filters = new FilterSetCollection(fs);

    // Now copy the template file to the output file and
    // use the filter set for replacement
    FileUtils fu = FileUtils.getFileUtils();
    try {
      fu.copyFile(templateFile, destFile, filters);
    } catch (IOException e) {
      throw new BuildException(e);
    }

  }

  // 
  // Task attributes
  // 

  // The setter for the "classpath" attribute
  public void addClasspath(Path classpath) {
    if (classpath != null) {
      classpathList.add(classpath);
    }
  }

  public void setTemplateFile(File templateFile) {
    this.templateFile = templateFile;
  }

  public void setDestFile(File destFile) {
    this.destFile = destFile;
  }

  public void setReplaceToken(String token) {
    replaceToken = token;
  }

  public void setRemoveFilePart(String removeFilePart) {
    this.removeFilePart = removeFilePart.replace('\\', '/');
  }

  private String getClassPathEntryXml(String item) {   
    String _item = item.replace('\\', '/');
    if (removeFilePart != null) {      
      if (_item.startsWith(removeFilePart)) {
        _item = _item.substring(removeFilePart.length()); 
      }
    }
    return "	<classpathentry kind=\"lib\" path=\"" + _item + "\"/>\n";
  }

}
