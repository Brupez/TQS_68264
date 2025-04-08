package ua.tqs.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.tqs.model.Booking;
import ua.tqs.model.BookingStatus;
import ua.tqs.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @CacheEvict(value = "bookings", allEntries = true)
    public Booking createBooking(Booking booking) {
        booking.setCreatedAt(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    @Cacheable(value = "bookings", key = "#email")
    public List<Booking> getBookingByEmail(String email) {
        return bookingRepository.findByEmail(email);
    }

    @CacheEvict(value = "bookings", key = "#email")
    public void cancelBooking(Long id, String email) {
        List<Booking> booking = bookingRepository.findByIdAndEmail(id, email);
        if (booking.isEmpty()) {
            throw new RuntimeException("Booking not found");
        }
        Booking bookingCancelled = booking.get(0);
        bookingCancelled.setStatus(BookingStatus.CANCELLED);
        bookingRepository.delete(bookingCancelled);
    }
}
