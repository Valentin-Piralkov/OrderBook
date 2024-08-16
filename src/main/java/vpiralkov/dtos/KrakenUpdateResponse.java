package vpiralkov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenUpdateResponse {
    @JsonProperty("channel")
    public String channel;

    @JsonProperty("type")
    public String type;

    @JsonProperty("data")
    public KrakenUpdateData[] data;

    public String getChannel() {
        return channel;
    }

    public String getType() {
        return type;
    }

    public String getSymbol() {
        if (data == null) {
            return null;
        }
        return data[0].symbol;
    }

    public long getChecksum() {
        if (data == null) {
            return 0;
        }
        return data[0].checksum;
    }

    public PriceQuantityPair[] getBids() {
        if (data == null) {
            return null;
        }
        return data[0].bids;
    }

    public PriceQuantityPair[] getAsks() {
        if (data == null) {
            return null;
        }
        return data[0].asks;
    }

    public String getTimestamp() {
        if (data == null) {
            return null;
        }
        return data[0].timestamp;
    }

}
