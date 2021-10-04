package com.sipios.refactoring.date;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DateTimeServiceTest {

    @Test
    void it_should_calculate_january_2nd_is_not_sales_period() throws ParseException {
        // Given
        DateTimeService dateTimeService = mock(DateTimeService.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

        Date date = sdf.parse("2021-01-02 00:00:00");

        given(dateTimeService.now()).willReturn(date);
        given(dateTimeService.isSales()).willCallRealMethod();
        // When
        Boolean isSales = dateTimeService.isSales();
        // Then
        assertThat(isSales).isFalse();
    }

    @Test
    void it_should_calculate_january_14_is_sales_period() throws ParseException {
        // Given
        DateTimeService dateTimeService = mock(DateTimeService.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

        Date date = sdf.parse("2021-01-14 00:00:00");

        given(dateTimeService.now()).willReturn(date);
        given(dateTimeService.isSales()).willCallRealMethod();
        // When
        Boolean isSales = dateTimeService.isSales();
        // Then
        assertThat(isSales).isTrue();
    }

    @Test
    void it_should_calculate_june_2nd_is_not_sales_period() throws ParseException {
        // Given
        DateTimeService dateTimeService = mock(DateTimeService.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

        Date date = sdf.parse("2021-06-02 00:00:00");

        given(dateTimeService.now()).willReturn(date);
        given(dateTimeService.isSales()).willCallRealMethod();
        // When
        Boolean isSales = dateTimeService.isSales();
        // Then
        assertThat(isSales).isFalse();
    }

    @Test
    void it_should_calculate_june_14_is_sales_period() throws ParseException {
        // Given
        DateTimeService dateTimeService = mock(DateTimeService.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

        Date date = sdf.parse("2021-06-14 00:00:00");

        given(dateTimeService.now()).willReturn(date);
        given(dateTimeService.isSales()).willCallRealMethod();
        // When
        Boolean isSales = dateTimeService.isSales();
        // Then
        assertThat(isSales).isTrue();
    }
}
