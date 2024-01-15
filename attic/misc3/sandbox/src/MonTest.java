import org.jmonde.state.State;
import org.jmonde.state.StateActionAdapter;
import org.jmonde.state.StateTransit;


public class MonTest {



    private final StateTransit myTransit = new StateTransit();
    
    private final State myStateLocked = new State(new ActionLocked());
    private final State myStateUnlocked = new State(new ActionUnlocked());
    final State myStateBroken = new State(new ActionBroken());
    
    
    public MonTest() {
      myTransit.enterInitial(myStateLocked);
      
    }
    /*
     * Event handlers
     */
    public void coin() {
      if (myStateLocked.isActive()) {
        simpleTransitionTo(myStateUnlocked);
      } else if (myStateUnlocked.isActive()) {
        thankYou();
      }
    }
    public void pass() {
      if (myStateUnlocked.isActive()) {
        simpleTransitionTo(myStateLocked);
      } else if (myStateLocked.isActive()) {
        alarm();
      }
    }
    public void fixed() {
      if (myStateBroken.isActive()) {
        simpleTransitionTo(myStateLocked);
      }
    }
    /*
     * Behavior
     */
    private void alarm() {
      System.out.println("Alarm");
    }
    void inOrder() {
      System.out.println("InOrder");
    }
    boolean lock() {
      // perhaps you can provide a more sophisticated lock simulation!
      boolean result = true;
      return result;
    }
    void lockError() {
      System.out.println("LockError");
    }
    void outOfOrder() {
      System.out.println("OutOfOrder");
    }
    private void thankYou() {
      System.out.println("ThankYou");
    }
    boolean unlock() {
      // perhaps you can provide a more sophisticated unlock simulation!
      boolean result = true;
      return result;
    }
    void unlockError() {
      System.out.println("UnlockError");
    }
    /*
     * State
     */
    void simpleTransitionTo(State s) {
      myTransit.leaveFor(s);
      myTransit.enter();
    }
    private class ActionLocked extends StateActionAdapter {
      private boolean myLockSuccess;
      public void enterAction() {
        
        myLockSuccess = lock();
      }
      public void action() {
        
        if (! myLockSuccess) {
          lockError();
          simpleTransitionTo(myStateBroken);
        }
      }
    }
    private class ActionUnlocked extends StateActionAdapter {
      private boolean myUnlockSuccess;
      public void enterAction() {
        System.out.println("enterAction");
        myUnlockSuccess = unlock();
      }
      public void action() {
        System.out.println("action");
        if (! myUnlockSuccess) {
          unlockError();
          simpleTransitionTo(myStateBroken);
        }
      }
      public void leaveAction() {
        System.out.println("leaveAction");
      }
    }
    private class ActionBroken extends StateActionAdapter {
      public void enterAction() {
        outOfOrder();
      }
      public void leaveAction() {
        inOrder();
      }
    }
    /*
     * Test driver
     */
    public static void main(String[] args) {
      MonTest t = new MonTest();
      //System.out.println(t.myStateLocked.isActive());
      System.out.println("vor coin");
      t.coin();
      System.out.println("nach coin");
      t.pass();

      

      //t.coin();

      //t.pass();
      //t.coin();
      /*
      t.fixed();
      t.coin();
      t.pass();
      t.fixed();
      t.pass();
      */
    }
  }

