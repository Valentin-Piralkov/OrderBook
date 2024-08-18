package vpiralkov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenConnectedResponse {
    @JsonProperty("channel")
    public String channel;

    @JsonProperty("type")
    public String type;

    @JsonProperty("data")
    public KrakenConnectedData[] data;

    public KrakenConnectedResponse() {
    }

    public String getSystem() {
        return data[0].system;
    }

    public String getChannel() {
        return channel;
    }

    public String getVersion() {
        return data[0].api_version;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public KrakenConnectedData[] getData() {
        return data;
    }

    public void setData(KrakenConnectedData[] data) {
        this.data = data;
    }
}
