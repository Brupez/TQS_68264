package repositories;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ua.tqs.MealReservationApplication;
import ua.tqs.model.Booking;
import ua.tqs.model.BookingStatus;
import ua.tqs.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = MealReservationApplication.class)
class BookingRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(BookingRepositoryTest.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void findBookingByEmail_thenReturnBooking() {
        logger.debug("Starting test: find booking by email");

        Booking booking = new Booking();
        booking.setCreatedAt(LocalDateTime.now());
        booking.setDayIndex(2);
        booking.setEmail("bruno@gmail.com");
        booking.setRestaurant("Castro");
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setWeek("13");

        bookingRepository.save(booking);

        logger.debug("Searching for booking with email: {}", booking.getEmail());
        List<Booking> found = bookingRepository.findByEmail(booking.getEmail());

        logger.debug("Found {} booking(s) with email: {}", found.size(), booking.getEmail());
        assertThat(found)
                .hasSize(1)
                .first()
                .satisfies(foundBooking -> {
                    assertThat(foundBooking.getId()).isEqualTo(booking.getId());
                    assertThat(foundBooking.getEmail()).isEqualTo(booking.getEmail());
                    assertThat(foundBooking.getRestaurant()).isEqualTo(booking.getRestaurant());
                    assertThat(foundBooking.getWeek()).isEqualTo(booking.getWeek());
                    assertThat(foundBooking.getDayIndex()).isEqualTo(booking.getDayIndex());
                    assertThat(foundBooking.getStatus()).isEqualTo(booking.getStatus());
                    assertThat(foundBooking.getCreatedAt()).isEqualTo(booking.getCreatedAt());
                });
    }

    @Test
    void findBookingByIdandEmail_thenReturnBooking() {
        logger.info("Starting test: find booking by ID and email");

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCreatedAt(LocalDateTime.now());
        booking1.setDayIndex(1);
        booking1.setEmail("email1@example.com");
        booking1.setRestaurant("Castro");
        booking1.setStatus(BookingStatus.CONFIRMED);
        booking1.setWeek("13");

        Booking booking2 = new Booking();
        booking2.setId(3L);
        booking2.setCreatedAt(LocalDateTime.now());
        booking2.setDayIndex(3);
        booking2.setEmail("email2@example.com");
        booking2.setRestaurant("Santiago");
        booking2.setStatus(BookingStatus.CONFIRMED);
        booking2.setWeek("13");

        Booking booking3 = new Booking();
        booking3.setId(4L);
        booking3.setCreatedAt(LocalDateTime.now());
        booking3.setDayIndex(3);
        booking3.setEmail("email3@example.com");
        booking3.setRestaurant("Santiago");
        booking3.setStatus(BookingStatus.CONFIRMED);
        booking3.setWeek("13");

        List<Booking> bookings = bookingRepository.saveAll(List.of(booking1, booking2, booking3));

        for (Booking booking : bookings) {
            logger.info("Searching for booking with ID: {} and email: {}", booking.getId(), booking.getEmail());
            List<Booking> found = bookingRepository.findByIdAndEmail(booking.getId(), booking.getEmail());
            assertThat(found)
                    .hasSize(1)
                    .contains(booking);
        }
    }

    @Test
    void whenInvalidBookingEmail_thenReturnNull() {
        logger.info("Starting test: find booking by invalid email");
        Booking booking = bookingRepository.findById(-111L).orElse(null);
        logger.info("Search result for invalid ID: {}", booking == null ? "null" : "found");
        assertThat(booking).isNull();
    }
}
