import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttReceiveSample {

	public static void main(String[] args) {

		String topic = "MQTT Examples";
		String broker = "tcp://192.168.99.100:1883";
		String clientId = "JavaSampleReceive";
		int qos = 2;
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");

			sampleClient.setCallback(new MqttCallback() {

				@Override
				public void messageArrived(String top, MqttMessage message)
						throws Exception {
					System.out.println(top);
					System.out.println(message);
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println(token);
				}

				@Override
				public void connectionLost(Throwable cause) {
					System.out.println(cause);
				}
			});
			sampleClient.subscribe(topic, qos);

			TimeUnit.MINUTES.sleep(5);
		}
		catch (InterruptedException | MqttException me) {
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}
}