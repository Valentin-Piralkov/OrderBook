package vpiralkov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenConnectedResponse {
    @JsonProperty("channel")
    public String channel;

    @JsonProperty("type")
    public String type;

    @JsonProperty("data")
    public KrakenConnectedData[] data;

    public String getSystem() {
        return data[0].system;
    }

    public String getChannel() {
        return channel;
    }

    public String getVersion() {
        return data[0].api_version;
    }

}
