package com.sipios.refactoring.controller;

import com.sipios.refactoring.date.DateTimeService;
import com.sipios.refactoring.dto.BodyDto;
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

    private final Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);
    private final DateTimeService dateTimeService;

    public ShoppingController(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @PostMapping
    public String getPrice(@RequestBody BodyDto body) {
        LOGGER.info("Receiving price request for {}", body);
        double price = 0;

        Date date = dateTimeService.now();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        // Compute discount for customer
        double discount = calculateDiscount(body);

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (IsSales(cal)) {
            LOGGER.debug("Calculating price during sales");
            if (body.getItems() == null) {
                return "0";
            }
            price = body.getItems().stream()
                .mapToDouble(item -> {
                    switch (item.getType()) {
                        case "TSHIRT":
                            return 30 * item.getQuantity() * discount;
                        case "DRESS":
                            return 50 * item.getQuantity() * discount;
                        case "JACKET":
                            return 100 * item.getQuantity() * discount;
                        default:
                            return 0.0;
                    }
                }).sum();
        } else {
            LOGGER.debug("Calculating price outside sales");
            if (body.getItems() == null) {
                return "0";
            }

            price = body.getItems().stream()
                .mapToDouble(item -> {
                    switch (item.getType()) {
                        case "TSHIRT":
                            return 30 * item.getQuantity() * discount;
                        case "DRESS":
                            return 50 * item.getQuantity() * 0.8 * discount;
                        case "JACKET":
                            return 100 * item.getQuantity() * 0.9 * discount;
                        default:
                            return 0.0;
                    }
                }).sum();
        }

        try {
            if (body.getType().equals("STANDARD_CUSTOMER")) {
                if (price > 200) {
                    throw new Exception("Price (" + price + ") is too high for standard customer");
                }
            } else if (body.getType().equals("PREMIUM_CUSTOMER")) {
                if (price > 800) {
                    throw new Exception("Price (" + price + ") is too high for premium customer");
                }
            } else if (body.getType().equals("PLATINUM_CUSTOMER")) {
                if (price > 2000) {
                    throw new Exception("Price (" + price + ") is too high for platinum customer");
                }
            } else {
                if (price > 200) {
                    throw new Exception("Price (" + price + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(price);
    }

    private double calculateDiscount(BodyDto body) {
        double discount;
        if (body.getType().equals("STANDARD_CUSTOMER")) {
            discount = 1;
        } else if (body.getType().equals("PREMIUM_CUSTOMER")) {
            discount = 0.9;
        } else if (body.getType().equals("PLATINUM_CUSTOMER")) {
            discount = 0.5;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return discount;
    }

    private boolean IsSales(Calendar cal) {
        return !(
            cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                cal.get(Calendar.MONTH) == 5
        ) &&
            !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                    cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                    cal.get(Calendar.MONTH) == 0
            );
    }
}
