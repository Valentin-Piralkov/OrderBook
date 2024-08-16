package vpiralkov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenSubscribedResult {
    @JsonProperty("channel")
    public String channel;

    @JsonProperty("depth")
    public int depth;

    @JsonProperty("snapshot")
    public boolean snapshot;

    @JsonProperty("symbol")
    public String symbol;
}
