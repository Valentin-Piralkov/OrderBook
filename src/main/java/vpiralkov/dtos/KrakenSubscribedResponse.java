package vpiralkov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenSubscribedResponse {
    @JsonProperty("method")
    public String method;

    @JsonProperty("result")
    public KrakenSubscribedResult result;

    @JsonProperty("success")
    public boolean success;

    @JsonProperty("time_in")
    public String time_in;

    @JsonProperty("time_out")
    public String time_out;

    public String getMethod() {
        return method;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getTimeIn() {
        return time_in;
    }

    public String getTimeOut() {
        return time_out;
    }

    public String getSymbol() {
        return result.symbol;
    }

    public int getDepth() {
        return result.depth;
    }

    public boolean getSnapshot() {
        return result.snapshot;
    }

    public String getChannel() {
        return result.channel;
    }
}
