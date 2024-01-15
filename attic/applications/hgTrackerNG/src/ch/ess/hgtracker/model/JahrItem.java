package ch.ess.hgtracker.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class JahrItem implements Externalizable {
  private int jahr;
  private String label;
  
  public int getJahr() {
    return jahr;
  }
  
  public void setJahr(int jahr) {
    this.jahr = jahr;
  }
  
  public String getLabel() {
    return label;
  }
  
  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    jahr = (Integer)in.readObject();
    label = (String)in.readObject();    
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeObject(jahr);
    out.writeObject(label);    
  }
  
  
  
}
