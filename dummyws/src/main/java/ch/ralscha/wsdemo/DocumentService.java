package ch.ralscha.wsdemo;

import javax.jws.WebService;

@WebService
public interface DocumentService {

   String saveDocument(DsSessionDto aSessionDto,
       DsDocumentDto aDocumentDto, String aKey, boolean aKeyIsNumeric,
       String aValue, String aSecurityPolicyName, String aContentFileKey);
   
   void deleteDocument(DsSessionDto aSessionDto, String aDocumentId);
  
   void updateDocument(DsSessionDto aSessionDto, String aDocumentId,
       DsDocumentDto aDocumentDto);
  
   String getDocumentContent(DsSessionDto aSessionDto, String aDocumentId);

} 
