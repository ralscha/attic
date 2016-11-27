package ch.ralscha.wsdemo;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;

import ch.ralscha.wsdemo.je.Document;

import com.sleepycat.je.Transaction;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;

@WebService(endpointInterface = "ch.ralscha.wsdemo.DocumentService")
@Named
public class DocumentServiceImpl implements DocumentService {

  @Inject
  private EntityStore entityStore;

  @Inject
  private PrimaryIndex<Long, Document> documentPrimaryIndex;

  @Override
  public String saveDocument(DsSessionDto aSessionDto, DsDocumentDto aDocumentDto, String aKey, boolean aKeyIsNumeric,
      String aValue, String aSecurityPolicyName, String aContentFileKey) {

    if ("bug".equals(aSessionDto.getUsername())) {
      throw new IllegalStateException("this is a bug");
    }

    Transaction tx = entityStore.getEnvironment().beginTransaction(null, null);

    Document doc = new Document();
    doc.setFileKey(aContentFileKey);

    doc.setFileName(getFileName(aDocumentDto));
    documentPrimaryIndex.put(doc);
    tx.commit();

    return String.valueOf(doc.getId());
  }

  @Override
  public void deleteDocument(DsSessionDto aSessionDto, String aDocumentId) {

    if ("bug".equals(aSessionDto.getUsername())) {
      throw new IllegalStateException("this is a bug");
    }

    Transaction tx = entityStore.getEnvironment().beginTransaction(null, null);
    documentPrimaryIndex.delete(Long.valueOf(aDocumentId));
    tx.commit();
  }

  @Override
  public void updateDocument(DsSessionDto aSessionDto, String aDocumentId, DsDocumentDto aDocumentDto) {

    if ("bug".equals(aSessionDto.getUsername())) {
      throw new IllegalStateException("this is a bug");
    }

    Transaction tx = entityStore.getEnvironment().beginTransaction(null, null);
    Document doc = documentPrimaryIndex.get(Long.valueOf(aDocumentId));
    doc.setFileName(getFileName(aDocumentDto));
    tx.commit();
  }

  @Override
  public String getDocumentContent(DsSessionDto aSessionDto, String aDocumentId) {
    if ("bug".equals(aSessionDto.getUsername())) {
      throw new IllegalStateException("this is a bug");
    }

    Document doc = documentPrimaryIndex.get(Long.valueOf(aDocumentId));
    return doc.getFileKey();
  }

  private String getFileName(DsDocumentDto aDocumentDto) {
    String fileName = null;
    for (DsPropertyDto property : aDocumentDto.getProperties()) {
      if ("documentTitle".equals(property.getName())) {
        fileName = property.getValue();
        break;
      }
    }
    return fileName;
  }

}
