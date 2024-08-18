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

    public String getPair() {
        return pair;
    }

    public TreeMap<Double, Double> getBids() {
        return bids;
    }

    public TreeMap<Double, Double> getAsks() {
        return asks;
    }

    public void updateBid(double price, double qty) {
        if (qty == 0) {
            this.bids.remove(price);
        } else {
            this.bids.put(price, qty);
        }
    }

    public void updateAsk(double price, double qty) {
        if (qty == 0) {
            this.asks.remove(price);
        } else {
            this.asks.put(price, qty);
        }
    }

    public void updateOrderBook(KrakenUpdateResponse krakenUpdateResponse) {
        // Check if the update is for the correct currency pair
        if (!Objects.equals(krakenUpdateResponse.getSymbol(), this.pair)) {
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
        // print the asks as a 2D array
        for (int i = 0; i < this.asks.size(); i++) {
            if (i == 0) {
                System.out.println("[ [ " + this.asks.keySet().toArray()[i] + ", " + this.asks.values().toArray()[i] + " ],");
            }
            else if (i == this.asks.size() - 1) {
                System.out.println("  [ " + this.asks.keySet().toArray()[i] + ", " + this.asks.values().toArray()[i] + " ] ]");
            }
            else {
                System.out.println("  [ " + this.asks.keySet().toArray()[i] + ", " + this.asks.values().toArray()[i] + " ],");
            }
        }
        // print the best ask and best bid
        System.out.println("Best ask: [ " + this.asks.lastEntry().getKey() + ", " + this.asks.lastEntry().getValue() + " ]");
        System.out.println("Best bid: [ " + this.bids.firstEntry().getKey() + ", " + this.bids.firstEntry().getValue() + " ]");
        System.out.println("Bids:");
        // print the bids as a 2D array
        for (int i = 0; i < this.bids.size(); i++) {
            if (i == 0) {
                System.out.println("[ [ " + this.bids.keySet().toArray()[i] + ", " + this.bids.values().toArray()[i] + " ],");
            }
            else if (i == this.bids.size() - 1) {
                System.out.println("  [ " + this.bids.keySet().toArray()[i] + ", " + this.bids.values().toArray()[i] + " ] ]");
            }
            else {
                System.out.println("  [ " + this.bids.keySet().toArray()[i] + ", " + this.bids.values().toArray()[i] + " ],");
            }
        }
        if (timestamp != null) {
            System.out.println(timestamp);
        }
        System.out.println(symbol);
        System.out.println(">------------------------------------<");
    }
}
