import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import ch.admin.astra.fabasoftadapter.cdocument.CDOCUMENTType;
import ch.admin.astra.fabasoftadapter.cdocument.ObjectFactory;


public class Sandbox {

	public static void main(String[] args) throws JAXBException {
		ObjectFactory factory = new ObjectFactory();
		CDOCUMENTType cdoc = factory.createCDOCUMENTType();
		
		cdoc.setObjurl("url");
		cdoc.setObjname("name");
		cdoc.setFileid("1");
		
		
		JAXBContext ctx = JAXBContext.newInstance(CDOCUMENTType.class);
		Marshaller m = ctx.createMarshaller();
		m.marshal( cdoc, System.out );


	}

}
