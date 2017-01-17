package mqtt.client.publisher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import mqtt.client.util.Util;

public class Publisher {

	private static final String BROKER_URL = "tcp://localhost:1883";
	private static final String SENSOR_TOPIC = "local/sensor";
	private static final URL FILENAME = Publisher.class.getClassLoader().getResource("sensor-data.csv");
	private MqttClient client;

	public Publisher() {
		final String clientId = Util.getMacAddress() + "-pub";

		try {
			client = new MqttClient(BROKER_URL, clientId);
		} catch (final MqttException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not create the MQTT client.");
		}
	}

	private void start() {

			final MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(false);
			options.setWill(client.getTopic("local/LWT"), "I'm gone :(".getBytes(), 0, false);

			try {
				client.connect(options);
			} catch (final MqttException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not start the MQTT client.");
			}

			while (true) {
				publishTopics();
			}
	}

	private void publishTopics() {
		final MqttTopic sensorTopic = client.getTopic(SENSOR_TOPIC);

		try (BufferedReader fileReader = new BufferedReader(new FileReader(FILENAME.getPath()))) {
			String csvLine = fileReader.readLine();
			while ((csvLine = fileReader.readLine()) != null && !(csvLine = csvLine.trim()).isEmpty()) {

				sensorTopic.publish(new MqttMessage(csvLine.getBytes()));
				System.out.println("Published data. Topic: " + sensorTopic.getName() + " Message: " + csvLine);

				Thread.sleep(300);
			}
		} catch (IOException | MqttException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(final String... args) {
		final Publisher publisher = new Publisher();
		publisher.start();
	}
}
