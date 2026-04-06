import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import ch.rasc.msg.PersonOuterClass.Person;
import ch.rasc.msg.PersonOuterClass.Person.Gender;

public class SimpleSend {

	public static void main(String[] args) throws MqttException {
		MqttClient client = new MqttClient("tcp://192.168.99.100:1883", "sender_id");
		MqttConnectOptions options = new MqttConnectOptions();
		// options.setUserName("username");
		// options.setPassword("password".toCharArray());
		client.connect(options);

		Person person = Person.newBuilder().setId(1).setName("Ralph")
				.setEmail("ralph@me.com").setActive(true).addHobbies("reading")
				.setGender(Gender.MALE).build();

		MqttMessage message = new MqttMessage(person.toByteArray());
		message.setRetained(true);
		client.publish("persons", message);
		client.disconnect();
		System.exit(0);
	}

}
