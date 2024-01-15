package ch.ess.langtool;

import javax.swing.JTextArea;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;

public class StatusTextArea extends JTextArea implements EventTopicSubscriber {
  
  public StatusTextArea() {
    super();
    setEditable(false);
    EventBus.subscribe("log", this);
    EventBus.subscribe("clear", this);
  }
  
  public void onEvent(@SuppressWarnings("unused") String topic, Object obj) {
    
    if ("log".equals(topic)) {
      String currentValue = getText();
      if (currentValue != null) {
        if (currentValue.length() > 0) {
          currentValue += "\n";
        }
        currentValue += (String)obj;
      } else {
        currentValue = (String)obj;
      }
      setText(currentValue);
    } else if ("clear".equals(topic)) {
      setText(null);
    }
  }
}
