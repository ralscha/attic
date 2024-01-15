package ch.ess.test.state;

import java.util.*;

import ch.ess.state.*;

public class BeschaffungSM {

  private static BeschaffungSM instance;
  
  private StateMachine sm;
  

  private final static Map ANGEBOTE = new HashMap();
  private final static Map ANFRAGEN = new HashMap();

  static {
    //DB
    Anfragen aone = new Anfragen(1l);
    aone.setStatus(new Integer(0));
    Anfragen atwo = new Anfragen(2l);
    atwo.setStatus(new Integer(0));
    Anfragen athree = new Anfragen(3l);
    athree.setStatus(new Integer(0));
      
    Angebote bone = new Angebote(1l);
    bone.setAnfrageId(aone.getId());
      
    Angebote btwo = new Angebote(2l);
    btwo.setAnfrageId(atwo.getId());
      
    ANFRAGEN.put(new Long(aone.getId()), aone);
    ANFRAGEN.put(new Long(atwo.getId()), atwo);
    ANFRAGEN.put(new Long(athree.getId()), athree);
      
       
    ANGEBOTE.put(new Long(bone.getId()), bone);
    ANGEBOTE.put(new Long(btwo.getId()), btwo);
    
    try {
      instance = new BeschaffungSM("/ch/ess/state/test/beschaffungsm.xml");
    } catch (StateMachineException e) {
      e.printStackTrace();
    }
  
  }
  
  private BeschaffungSM(String path) throws StateMachineException {
    sm = StateMachine.createStateMachine(path);
  }


  public static void doEventAnfrage(long anfrageId, String eventName, Object guardObj) throws StateMachineException {

    Anfragen anfrage = getAnfrage(anfrageId);

    State currentState = instance.sm.lookupState(anfrage.getStatus());
    StateTransit transit = new StateTransit(currentState);

    transit.getContext().put("anfrage", anfrage);

    instance.sm.doEvent(transit, eventName, guardObj);
  }

  public static void doEventAngebot(long angebotId, String eventName, Object guardObj) throws StateMachineException {

   
    Angebote angebot = getAngebot(angebotId);    
    Anfragen anfrage = getAnfrage(angebot.getAnfrageId());

    State currentState = instance.sm.lookupState(anfrage.getStatus());
    StateTransit transit = new StateTransit(currentState);
    transit.getContext().put("anfrage", anfrage);
    transit.getContext().put("angebot", angebot);    

    instance.sm.doEvent(transit, eventName, guardObj);


  }

  private StateTransit createStateTransit(long anfrageId) {

    Anfragen anfrage = getAnfrage(anfrageId);

    State currentState = sm.lookupState(anfrage.getStatus());
    StateTransit transit = new StateTransit(currentState);

    return transit;
  }



  public static boolean isVisible(User theUser, String permission, String eventName, long anfrageNr, long angebotNr) {
    if (angebotNr != -1) {
      // search anfrage for this angebot
      anfrageNr = getAngebot(angebotNr).getAnfrageId();
    }
    
    // set the state
    StateTransit transit = instance.createStateTransit(anfrageNr);
    if (theUser.hasPermission(permission)) {
      return instance.sm.hasActiveStateEvent(transit, eventName, new StringGuard(permission));
    }
    return false;
  }

  public static boolean showItem(User theUser, String recht, long anfrageNr) {
    StateTransit transit = instance.createStateTransit(anfrageNr);
    State state = transit.getActiveState();

    if (theUser.hasPermission(recht)) {

      Set rechte = state.getStateAction().getPermissions();
      Iterator it = rechte.iterator();
      while (it.hasNext()) {
        String element = (String) it.next();
        if (element.startsWith(recht)) {
          return true;
        } 
      }
    }
    return false;
  }

  public static boolean showItemRW(User theUser, String recht, long anfrageNr) {
    StateTransit transit = instance.createStateTransit(anfrageNr);
    State state = transit.getActiveState();

    if (theUser.hasPermission(recht)) {

      Set rechte = state.getStateAction().getPermissions();
      Iterator it = rechte.iterator();
      while (it.hasNext()) {
        String element = (String) it.next();
        if (element.startsWith(recht) && element.endsWith("bearbeiten")) {
          return true;
        } 
      }
    }
    return false;
  }

 
  //Utility methods
  private static Anfragen getAnfrage(long id) {
    return (Anfragen)ANFRAGEN.get(new Long(id));
  }

  private static Angebote getAngebot(long id) {
    return (Angebote)ANGEBOTE.get(new Long(id));
  }



  public static void main(String[] args) {
    
    try {
      
      System.out.println("anfrage 1");
      System.out.println("event: BedarfNeu");
      BeschaffungSM.doEventAnfrage(1l, "BedarfNeu", "beschaffung.bedarf.bearbeiten");
      
      System.out.println("event: BedarfSpeichern");
      BeschaffungSM.doEventAnfrage(1l, "BedarfSpeichern", "beschaffung.bedarf.bearbeiten");

      System.out.println("anfrage 2");
      System.out.println("event: BedarfNeu");
      BeschaffungSM.doEventAnfrage(2l, "BedarfNeu", null);
      
      System.out.println("event: BedarfSpeichern");
      BeschaffungSM.doEventAnfrage(2l, "BedarfSpeichern", null);

    
    } catch (StateMachineException e) {
      
      e.printStackTrace();
    }  
    
  }
}
