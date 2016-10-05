package coap.prototype;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoAPEndpoint;

public class Server extends CoapServer {

	private static final int PORT = 5683;
	private final Map<Integer, Sensor> sensors = new HashMap<>();

	public Server() {
		super();

		final SensorResource resource = new SensorResource(sensors);

			final int port = PORT;
			addEndpoint(new CoAPEndpoint(port));
			final Sensor sensor = new Sensor(port);
			sensor.setListener(resource);
			sensors.put(port, sensor);

		add(resource);
	}

	public static void main(final String[] args) {
		final Server server = new Server();
		server.start();
	}

	@Override
	public void start() {
		final Collection<Sensor> values = sensors.values();
		for (final Sensor sensor : values) {
			new Thread(sensor).start();
		}
		super.start();
	}

}
