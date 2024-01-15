import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;


public class BitTest {
  public static void main(String[] args) {
    
    List eventList = new ArrayList();
    
    BitTestBean btb = new BitTestBean();
    btb.getIdList().add(new Integer(12));
    btb.getDistribution().set(0);
    btb.getDistribution().set(1);
    btb.getDistribution().set(2);
    btb.getDistribution().set(3);    
    eventList.add(btb);

    btb = new BitTestBean();
    btb.getIdList().add(new Integer(13));
    btb.getDistribution().set(4);
    btb.getDistribution().set(5);
    btb.getDistribution().set(6);    
    eventList.add(btb);
    
    btb = new BitTestBean();
    btb.getIdList().add(new Integer(14));
    btb.getDistribution().set(5);
    btb.getDistribution().set(6);    
    eventList.add(btb);    
    
    btb = new BitTestBean();
    btb.getIdList().add(new Integer(15));
    btb.getDistribution().set(10);
    btb.getDistribution().set(11);    
    eventList.add(btb);    

    if (eventList.size() > 1) {
      for (int i = 1; i < eventList.size(); i++) {
        BitTestBean bt = (BitTestBean)eventList.get(i);
        
        for (int j = 0; j < i; j++) {
          BitTestBean btc = (BitTestBean)eventList.get(j);
          
          BitSet tmp = (BitSet)btc.getDistribution().clone();
          tmp.and(bt.getDistribution());
          if (tmp.isEmpty()) {
            //hat platz, move
            btc.getDistribution().or(bt.getDistribution());
            btc.getIdList().addAll(bt.getIdList());            
            eventList.remove(i);
            break;
          }

        }
      }
    }
    

    System.out.println(eventList);
    
  }
}
