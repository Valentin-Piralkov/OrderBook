package vpiralkov.dstructs;

import vpiralkov.dtos.KrakenUpdateResponse;
import vpiralkov.dtos.PriceQuantityPair;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

/**
 * This class represents an order book for a specific currency pair.
 * It maintains a list of bids and asks for the currency pair, which are stored in TreeMaps.
 *
 * Parameters:
 * - pair: The currency pair for which the order book is created.
 * - bids: A TreeMap that stores the bids in the order book.
 * - asks: A TreeMap that stores the asks in the order book.
 *
 * Methods:
 * - updateBid: Updates the bid price and quantity in the order book.
 * - updateAsk: Updates the ask price and quantity in the order book.
 * - updateOrderBook: Updates the order book based on a KrakenUpdateResponse object.
 * - printOrders: Prints the order book on the console, including the best ask, best bid, and timestamp.
 **/

public class OrderBook {
    private final String pair;
    private final TreeMap<Double, Double> bids;
    private final TreeMap<Double, Double> asks;

    public OrderBook(String pair) {
        this.pair = pair;
        this.bids = new TreeMap<>(Comparator.reverseOrder());
        this.asks = new TreeMap<>(Comparator.reverseOrder());
    }

    public void updateBid(double price, double qty) {
        if (qty == 0) {
            bids.remove(price);
        } else {
            bids.put(price, qty);
        }
    }

    public void updateAsk(double price, double qty) {
        if (qty == 0) {
            asks.remove(price);
        } else {
            asks.put(price, qty);
        }
    }

    public void updateOrderBook(KrakenUpdateResponse krakenUpdateResponse) {
        // Check if the update is for the correct currency pair
        if (!Objects.equals(krakenUpdateResponse.getSymbol(), pair)) {
            return;
        }
        // Update the order book with the new bids and asks
        if (krakenUpdateResponse.getBids() != null) {
            for (PriceQuantityPair bid : krakenUpdateResponse.getBids()) {
                updateBid(bid.price, bid.qty);
            }
        }
        // Update the order book with the new asks
        if (krakenUpdateResponse.getAsks() != null) {
            for (PriceQuantityPair ask : krakenUpdateResponse.getAsks()) {
                updateAsk(ask.price, ask.qty);
            }
        }
    }

    public void printOrders(String symbol, String timestamp) {
        System.out.println("<------------------------------------>");
        System.out.println("Asks:");
        asks.forEach((price, qty) -> System.out.println("[ " + price + ", " + qty + " ]"));
        System.out.println("Best ask: [ " + asks.lastEntry().getKey() + ", " + asks.lastEntry().getValue() + " ]");
        System.out.println("Best bid: [ " + bids.firstEntry().getKey() + ", " + bids.firstEntry().getValue() + " ]");
        System.out.println("Bids:");
        bids.forEach((price, qty) -> System.out.println("[ " + price + ", " + qty + " ]"));
        System.out.println(timestamp);
        System.out.println(symbol);
        System.out.println(">------------------------------------<");
    }
}
