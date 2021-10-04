package com.sipios.refactoring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BodyDto {
    private ItemDto[] items;
    private String type;

    public BodyDto(ItemDto[] items, String type) {
        this.items = items;
        this.type = type;
    }
}
