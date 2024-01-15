package ch.ess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.Manager;
import org.jboss.seam.faces.RedirectException;
import org.jboss.seam.pdf.DocumentData;
import org.jboss.seam.pdf.DocumentStore;
import org.jdom.JDOMException;
import org.jopendocument.dom.template.JavaScriptFileTemplate;
import org.jopendocument.dom.template.TemplateException;

@Name("jodConverter")
public class JodConverter {

  @In
  private InputData inputData;

  @In
  private Manager manager;

  @In
  private FacesContext facesContext;

  public void doIt() throws IOException, TemplateException, JDOMException {
    System.out.println(inputData.getBestellnummer());

    File templateFile = File.createTempFile("jodtest", "temp");
    templateFile.deleteOnExit();
    FileOutputStream fos = new FileOutputStream(templateFile);
    IOUtils.copy(inputData.getTemplate(), fos);
    fos.close();
    inputData.getTemplate().close();

    File outFile = File.createTempFile("jodtestoutput", "temp");
    outFile.deleteOnExit();
    // Load the template.
    // Java 5 users will have to use RhinoFileTemplate instead
    JavaScriptFileTemplate template = new JavaScriptFileTemplate(templateFile);

    // Fill with sample values.
    template.setField("bestellnummer", inputData.getBestellnummer());
    template.setField("vertragsnummer", inputData.getVertragsnummer());
    template.setField("von", inputData.getVon());
    template.setField("bis", inputData.getBis());

    template.hideParagraph("p1");

    // Save to file.
    template.saveAs(outFile);

    File f = new File(outFile+".odt");
    
    byte[] binaryData = FileUtils.readFileToByteArray(f);
    System.out.println(binaryData.length);
    f.delete();
    outFile.delete();
    templateFile.delete();
    DocumentData data = new DocumentData("output", new DocumentData.DocumentType("odt", "application/vnd.oasis.opendocument.text "),
        binaryData);
    String docId = DocumentStore.instance().newId();
    DocumentStore.instance().saveData(docId, data);
    String documentUrl = DocumentStore.instance().preferredUrlForContent(data.getBaseName(), data.getDocumentType().getExtension(), docId);

    System.out.println(documentUrl);
    
    try {
      facesContext.getExternalContext().redirect(manager.encodeConversationId(documentUrl));
    } catch (IOException ioe) {
      throw new RedirectException(ioe);
    }

  }
}
