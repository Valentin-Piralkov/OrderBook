package vpiralkov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenUpdateData {

    @JsonProperty("symbol")
    public String symbol;

    @JsonProperty("bids")
    public PriceQuantityPair[] bids;

    @JsonProperty("asks")
    public PriceQuantityPair[] asks;

    @JsonProperty("checksum")
    public long checksum;

    @JsonProperty("timestamp")
    public String timestamp;

    public KrakenUpdateData(String symbol, PriceQuantityPair[] bids, PriceQuantityPair[] asks, long checksum, String timestamp) {
        this.symbol = symbol;
        this.bids = bids;
        this.asks = asks;
        this.checksum = checksum;
        this.timestamp = timestamp;
    }

    public KrakenUpdateData() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public PriceQuantityPair[] getBids() {
        return bids;
    }

    public void setBids(PriceQuantityPair[] bids) {
        this.bids = bids;
    }

    public PriceQuantityPair[] getAsks() {
        return asks;
    }

    public void setAsks(PriceQuantityPair[] asks) {
        this.asks = asks;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
