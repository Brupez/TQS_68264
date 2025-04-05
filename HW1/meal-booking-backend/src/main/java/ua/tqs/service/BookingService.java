package ua.tqs.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tqs.model.Booking;
import ua.tqs.model.BookingStatus;
import ua.tqs.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        booking.setCreatedAt(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingByEmail(String email) {
        return bookingRepository.findByEmail(email);
    }

    public Booking cancelBooking(Long id, String email) {
        List<Booking> booking = bookingRepository.findByIdAndEmail(id, email);
        if (booking.isEmpty()) {
            throw new RuntimeException("Booking not found");
        }
        Booking bookingCanceled = booking.get(0);
        bookingCanceled.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(bookingCanceled);
    }
    public Booking deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        bookingRepository.delete(booking);
        return booking;
    }
}
