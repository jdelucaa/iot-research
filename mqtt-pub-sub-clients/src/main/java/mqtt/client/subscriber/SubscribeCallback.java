package mqtt.client.subscriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubscribeCallback implements MqttCallback {

	public void connectionLost(final Throwable cause) {

	}

	public void messageArrived(final String topic, final MqttMessage message) throws Exception {
		System.out.println("Message arrived. Topic: " + topic + "  Message: " + message.toString());

		if ("home/LWT".equals(topic)) {
			System.err.println("Sensor gone!");
		}
	}

	public void deliveryComplete(final IMqttDeliveryToken token) {

	}

}
