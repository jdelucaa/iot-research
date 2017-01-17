package coap.prototype;

import java.util.Map;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class SensorResource extends CoapResource implements Listener {

    private final Map<Integer, Sensor> sensors;

    public SensorResource(final Map<Integer, Sensor> sensors) {
        super("sensor");

        this.sensors = sensors;

        getAttributes().setTitle("Sensor Resource");
        getAttributes().setObservable();

        setObservable(true);
        setObserveType(CoAP.Type.CON);
    }

    @Override
    public void handleGET(final CoapExchange exchange) {
        System.out.println("handleGet called");
        final int port = exchange.advanced().getEndpoint().getAddress().getPort();
        exchange.respond(sensors.get(port).getSensorData());
    }

    @Override
    public void handleDELETE(final CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.DELETED);
    }

    @Override
    public void handlePUT(final CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.CHANGED);
    }

    @Override
    public void handlePOST(final CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.CREATED);
    }

    @Override
    public void handleRequest(final Exchange exchange) {
        super.handleRequest(exchange);
    }

    @Override
    public void update(final int port, final String sensorResource) {
        System.out.println(System.currentTimeMillis() + " sensor resource updated: " + sensorResource);
        changed();
    }

}
