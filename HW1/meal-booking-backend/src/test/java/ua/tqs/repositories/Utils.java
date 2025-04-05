package ua.tqs.repositories;

import ua.tqs.model.Booking;
import ua.tqs.model.BookingStatus;

import java.time.LocalDateTime;

public class Utils {

    public static Booking createBooking(Long id, int dayIndex, String restaurant) {
        Booking booking = new Booking();
        booking.setId(id);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setDayIndex(dayIndex);
        booking.setEmail("bruno@email.com");
        booking.setRestaurant(restaurant);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setWeek("13");
        return booking;
    }
}
