package ch.ess.dummyws.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.commons.lang3.RandomStringUtils;
import ch.admin.astra.fabasoftadapter.ArrayOfString;
import ch.admin.astra.fabasoftadapter.FabasoftAdapterSoap;
import ch.admin.astra.fabasoftadapter.cdocument.CDOCUMENTType;
import ch.admin.astra.fabasoftadapter.cdocument.ObjectFactory;
import ch.admin.astra.fabasoftadapter.cdocument.ObjectType;
import ch.admin.astra.fabasoftadapter.cincoming.CINCOMINGType;
import ch.admin.astra.fabasoftadapter.cjurperson.CJURPERSONType;
import ch.admin.astra.fabasoftadapter.cnatperson.CNATPERSONType;
import ch.admin.astra.fabasoftadapter.csubfile.CSUBFILEType;

public class FabasoftAdapterSoapImpl implements FabasoftAdapterSoap {

  
 
  private JAXBContext ctx;

  public FabasoftAdapterSoapImpl() throws JAXBException {
     ctx = JAXBContext.newInstance(CDOCUMENTType.class, CINCOMINGType.class, CJURPERSONType.class, CNATPERSONType.class, CSUBFILEType.class);
  }


  @Override
  public ArrayOfString searchObjects(String objclass, String name, ArrayOfString appkeys, String createdat, String changedat, String owner,
      String root) {

    if ("CDOCUMENT".equals(objclass)) {
      return new ArraryOfStringImpl(createDocuments());
    } else if ("CSUBFILE".equals(objclass)) {

    } else if ("CINCOMING".equals(objclass)) {

    } else if ("CNATPERSON".equals(objclass)) {

    } else if ("CJURPERSON".equals(objclass)) {

    } else if ("CADDRESS".equals(objclass)) {

    }

    return null;

  }

  
  @Override
  public String getObject(String objaddress) {

    System.out.println(objaddress);
    
    return null;
  }

  @Override
  public ArrayOfString searchUsers(String name, String login) {
    return null;
  }
  
  @Override
  public boolean setObject(String objaddress, String xml) {
    return false;
  }

  @Override
  public ArrayOfString test(String val) {
    return null;
  }

  @Override
  public String getFoundObject(String objaddress) {
    return null;
  }

  @Override
  public ArrayOfString searchKeywords(String name) {
    return null;
  }

  List<String> createDocuments() {
    try {
      List<String> strings = new ArrayList<String>();

      ObjectFactory factory = new ObjectFactory();
      CDOCUMENTType cdoc = factory.createCDOCUMENTType();

      ch.admin.astra.fabasoftadapter.csubfile.ObjectFactory subfileFactory = new ch.admin.astra.fabasoftadapter.csubfile.ObjectFactory();
      ObjectType subfile = subfileFactory.createObjectType();
      subfile.setObjaddress("adr");
      subfile.setObjname("sub");
      subfile.setObjurl("suburl");
      
      cdoc.setObjaddress(RandomStringUtils.random(30, true, false));
      cdoc.setObjurl(RandomStringUtils.random(30, true, false));
      cdoc.setObjname(RandomStringUtils.random(10, true, false));
      cdoc.setFileid(RandomStringUtils.random(5, true, false));
      cdoc.setParent(subfile);
      
      strings.add(toXml(cdoc));

      return strings;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }


  private String toXml(CDOCUMENTType cdoc) throws JAXBException {
    Marshaller m = ctx.createMarshaller();
    StringWriter sw = new StringWriter();
    m.marshal(cdoc, sw);
    return sw.toString();
  }

}
