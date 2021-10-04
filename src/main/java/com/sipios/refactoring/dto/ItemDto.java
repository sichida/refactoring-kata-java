package com.sipios.refactoring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemDto {

    private String type;
    @JsonProperty("nb")
    private int quantity;

    public ItemDto() {}

    public ItemDto(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
