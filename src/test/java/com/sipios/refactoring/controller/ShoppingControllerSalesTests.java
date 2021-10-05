package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.date.DateTimeService;
import com.sipios.refactoring.dto.BodyDto;
import com.sipios.refactoring.dto.ItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.List;

import static com.sipios.refactoring.dto.CustomerTypeDto.*;
import static com.sipios.refactoring.dto.ItemTypeDto.*;
import static org.assertj.core.api.Assertions.assertThat;

class ShoppingControllerSalesTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;
    @Mock
    DateTimeService dateTimeService;

    @BeforeEach
    void configureSaleInJanuary() throws ParseException {
        Mockito.when(dateTimeService.isSales()).thenReturn(true);
    }

    @Test
    void it_should_calculate_price_for_empty_cart_for_standard_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(null, STANDARD_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo("0");
    }

    @Test
    void it_should_calculate_price_for_1_tshirt_for_standard_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(TSHIRT, 1)
        ), STANDARD_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("30").toString());
    }

    @Test
    void it_should_calculate_price_for_1_dress_for_standard_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(DRESS, 1)
        ), STANDARD_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("50") * 0.8));
    }

    @Test
    void it_should_calculate_price_for_1_jacket_for_standard_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(JACKET, 1)
        ), STANDARD_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("100") * 0.9));
    }

    @Test
    void it_should_calculate_price_for_multiple_items_for_standard_customer_during_sales() {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(TSHIRT, 1),
            new ItemDto(DRESS, 1),
            new ItemDto(JACKET, 1)
        ), STANDARD_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("30") + Double.parseDouble("50") * 0.8 + Double.parseDouble("100") * 0.9));
    }

    @Test
    void it_should_calculate_price_for_empty_cart_for_premium_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(null, PREMIUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo("0");
    }

    @Test
    void it_should_calculate_price_for_1_tshirt_for_premium_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(TSHIRT, 1)
        ), PREMIUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("30") * 0.9));
    }

    @Test
    void it_should_calculate_price_for_1_dress_for_premium_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(DRESS, 1)
        ), PREMIUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("50") * 0.9 * 0.8));
    }

    @Test
    void it_should_calculate_price_for_1_jacket_for_premium_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(JACKET, 1)
        ), PREMIUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("100") * 0.9 * 0.9));
    }

    @Test
    void it_should_calculate_price_for_multiple_items_for_premium_customer_during_sales() {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(TSHIRT, 1),
            new ItemDto(DRESS, 1),
            new ItemDto(JACKET, 1)
        ), PREMIUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf((Double.parseDouble("30") + Double.parseDouble("50") * 0.8 + Double.parseDouble("100") * 0.9) * 0.9));
    }

    @Test
    void it_should_calculate_price_for_empty_cart_for_platinum_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(null, PLATINUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo("0");
    }

    @Test
    void it_should_calculate_price_for_1_tshirt_for_platinum_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(TSHIRT, 1)
        ), PLATINUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("30") * 0.5));
    }

    @Test
    void it_should_calculate_price_for_1_dress_for_platinum_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(DRESS, 1)
        ), PLATINUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("50") * 0.5 * 0.8));
    }

    @Test
    void it_should_calculate_price_for_1_jacket_for_platinum_customer_during_sales() throws ParseException {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(JACKET, 1)
        ), PLATINUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("100") * 0.5 * 0.9));
    }

    @Test
    void it_should_calculate_price_for_multiple_items_for_platinum_customer_during_sales() {
        String price = controller.getPrice(new BodyDto(List.of(
            new ItemDto(TSHIRT, 1),
            new ItemDto(DRESS, 1),
            new ItemDto(JACKET, 1)
        ), PLATINUM_CUSTOMER));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf((Double.parseDouble("30") + Double.parseDouble("50") * 0.8 + Double.parseDouble("100") * 0.9) * 0.5));
    }
}
