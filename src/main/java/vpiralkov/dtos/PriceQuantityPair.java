package vpiralkov.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceQuantityPair {
    @JsonProperty("price")
    public double price;

    @JsonProperty("qty")
    public double qty;

    public PriceQuantityPair() {
    }

    public PriceQuantityPair(double price, double qty) {
        this.price = price;
        this.qty = qty;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQty() {
        return this.qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}
