package services;

import ua.tqs.service.BookingService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tqs.model.Booking;
import ua.tqs.model.BookingStatus;
import ua.tqs.repository.BookingRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository; 

    @Test
    void whenCreateBooking_thenBookingIsSaved() {
        Booking booking = new Booking();
        booking.setEmail("bruno@gmail.com");
        booking.setRestaurant("Castro");
        booking.setDayIndex(1);
        
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(booking);

        assertNotNull(createdBooking);
        assertEquals("bruno@gmail.com", createdBooking.getEmail());
        assertEquals("Castro", createdBooking.getRestaurant());
        assertEquals(BookingStatus.CONFIRMED, createdBooking.getStatus());
        assertNotNull(createdBooking.getCreatedAt());
        
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void whenCancelBooking_thenCancelled() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setEmail("bruno@gmail.com");
        booking.setStatus(BookingStatus.CONFIRMED);

        when(bookingRepository.findByIdAndEmail(1L, "bruno@gmail.com"))
                .thenReturn(List.of(booking));

        bookingService.cancelBooking(1L, "bruno@gmail.com");

        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
        verify(bookingRepository).delete(booking);
    }

    @Test
    void whenCancelNonExistentBooking_thenThrowException() {
        when(bookingRepository.findByIdAndEmail(1L, "bruno@gmail.com"))
                .thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () ->
                bookingService.cancelBooking(1L, "bruno@gmail.com"));
    }
}