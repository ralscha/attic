import org.jmonde.state.StateActionAdapter;


public class State2 extends StateActionAdapter {

  public void enterAction() {
    System.out.println("State2: enterAction");
  }

  public void action() {
    System.out.println("State2: action");
  }

  public void leaveAction() {
    System.out.println("State2: leave");

  }

}
