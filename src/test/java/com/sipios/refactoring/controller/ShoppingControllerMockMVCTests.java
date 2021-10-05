package com.sipios.refactoring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sipios.refactoring.dto.BodyDto;
import com.sipios.refactoring.dto.ItemDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingControllerMockMVCTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void it_should_calculate_cart_price_for_tshirt() throws Exception {
        // Given
        String bodyRequest = "{\"type\": \"STANDARD_CUSTOMER\", \"items\":[{\"type\":\"TSHIRT\", \"nb\":1}]}";
        String expectedPrice = "30.0";
        // When
        MvcResult mvcResult = this.mockMvc.perform(
                post("/shopping")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyRequest)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(status().isOk())
            .andReturn();
        // Then
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedPrice);
    }

    @Test
    void it_should_calculate_cart_price_for_dress() throws Exception {
        // Given
        String bodyRequest = "{\"type\": \"STANDARD_CUSTOMER\", \"items\":[{\"type\":\"DRESS\", \"nb\":1}]}";
        String expectedPrice = "50.0";
        // When
        MvcResult mvcResult = this.mockMvc.perform(
                post("/shopping")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyRequest)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(status().isOk())
            .andReturn();
        // Then
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedPrice);
    }

    @Test
    void it_should_calculate_cart_price_for_jacket() throws Exception {
        // Given
        String bodyRequest = "{\"type\": \"STANDARD_CUSTOMER\", \"items\":[{\"type\":\"JACKET\", \"nb\":1}]}";
        String expectedPrice = "100.0";
        // When
        MvcResult mvcResult = this.mockMvc.perform(
                post("/shopping")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyRequest)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(status().isOk())
            .andReturn();
        // Then
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedPrice);
    }


    @Test
    void it_should_not_support_unknown_type_of_customer() throws Exception {
        // Given
        String bodyRequest = "{\"type\": \"CUSTOM_CUSTOMER\", \"items\":[{\"type\":\"JACKET\", \"nb\":1}]}";
        String expectedPrice = "100.0";
        // When
        MvcResult mvcResult = this.mockMvc.perform(
                post("/shopping")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyRequest)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(status().isBadRequest())
            .andReturn();
    }
}
