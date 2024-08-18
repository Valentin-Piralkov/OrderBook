package vpiralkov.websocket;

import vpiralkov.env.EnvironmentGlobals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CountDownLatch;

/**
 * This class represents a WebSocket client for the Kraken API.
 * It establishes a WebSocket connection to a given URI and uses a WebSocketListener to handle incoming messages.
 * The class uses the java.net.http.WebSocket library to manage the WebSocket connection.
 * It also uses a CountDownLatch to ensure that the client waits for the WebSocket connection to be established before proceeding.
 **/

public class WebSocketClient {
    private static final CountDownLatch latch = new CountDownLatch(1);

    private final URI uri;
    private WebSocket webSocket;

    public WebSocketClient(URI uri) {
        this.uri = uri;
    }

    public void connect() {
        try {
            //connect web socket
            WebSocket.Builder builder = HttpClient.newHttpClient().newWebSocketBuilder();
            webSocket = builder.buildAsync(this.uri, new WebSocketListener()).join();
            // wait for client to establish connection
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        WebSocketClient client = new WebSocketClient(URI.create(EnvironmentGlobals.WSS));
        client.connect();
    }
}
