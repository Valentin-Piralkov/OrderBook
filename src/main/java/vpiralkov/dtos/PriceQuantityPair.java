package vpiralkov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceQuantityPair {
    @JsonProperty("price")
    public double price;

    @JsonProperty("qty")
    public double qty;
}
