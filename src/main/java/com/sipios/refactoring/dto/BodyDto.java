package com.sipios.refactoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BodyDto {
    private List<ItemDto> items;
    private CustomerTypeDto type;
}
