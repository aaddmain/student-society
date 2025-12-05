package com.gzy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ConvertUtils {
    public static Instant toInstant(String time) {
        // Handle null or empty time values
        if (time == null || time.isEmpty()) {
            return Instant.now(); // or return null based on your requirements
        }
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date.toInstant();
    }
}