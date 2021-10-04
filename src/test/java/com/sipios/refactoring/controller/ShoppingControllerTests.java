package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.date.DateTimeService;
import com.sipios.refactoring.dto.BodyDto;
import com.sipios.refactoring.dto.ItemDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;
    @Mock
    DateTimeService dateTimeService;

    @BeforeEach
    public void setUp() {
        Mockito.when(dateTimeService.now()).thenReturn(new Date());
    }

    @Test
    void it_should_support_standard_customer() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new BodyDto(new ItemDto[]{}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void it_should_support_premium_customer() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new BodyDto(new ItemDto[]{}, "PREMIUM_CUSTOMER"))
        );
    }

    @Test
    void it_should_support_platinum_customer() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new BodyDto(new ItemDto[]{}, "PLATINUM_CUSTOMER"))
        );
    }

    @Test
    void it_should_not_support_unknown_type_of_customer() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new BodyDto(new ItemDto[]{}, "CUSTOM_CUSTOMER"))
        );
    }

    @Test
    void it_should_calculate_price_for_empty_cart_for_standard_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(null, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo("0");
    }

    @Test
    void it_should_calculate_price_for_empty_cart_for_premium_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(null, "PREMIUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo("0");
    }

    @Test
    void it_should_calculate_price_for_empty_cart_for_platinum_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(null, "PLATINUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo("0");
    }

    @Test
    void it_should_calculate_price_for_1_tshirt_for_standard_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("TSHIRT", 1)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("30").toString());
    }

    @Test
    void it_should_calculate_price_for_1_dress_for_standard_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("DRESS", 1)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("50").toString());
    }

    @Test
    void it_should_calculate_price_for_1_jacket_for_standard_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("JACKET", 1)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("100").toString());
    }

    @Test
    void it_should_calculate_price_for_1_tshirt_for_premium_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("TSHIRT", 1)
        }, "PREMIUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("27").toString());
    }

    @Test
    void it_should_calculate_price_for_1_dress_for_premium_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("DRESS", 1)
        }, "PREMIUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("45").toString());
    }

    @Test
    void it_should_calculate_price_for_1_jacket_for_premium_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("JACKET", 1)
        }, "PREMIUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("90").toString());
    }

    @Test
    void it_should_calculate_price_for_1_tshirt_for_platinum_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("TSHIRT", 1)
        }, "PLATINUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("15").toString());
    }

    @Test
    void it_should_calculate_price_for_1_dress_for_platinum_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("DRESS", 1)
        }, "PLATINUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("25").toString());
    }

    @Test
    void it_should_calculate_price_for_1_jacket_for_platinum_customer_outside_sales() {
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("JACKET", 1)
        }, "PLATINUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(Double.valueOf("50").toString());
    }

    @Test
    void it_should_calculate_price_for_multiple_tshirt_for_standard_customer_outside_sales() {
        int quantity = 3;
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("TSHIRT", quantity)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("30") * quantity));
    }

    @Test
    void it_should_calculate_price_for_multiple_dress_for_standard_customer_outside_sales() {
        int quantity = 3;
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("DRESS", quantity)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("50") * quantity));
    }

    @Test
    void it_should_calculate_price_for_multiple_jacket_for_standard_customer_outside_sales() {
        int quantity = 2;
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("JACKET", quantity)
        }, "STANDARD_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("100") * quantity));
    }

    @Test
    void it_should_calculate_price_for_mulitple_tshirt_for_premium_customer_outside_sales() {
        int quantity = 3;
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("TSHIRT", quantity)
        }, "PREMIUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("30") * quantity * 0.9));
    }

    @Test
    void it_should_calculate_price_for_mulitple_dress_for_premium_customer_outside_sales() {
        int quantity = 3;
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("DRESS", quantity)
        }, "PREMIUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("50") * quantity * 0.9));
    }

    @Test
    void it_should_calculate_price_for_multiple_jacket_for_premium_customer_outside_sales() {
        int quantity = 3;
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("JACKET", quantity)
        }, "PREMIUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("100") * quantity * 0.9));
    }

    @Test
    void it_should_calculate_price_for_mulitple_tshirt_for_platinum_customer_outside_sales() {
        int quantity = 3;
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("TSHIRT", quantity)
        }, "PLATINUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("30") * quantity * 0.5));
    }

    @Test
    void it_should_calculate_price_for_multiple_dress_for_platinum_customer_outside_sales() {
        int quantity = 3;
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("DRESS", quantity)
        }, "PLATINUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("50") * quantity * 0.5));
    }

    @Test
    void it_should_calculate_price_for_multiple_jacket_for_platinum_customer_outside_sales() {
        int quantity = 3;
        String price = controller.getPrice(new BodyDto(new ItemDto[]{
            new ItemDto("JACKET", quantity)
        }, "PLATINUM_CUSTOMER"));
        assertThat(price).isNotBlank().isEqualTo(String.valueOf(Double.parseDouble("100") * quantity * 0.5));
    }

    @Test
    void it_should_prevent_standard_customer_to_buy_expensive_stuff() {
        int quantity = 3;
        Assertions.assertThrows(Exception.class,
            () -> controller.getPrice(new BodyDto(new ItemDto[]{
                new ItemDto("JACKET", quantity)
            }, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void it_should_prevent_premium_customer_to_buy_expensive_stuff() {
        int quantity = 9;
        Assertions.assertThrows(Exception.class,
            () -> controller.getPrice(new BodyDto(new ItemDto[]{
                new ItemDto("JACKET", quantity)
            }, "PREMIUM_CUSTOMER"))
        );
    }

    @Test
    void it_should_prevent_platinum_customer_to_buy_expensive_stuff() {
        int quantity = 41;
        Assertions.assertThrows(Exception.class,
            () -> controller.getPrice(new BodyDto(new ItemDto[]{
                new ItemDto("JACKET", quantity)
            }, "PLATINUM_CUSTOMER"))
        );
    }
}
