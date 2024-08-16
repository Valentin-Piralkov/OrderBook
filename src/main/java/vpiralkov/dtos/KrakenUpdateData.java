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
}
