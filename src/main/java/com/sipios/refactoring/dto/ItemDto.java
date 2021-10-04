package com.sipios.refactoring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private String type;
    @JsonProperty("nb")
    private int quantity;

    public ItemDto(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }
}
