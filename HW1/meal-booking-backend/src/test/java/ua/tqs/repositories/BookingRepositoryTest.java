package ua.tqs.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.tqs.model.Booking;
import ua.tqs.repository.BookingRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void findBookingByEmail_thenReturnBooking() {
        Booking booking = Utils.createBooking(2L, 2, "Castro");

        bookingRepository.save(booking);
        List<Booking> found = bookingRepository.findByEmail(booking.getEmail());
        assertThat(found)
                .hasSize(1)
                .extracting(Booking::getEmail)
                .contains(booking.getEmail()
                );
    }


    @Test
    void findBookingByIdandEmail_thenReturnBooking() {
        List<Booking> bookings = List.of(
                Utils.createBooking(1L, 1, "Castro"),
                Utils.createBooking(3L, 3, "Santiago"),
                Utils.createBooking(4L, 3, "Santiago")
        );

        bookingRepository.saveAll(bookings);

        for (Booking booking : bookings) {
            List<Booking> found = bookingRepository.findByIdAndEmail(booking.getId(), booking.getEmail());
            assertThat(found)
                    .hasSize(1)
                    .contains(booking);
        }
    }

    @Test
    void whenInvalidBookingEmail_thenReturnNull() {
        Booking booking = bookingRepository.findById(-111L).orElse(null);
        assertThat(booking).isNull();
    }
}
