package com.sipios.refactoring.controller;

import com.sipios.refactoring.date.DateTimeService;
import com.sipios.refactoring.dto.BodyDto;
import com.sipios.refactoring.dto.ItemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    public static final int TSHIRT_PRICE = 30;
    public static final int DRESS_PRICE = 50;
    public static final int JACKET_PRICE = 100;
    public static final int STANDARD_CUSTOMER_PRICE_RATE = 1;
    public static final double PREMIUM_CUSTOMER_PRICE_RATE = 0.9;
    public static final double PLATINUM_CUSTOMER_PRICE_RATE = 0.5;
    public static final double SALE_TSHIRT_DISCOUNT = 1.0;
    public static final double SALE_DRESS_DISCOUNT = 0.8;
    public static final double SALE_JACKET_DISCOUNT = 0.9;
    private final Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);
    private final DateTimeService dateTimeService;

    public ShoppingController(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @PostMapping
    public String getPrice(@RequestBody BodyDto body) {
        LOGGER.info("Receiving price request for {}", body);
        // Calculate if sales is on
        boolean isSales = dateTimeService.isSales();
        // Compute customerTypeDiscount for customer
        double customerTypeDiscount = calculateDiscount(body);

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        LOGGER.debug("Calculating price (sale activated: {})", isSales);
        if (body.getItems() == null) {
            return "0";
        }

        return validatePriceAndReturn(
            body.getType(),
            body.getItems().stream()
                .mapToDouble(item -> calculatePrice(customerTypeDiscount, isSales, item)).sum()
        );

    }

    private double calculatePrice(double customerTypeDiscount, boolean isSale, ItemDto item) {
        switch (item.getType()) {
            case TSHIRT:
                return TSHIRT_PRICE * item.getQuantity() * (isSale ? SALE_TSHIRT_DISCOUNT : 1.0) * customerTypeDiscount;
            case DRESS:
                return DRESS_PRICE * item.getQuantity() * (isSale ? SALE_DRESS_DISCOUNT : 1.0) * customerTypeDiscount;
            case JACKET:
                return JACKET_PRICE * item.getQuantity() * (isSale ? SALE_JACKET_DISCOUNT : 1.0) * customerTypeDiscount;
            default:
                return 0.0;
        }
    }

    private String validatePriceAndReturn(String type, double price) {
        switch (type) {
            case "PREMIUM_CUSTOMER":
                if (price > 800) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price (" + price + ") is too high for premium customer");
                }
                break;
            case "PLATINUM_CUSTOMER":
                if (price > 2000) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price (" + price + ") is too high for platinum customer");
                }
                break;
            default:
                if (price > 200) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price (" + price + ") is too high for standard customer");
                }
                break;
        }
        LOGGER.debug("Price is {}", price);
        return String.valueOf(price);
    }

    private double calculateDiscount(BodyDto body) {
        double discount;
        switch (body.getType()) {
            case "STANDARD_CUSTOMER":
                discount = STANDARD_CUSTOMER_PRICE_RATE;
                break;
            case "PREMIUM_CUSTOMER":
                discount = PREMIUM_CUSTOMER_PRICE_RATE;
                break;
            case "PLATINUM_CUSTOMER":
                discount = PLATINUM_CUSTOMER_PRICE_RATE;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return discount;
    }
}
