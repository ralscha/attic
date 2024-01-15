package spring;

public class TestBusiness implements IBusiness {

  private String test;
  private ICollaborator collab;
  
  public void setCollab(ICollaborator collab) {
    this.collab = collab;
  }
  
  public String getTest() {
    return test;
  }
  
  public void setTest(String test) {
    this.test = test;
  }
  
  public void doStuff() {
    System.out.println("test " + collab.getResult());
  }

}
