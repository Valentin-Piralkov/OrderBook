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

    public KrakenSubscribedResult() {
    }

    public String getChannel() {
        return channel;
    }

    public int getDepth() {
        return depth;
    }

    public boolean getSnapshot() {
        return snapshot;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setSnapshot(boolean snapshot) {
        this.snapshot = snapshot;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
