package au.com.demo.transactions.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import static au.com.demo.transactions.constants.Constants.DATE_TIME_INPUT_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DateTimeConverterTest {

    @Test
    public void dateTimeConvert_Success() {
        OffsetDateTime convertedDateTime = DateTimeConverter.convertToOffsetDateTime(
                "2025-03-20 09:45", DATE_TIME_INPUT_FORMAT);

        assertNotNull(convertedDateTime);
        assertEquals("2025-03-20T09:45+11:00", convertedDateTime.toString());
    }

    @Test
    public void dateTimeConvert_InvalidFormat() {
        DateTimeParseException dateTimeParseException =
                assertThrows(DateTimeParseException.class,
                        () -> DateTimeConverter.convertToOffsetDateTime(
                                "03-20-2025 09:45", DATE_TIME_INPUT_FORMAT));

        assertNotNull(dateTimeParseException.getMessage());
    }
}