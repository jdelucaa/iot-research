package mqtt.client.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

public class Util {

	public static String getMacAddress() {
		String result = "";

		try {
			for (final NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
				final byte[] hardwareAddress = ni.getHardwareAddress();

				if (hardwareAddress != null) {
					for (int i = 0; i < hardwareAddress.length; i++)
						result += String.format((i == 0 ? "" : "-") + "%02X", hardwareAddress[i]);

					return result;
				}
			}

		} catch (final SocketException e) {
			System.out.println("Could not find out MAC Adress. Exiting Application.");
			System.exit(1);
		}
		return result;
	}
}
