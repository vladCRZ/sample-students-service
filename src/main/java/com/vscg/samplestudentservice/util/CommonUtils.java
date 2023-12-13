package com.vscg.samplestudentservice.util;

import com.vscg.samplestudentservice.exception.InvalidDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static String convertDateToString(Date date) {
        if (date == null) {
            return "";
        }
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date convertStringToDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);

        try {
            return sdf.parse(date);

        } catch (ParseException e) {
            throw new InvalidDataException("dateOfBirth must be in yyyy-mm-dd format.");
        }
    }

}