package ch.ess.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.thoughtworks.xstream.XStream;

/**
 * @author sr
 */
public class CreateSource {

  public static void main(String[] args) {
    try {
      InputStream is = CreateSource.class.getResourceAsStream("/velocity.properties");
      Properties props = new Properties();
      props.load(is);
      is.close();
      Velocity.init(props);

      XStream xstream = new XStream();
      xstream.alias("sourcegen", Config.class);
      xstream.alias("classDescription", ClassDescription.class);
      xstream.alias("property", PropertyDescription.class);
      is = CreateSource.class.getResourceAsStream("/event.xml");
      Reader reader = new InputStreamReader(is);
      Config config = (Config)xstream.fromXML(reader);
      reader.close();
      createSource(config);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String[] javaWebTemplateFiles = {"form.vm", "list.vm", "edit.vm"};
  private static String[] jspTemplateFiles = {"listjsp.vm", "editjsp.vm"};
  private static String[] javaWebOutputFiles = {"${clazz.name}Form.java", "${clazz.name}ListAction.java",
      "${clazz.name}EditAction.java"};
  private static String[] jspOutputFiles = {"${clazz.nameAllSmall}list.jsp", "${clazz.nameAllSmall}edit.jsp"};

  static void createSource(Config config) {
    try {
      for (ClassDescription cd : config.getClassDescriptions()) {

        VelocityContext context = new VelocityContext();
        context.put("clazz", cd);
        context.put("config", config);

        String webPackageName = cd.getPackageName() + ".web." + cd.getNameAllSmall();
        webPackageName = webPackageName.replace('.', File.separatorChar);
        String javaWebOut = config.getJavaOutputPath() + File.separatorChar + webPackageName;
        createPath(javaWebOut);
        createFile(context, javaWebOutputFiles, javaWebTemplateFiles, javaWebOut);

        String perPackageName = cd.getPackageName() + ".persistence";
        perPackageName = perPackageName.replace('.', File.separatorChar);
        String javaPerOut = config.getJavaOutputPath() + File.separatorChar + perPackageName;
        createPath(javaPerOut);
        createFile(context, new String[]{"${clazz.name}Dao.java"}, new String[]{"dao.vm"}, javaPerOut);

        String perhPackageName = cd.getPackageName() + ".persistence.hibernate";
        perhPackageName = perhPackageName.replace('.', File.separatorChar);
        String javaPerhOut = config.getJavaOutputPath() + File.separatorChar + perhPackageName;
        createPath(javaPerhOut);
        createFile(context, new String[]{"${clazz.name}DaoHibernate.java"}, new String[]{"daohibernate.vm"},
            javaPerhOut);

        createPath(config.getJspOutputPath());
        createFile(context, jspOutputFiles, jspTemplateFiles, config.getJspOutputPath());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void createPath(String javaout) {
    File f = new File(javaout);
    if (!f.exists()) {
      f.mkdirs();
    }
  }

  private static void createFile(VelocityContext context, String[] files, String[] templates, String outputPath)
      throws Exception {
    for (int i = 0; i < templates.length; i++) {
      StringWriter w = new StringWriter();
      Velocity.evaluate(context, w, "output", files[i]);
      String output = w.getBuffer().toString();

      File f = new File(outputPath + "/" + output);
      if (!f.exists()) {
        PrintWriter pw = new PrintWriter(new FileWriter(f));
        Velocity.mergeTemplate("ch/ess/tools/template/" + templates[i], "UTF-8", context, pw);
        pw.close();
      } else {
        System.out.println("existing file skipped - " + f.getName());
      }
    }
  }
}