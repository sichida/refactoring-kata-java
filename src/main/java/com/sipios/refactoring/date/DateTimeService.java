package com.sipios.refactoring.date;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateTimeService {
    public Date now() {
        return new Date();
    }
}
