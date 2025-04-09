package models;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import ua.tqs.model.Booking;
import ua.tqs.model.BookingStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Booking.class)
class BookingTest {
    private static final Logger logger = LoggerFactory.getLogger(BookingTest.class);

    @Test
    void whenCreateBookingWithAllArgs_thenAllFieldsAreSet() {
        logger.debug("Testing Booking all-args constructor");

        LocalDateTime now = LocalDateTime.now();
        Booking booking = new Booking(
                1L,
                "rafael@email.com",
                "Santiago",
                "13",
                2,
                BookingStatus.CONFIRMED,
                now
        );

        logger.debug("Verifying: {}", booking);
        assertThat(booking)
                .extracting(
                        Booking::getId,
                        Booking::getEmail,
                        Booking::getRestaurant,
                        Booking::getWeek,
                        Booking::getDayIndex,
                        Booking::getStatus,
                        Booking::getCreatedAt
                )
                .containsExactly(
                        1L,
                        "rafael@email.com",
                        "Santiago",
                        "13",
                        2,
                        BookingStatus.CONFIRMED,
                        now
                );
    }
}
