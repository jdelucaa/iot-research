package mqtt.client.subscriber;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import mqtt.client.util.Util;

public class Subscriber {

	public static final String BROKER_URL = "tcp://localhost:1883";
	private MqttClient mqttClient;

	public Subscriber() {
		final String clientId = Util.getMacAddress() + "-sub";

		try {
			mqttClient = new MqttClient(BROKER_URL, clientId);
		} catch (final MqttException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not create the MQTT client.");
		}
	}

	public void start() {
		try {
			mqttClient.setCallback(new SubscribeCallback());
			mqttClient.connect();

			final String topic = "local/#";
			mqttClient.subscribe(topic, 1);

			System.out.println("Subscriber is now listening to " + topic);

		} catch (final MqttException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not start the MQTT client.");
		}
	}

	public static void main(final String... args) {
		final Subscriber subscriber = new Subscriber();
		subscriber.start();
	}
}
