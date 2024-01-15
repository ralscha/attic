import org.jmonde.state.StateActionAdapter;


public class State3 extends StateActionAdapter {

  public void enterAction() {
    System.out.println("State3: enterAction");
  }

  public void action() {
    System.out.println("State3: action");
  }

  public void leaveAction() {
    System.out.println("State3: leave");

  }

}
