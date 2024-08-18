package vpiralkov.websocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vpiralkov.dstructs.OrderBook;
import vpiralkov.dtos.KrakenAPIState;
import vpiralkov.dtos.KrakenConnectedResponse;
import vpiralkov.dtos.KrakenSubscribedResponse;
import vpiralkov.dtos.KrakenUpdateResponse;

import static org.mockito.Mockito.*;

import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketListenerTest {

    @Mock
    private WebSocket mockWebSocket;

    @Mock
    private KrakenAPIState mockState;

    @Mock
    private KrakenConnectedResponse mockConnectedResponse;

    @Mock
    private KrakenSubscribedResponse mockSubscribedResponse;

    @Mock
    private KrakenUpdateResponse mockUpdateResponse;

    @Mock
    private WebSocketListener mockWebSocketListener;

    @InjectMocks
    private WebSocketListener webSocketListener = new WebSocketListener(List.of("BTC/USD"));

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(mockState.getCurrentState()).thenReturn(KrakenAPIState.State.DISCONNECTED);
    }

    @Test
    public void testOnOpen() {
        webSocketListener.onOpen(mockWebSocket);
        assertEquals(KrakenAPIState.State.DISCONNECTED, webSocketListener.getState(), "State should be DISCONNECTED");
        verify(mockWebSocket, times(1)).request(1);
    }

    @Test
    public void testOnTextDisconnected() throws Exception {
        when(mockConnectedResponse.getSystem()).thenReturn("online");
        when(mockState.getCurrentState()).thenReturn(KrakenAPIState.State.DISCONNECTED);

        String jsonResponse = "{\"channel\":\"status\",\"data\":[{\"api_version\":\"v2\",\"connection_id\":2194372320275201919,\"system\":\"online\",\"version\":\"2.0.7\"}],\"type\":\"update\"}";
        ObjectMapper mockObjectMapper = spy(ObjectMapper.class);
        doReturn(mockConnectedResponse).when(mockObjectMapper).readValue(anyString(), eq(KrakenConnectedResponse.class));

        webSocketListener.onText(mockWebSocket, jsonResponse, true);
        assertEquals(KrakenAPIState.State.CONNECTED_SUCCESSFULLY, webSocketListener.getState(), "State should be CONNECTED_SUCCESSFULLY");
        verify(mockWebSocket, times(1)).sendText(anyString(), eq(true));
        verify(mockWebSocket, times(1)).request(1);
    }

    @Test
    public void testOnTextConnected() throws Exception {
        when(mockSubscribedResponse.getSuccess()).thenReturn(true);
        when(mockState.getCurrentState()).thenReturn(KrakenAPIState.State.CONNECTED_SUCCESSFULLY);

        String jsonResponse = "{\"method\":\"subscribe\",\"result\":{\"channel\":\"book\",\"depth\":10,\"snapshot\":true,\"symbol\":\"BTC/USD\"},\"success\":true,\"time_in\":\"2024-08-18T11:04:53.498117Z\",\"time_out\":\"2024-08-18T11:04:53.498195Z\"}";

        ObjectMapper mockObjectMapper = spy(ObjectMapper.class);
        doReturn(mockSubscribedResponse).when(mockObjectMapper).readValue(anyString(), eq(KrakenSubscribedResponse.class));

        // Test
        webSocketListener.getKrakenAPIState().setConnected();
        webSocketListener.onText(mockWebSocket, jsonResponse, true);

        assertEquals(KrakenAPIState.State.SUBSCRIBED, webSocketListener.getState(), "State should be SUBSCRIBED");
        verify(mockWebSocket, times(1)).request(1);
    }

    @Test
    public void testOnTextSubscribed() throws Exception {
        when(mockUpdateResponse.getSymbol()).thenReturn("BTC/USD");
        when(mockState.getCurrentState()).thenReturn(KrakenAPIState.State.SUBSCRIBED);

        String jsonResponse = "{\"channel\":\"book\",\"type\":\"snapshot\",\"data\":[{\"symbol\":\"BTC/USD\",\"bids\":[{\"price\":59899.9,\"qty\":24.84299718}],\"asks\":[{\"price\":59935.5,\"qty\":2.91980967}],\"checksum\":2118592378}]}";
        ObjectMapper mockObjectMapper = spy(ObjectMapper.class);
        doReturn(mockUpdateResponse).when(mockObjectMapper).readValue(anyString(), eq(KrakenUpdateResponse.class));

        OrderBook mockOrderBook = new OrderBook("BTC/USD");
        HashMap<String, OrderBook> orderBooks = webSocketListener.getOrderBooks();
        orderBooks.put("BTC/USD", mockOrderBook);

        // Test
        webSocketListener.getKrakenAPIState().setSubscribed("BTC/USD");
        webSocketListener.onText(mockWebSocket, jsonResponse, true);

        assertEquals(KrakenAPIState.State.SUBSCRIBED, webSocketListener.getState(), "State should be SUBSCRIBED");
        assertEquals(mockOrderBook, orderBooks.get("BTC/USD"), "Order book should be updated");
        verify(mockWebSocket, times(1)).request(1);
    }

    @Test
    public void testOnClose() {
        mockWebSocketListener.onClose(mockWebSocket, 1000, "Test close");
        verify(mockWebSocketListener, times(1)).onClose(mockWebSocket, 1000, "Test close");
    }

    @Test
    public void testOnError() {
        Throwable mockError = new RuntimeException("WebSocket error");
        mockWebSocketListener.onError(mockWebSocket, mockError);
        verify(mockWebSocketListener, times(1)).onError(mockWebSocket, mockError);
    }
}
