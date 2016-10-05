package coap.prototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;

public class Client {

	public static void main(final String[] args) {
		final CoapClient client = new CoapClient("coap://localhost:5683/sensor");

		System.out.println("Press ENTER to start observing");

		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			br.readLine();
		} catch (final IOException e) {
		}

		System.out.println("OBSERVING - ENTER to stop)");

		final CoapObserveRelation relation = client.observe(new CoapHandler() {
			@Override
			public void onLoad(final CoapResponse response) {
				final String content = response.getResponseText();
				System.out.println(System.currentTimeMillis() + " NOTIFICATION: " + content);
			}

			@Override
			public void onError() {
				System.err.println("OBSERVING FAILED (press enter to exit)");
			}
		});

		try {
			br.readLine();
		} catch (final IOException e) {
		}

		System.out.println("CANCELED");
		relation.proactiveCancel();
	}
}
