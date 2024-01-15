

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author sr
 */
public class ParseProject {

  private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

  public static void main(String[] args) {
    try {

      if (args.length != 3) {
        System.out.println("java ParseProject <projectFile> <workspaceDir> <makeDir>");
        System.exit(1);
      }

      String projectFile = args[0];
      String workspaceDir = args[1];
      String makeDir = args[2];
      File makeDirFile = new File(makeDir);
      
      if (!makeDirFile.exists()) {
        makeDirFile.mkdirs();
      }

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(false);

      DocumentBuilder db = dbf.newDocumentBuilder();

      Document doc = db.parse(new File(projectFile));

      NodeList nl = doc.getElementsByTagName("name");
      if (nl.getLength() > 0) {
        Node n = nl.item(0);

        String name = n.getChildNodes().item(0).getNodeValue();

        copyDir(new File(workspaceDir + File.separatorChar + name), makeDirFile);

      }

      nl = doc.getElementsByTagName("project");
      for (int i = 0; i < nl.getLength(); i++) {
        Node node = nl.item(i);

        if (node.getNodeType() == Node.ELEMENT_NODE) {
          NodeList nlc = node.getChildNodes();
          for (int j = 0; j < nlc.getLength(); j++) {
            Node n = nlc.item(j);
            if (n.getNodeType() == Node.TEXT_NODE) {
              copyDir(new File(workspaceDir + File.separatorChar + n.getNodeValue()), makeDirFile);
            }
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }

  }

  private static void copyDir(File fromDir, File toDir) throws IOException {
    File[] files = fromDir.listFiles();
    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        if (files[i].isDirectory()) {
          File targetDir = new File(toDir, files[i].getName());
          targetDir.mkdirs();
          copyDir(files[i], targetDir);
        } else {
          copy(files[i], toDir);
        }
      }
    }
  }

  private static int copy(File srcFile, File toDir) throws IOException {
    InputStream is = new FileInputStream(srcFile);

    File toFile = new File(toDir, srcFile.getName());
    OutputStream out = new FileOutputStream(toFile);

    try {

      return copy(is, out);
    } finally {

      is.close();
      out.close();
    }
  }

  private static int copy(InputStream input, OutputStream output) throws IOException {
    final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    int count = 0;
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
      count += n;
    }
    return count;
  }

}

