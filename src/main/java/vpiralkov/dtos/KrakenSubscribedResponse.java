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

    public KrakenSubscribedResponse() {
    }

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

    public void setMethod(String method) {
        this.method = method;
    }

    public void setResult(KrakenSubscribedResult result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTime_in(String time_in) {
        this.time_in = time_in;
    }

    public void setTime_out(String time_out) {
        this.time_out = time_out;
    }
}
