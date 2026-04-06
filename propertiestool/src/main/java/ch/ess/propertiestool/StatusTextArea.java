package ch.ess.propertiestool;

import javax.swing.JTextArea;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;

public class StatusTextArea extends JTextArea implements EventTopicSubscriber<String> {

	private static final long serialVersionUID = 1L;

	public StatusTextArea() {
		super();
		setEditable(false);
		EventBus.subscribe("log", this);
		EventBus.subscribe("clear", this);
	}

	@Override
	public void onEvent(String topic, String data) {

		if ("log".equals(topic)) {
			String currentValue = getText();
			if (currentValue != null) {
				if (currentValue.length() > 0) {
					currentValue += "\n";
				}
				currentValue += data;
			}
			else {
				currentValue = data;
			}
			setText(currentValue);
		}
		else if ("clear".equals(topic)) {
			setText(null);
		}
	}
}
