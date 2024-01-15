import org.jmonde.state.StateActionAdapter;


public class State1 extends StateActionAdapter {

  public void enterAction() {
    System.out.println("State1: enterAction");
  }

  public void action() {
    System.out.println("State1: action");
  }

  public void leaveAction() {
    System.out.println("State1: leave");

  }

}
