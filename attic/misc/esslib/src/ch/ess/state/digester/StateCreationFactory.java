package ch.ess.state.digester;

import org.apache.commons.digester.*;
import org.xml.sax.*;

import ch.ess.state.*;

public class StateCreationFactory extends AbstractObjectCreationFactory {

  public Object createObject(Attributes attr) throws Exception {
    String name = attr.getValue("name");    
    Integer number = new Integer(attr.getValue("number"));
    String key = attr.getValue("key");
    String location = attr.getValue("location");

    return new State(name, key, number, location);
  }

}
