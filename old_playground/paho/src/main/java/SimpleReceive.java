import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import ch.rasc.msg.PersonOuterClass.Person;

public class SimpleReceive {

	public static void main(String[] args) throws MqttException {
		MqttClient client = new MqttClient("tcp://192.168.99.100:1883", "receiver_id");
		client.connect();

		client.setCallback(new MqttCallback() {

			@Override
			public void messageArrived(String topic, MqttMessage message)
					throws Exception {
				Person person = Person.parseFrom(message.getPayload());
				System.out.println(person);
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
				// nothing here
			}

			@Override
			public void connectionLost(Throwable cause) {
				// nothing here
			}
		});
		client.subscribe("persons");

	}

}
