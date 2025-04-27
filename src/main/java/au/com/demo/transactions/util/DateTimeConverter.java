package au.com.demo.transactions.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static au.com.demo.transactions.constants.Constants.LOCAL_TIME_ZONE;

@Slf4j
public class DateTimeConverter {

    public static OffsetDateTime convertToOffsetDateTime(String dateTimeString, String inputFormat) {

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(inputFormat);
            return LocalDateTime.parse(dateTimeString, dateTimeFormatter)
                    .atZone(ZoneId.of(LOCAL_TIME_ZONE))
                    .toOffsetDateTime();
        } catch (DateTimeParseException ex) {
            log.error("Error parsing or formatting date: {}", ex.getMessage());
            throw ex;
        }
    }
}

