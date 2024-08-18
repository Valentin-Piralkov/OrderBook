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

    public KrakenConnectedData() {
    }

    public String getApi_version() {
        return api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    public String getConnection_id() {
        return connection_id;
    }

    public void setConnection_id(String connection_id) {
        this.connection_id = connection_id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
