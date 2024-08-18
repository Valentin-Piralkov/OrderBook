package vpiralkov.dstructs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vpiralkov.dtos.KrakenUpdateData;
import vpiralkov.dtos.KrakenUpdateResponse;
import vpiralkov.dtos.PriceQuantityPair;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class OrderBookTest {
    private OrderBook orderBook;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    public void setUpBook() {
        this.orderBook = new OrderBook("BTC/USD");
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testUpdateBid() {
        this.orderBook.updateBid(5000.0, 1.0);
        Double expectedQty = orderBook.getBids().get(5000.0);
        assertEquals(1.0, expectedQty, "Expected bid quantity is 1.0. Received: " + expectedQty);
    }

    @Test
    public void testUpdateAsk() {
        orderBook.updateAsk(6000.0, 2.0);
        Double expectedQty = orderBook.getAsks().get(6000.0);
        assertEquals(2.0, expectedQty, "Expected ask quantity is 2.0. Received: " + expectedQty);
    }

    @Test
    public void testUpdateOrderBook() {
        KrakenUpdateResponse krakenUpdateResponse = new KrakenUpdateResponse();
        KrakenUpdateData krakenUpdateData = new KrakenUpdateData("BTC/USD", null, null, 0, "2022-01-01T00:00:00Z");
        PriceQuantityPair[] bids = {new PriceQuantityPair(5000.0, 1.0)};
        PriceQuantityPair[] asks = {new PriceQuantityPair(6000.0, 2.0)};
        krakenUpdateData.setBids(bids);
        krakenUpdateData.setAsks(asks);
        krakenUpdateResponse.setData(new KrakenUpdateData[]{krakenUpdateData});

        orderBook.updateOrderBook(krakenUpdateResponse);

        assertEquals(1.0, orderBook.getBids().get(5000.0), "Expected bid quantity is 1.0. Received: " + orderBook.getBids().get(5000.0));
        assertEquals(2.0, orderBook.getAsks().get(6000.0), "Expected ask quantity is 2.0. Received: " + orderBook.getAsks().get(6000.0));
    }

    @Test
    public void testPrintOrders() {
        orderBook.updateBid(5000.0, 1.0);
        orderBook.updateAsk(6000.0, 2.0);

        orderBook.printOrders("BTC/USD", "2022-01-01T00:00:00Z");

        assertTrue(outContent.toString().contains("Best bid: [ 5000.0, 1.0 ]"));
        assertTrue(outContent.toString().contains("Best ask: [ 6000.0, 2.0 ]"));
    }
}
