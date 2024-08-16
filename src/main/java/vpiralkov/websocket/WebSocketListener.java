package vpiralkov.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import vpiralkov.dstructs.OrderBook;
import vpiralkov.dtos.KrakenAPIState;
import vpiralkov.dtos.KrakenConnectedResponse;
import vpiralkov.dtos.KrakenSubscribedResponse;
import vpiralkov.dtos.KrakenUpdateResponse;
import vpiralkov.env.EnvironmentGlobals;
import vpiralkov.utils.JsonReader;

import java.io.IOException;
import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.concurrent.CompletionStage;

/**
 * This class implements the WebSocket.Listener interface and is used to handle WebSocket events.
 * It maintains the state of the Kraken API and manages order books for different currency pairs.
 *
 * Parameters:
 * - state: Stores the current state of the WebSocket. When state is SUBSCRIBED, the class starts to listen for book messages.
 * - orderBooks: Stores a separate order book for each currency pair
 *
 * Events:
 * - onOpen: Prints a message when the WebSocket connection is established and requests the first message.
 * - onText: Handles incoming text messages based on the current state of the connection. It can parse connection messages, subscription results, and order book updates.
 * - onClose: Prints a message when the WebSocket connection is closed, including the status code and reason for closure.
 * - onError: Prints an error message when a WebSocket error occurs.
 *
 * The class uses the Jackson library to parse JSON messages and updates the order books accordingly.
 */

public class WebSocketListener implements WebSocket.Listener {
    private final KrakenAPIState state;
    private final HashMap<String, OrderBook> orderBooks = new HashMap<>();

    public WebSocketListener() {
        super();
        this.state = new KrakenAPIState(EnvironmentGlobals.PAIRS);
        this.orderBooks.put("BTC/USD", new OrderBook("BTC/USD"));
        this.orderBooks.put("ETH/USD", new OrderBook("ETH/USD"));
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("WebSocket connected");
        //request the first message
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        ObjectMapper objectMapper = new ObjectMapper();
        // check the current state of the connection
        switch (state.getCurrentState()) {
            case KrakenAPIState.State.DISCONNECTED ->  {
                try {
                    // parse connection message
                    KrakenConnectedResponse krakenConnectedResponse = objectMapper.readValue(data.toString(), KrakenConnectedResponse.class);
                    // check if ok
                    if (krakenConnectedResponse.getSystem().equals("online")) {
                        state.setConnected();
                        // send subscription message
                        JsonReader jsonReader = new JsonReader();
                        String subscribeMessage = jsonReader.getSubscribeMessage(EnvironmentGlobals.FILE_PATH);
                        webSocket.sendText(subscribeMessage, true);
                        System.out.println("Connected to Kraken API with status: " + krakenConnectedResponse.getSystem());
                    }
                    else {
                        // close the connection
                        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Kraken API is offline");
                    }
                } catch (IOException e) {
                    webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Error connecting to Kraken API");
                    throw new RuntimeException(e);
                }

            }
            case KrakenAPIState.State.CONNECTED_SUCCESSFULLY -> {
                try {
                    // parse subscription result
                    KrakenSubscribedResponse krakenSubscribedResponse = objectMapper.readValue(data.toString(), KrakenSubscribedResponse.class);
                    // check if ok
                    if (krakenSubscribedResponse.getSuccess()) {
                        // set subscription state
                        state.setSubscribed(krakenSubscribedResponse.getSymbol());
                        System.out.println("Subscribed to: " + krakenSubscribedResponse.getSymbol());
                    }
                } catch (JsonProcessingException e) {
                    webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Error parsing connection message");
                    throw new RuntimeException(e);
                }
            }
            case KrakenAPIState.State.SUBSCRIBED -> {
                try {
                    // parse order book
                    KrakenUpdateResponse krakenUpdateResponse = objectMapper.readValue(data.toString(), KrakenUpdateResponse.class);
                    // update order book
                    String symbol = krakenUpdateResponse.getSymbol();
                    if (symbol != null) {
                        OrderBook orderBook = this.orderBooks.get(symbol);
                        orderBook.updateOrderBook(krakenUpdateResponse);
                        orderBook.printOrders(krakenUpdateResponse.getSymbol(), krakenUpdateResponse.getTimestamp());
                    }
                } catch (JsonProcessingException e) {
                    webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Error parsing order book message");
                    throw new RuntimeException(e);
                }
            }
        }
        //request the next message
        webSocket.request(1);
        return null;
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed with statusCode: " + statusCode + " \nReason: " + reason);
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("WebSocket error: " + error.getMessage());
    }
}
