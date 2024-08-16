package vpiralkov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenConnectedData {
    @JsonProperty("api_version")
    public String api_version;

    @JsonProperty("connection_id")
    public String connection_id;

    @JsonProperty("system")
    public String system;

    @JsonProperty("version")
    public String version;
}
