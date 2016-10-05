package coap.prototype;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Sensor implements Runnable {

	private static final long INTERVAL = 300;
	private static final URL FILENAME = Sensor.class.getClassLoader().getResource("sensor-data.csv");
	private final int port;
	private Listener listener;

	private String sensorData = "";

	public String getSensorData() {
		return sensorData;
	}

	public Sensor(final int port) {
		this.port = port;
	}

	public void setListener(final Listener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try (BufferedReader fileReader = new BufferedReader(new FileReader(FILENAME.getPath()))) {
				String csvLine = fileReader.readLine();
				while ((csvLine = fileReader.readLine()) != null && !(csvLine = csvLine.trim()).isEmpty()) {
					sensorData = csvLine;
					if (listener != null) {
						listener.update(port, sensorData);
						Thread.sleep(INTERVAL);
					}
				}
			} catch (final InterruptedException | IOException ex) {
				ex.printStackTrace();
				System.out.println("Sensor stopped!");
			}
		}
	}
}