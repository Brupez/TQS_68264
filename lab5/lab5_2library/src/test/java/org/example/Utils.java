package org.example;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    public static LocalDate localDateFromDateParts(String year, String month, String day) {
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public static LocalDate parsePublish(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter textFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        try {
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            return LocalDate.parse(date, textFormatter);
        }
    }

    public static Date isoTextToLocalDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
