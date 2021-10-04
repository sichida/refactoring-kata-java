package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.date.DateTimeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingControllerSalesTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;
    @Mock
    DateTimeService dateTimeService;

    private void configureSaleInJanuary() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

        Date date = sdf.parse("2021-01-14 00:00:00");

        Mockito.when(dateTimeService.now()).thenReturn(date);
    }

    private void configureSaleInJune() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

        Date date = sdf.parse("2021-06-14 00:00:00");

        Mockito.when(dateTimeService.now()).thenReturn(date);
    }

    @Test
    void it_should_calculate_price_for_empty_cart_for_standard_customer_during_winter_sales() throws ParseException {
        configureSaleInJanuary();
        String price = controller.getPrice(new Body(null, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo("0");
    }

    @Test
    void it_should_calculate_price_for_empty_cart_for_standard_customer_during_spring_sales() throws ParseException {
        configureSaleInJune();
        String price = controller.getPrice(new Body(null, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo("0");
    }

    @Test
    void it_should_calculate_price_for_1_tshirt_for_standard_customer_during_winter_sales() throws ParseException {
        configureSaleInJanuary();
        String price = controller.getPrice(new Body(new Item[]{
            new Item("TSHIRT", 1)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("30").toString());
    }

    @Test
    void it_should_calculate_price_for_1_dress_for_standard_customer_during_winter_sales() throws ParseException {
        configureSaleInJanuary();
        String price = controller.getPrice(new Body(new Item[]{
            new Item("DRESS", 1)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("50") * 0.8));
    }

    @Test
    void it_should_calculate_price_for_1_jacket_for_standard_customer_during_winter_sales() throws ParseException {
        configureSaleInJanuary();
        String price = controller.getPrice(new Body(new Item[]{
            new Item("JACKET", 1)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("100") * 0.9));
    }
    @Test
    void it_should_calculate_price_for_1_tshirt_for_standard_customer_during_spring_sales() throws ParseException {
        configureSaleInJune();
        String price = controller.getPrice(new Body(new Item[]{
            new Item("TSHIRT", 1)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("30").toString());
    }

    @Test
    void it_should_calculate_price_for_1_dress_for_standard_customer_during_spring_sales() throws ParseException {
        configureSaleInJune();
        String price = controller.getPrice(new Body(new Item[]{
            new Item("DRESS", 1)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("50") * 0.8));
    }

    @Test
    void it_should_calculate_price_for_1_jacket_for_standard_customer_during_spring_sales() throws ParseException {
        configureSaleInJune();
        String price = controller.getPrice(new Body(new Item[]{
            new Item("JACKET", 1)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("100") * 0.9));
    }
}
