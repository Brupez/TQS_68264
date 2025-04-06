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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository; 

    @Test
    void whenCreateBooking_thenBookingIsSaved() {
        Booking booking = new Booking();
        booking.setEmail("test@example.com");
        booking.setRestaurant("Castro");
        booking.setDayIndex(1);
        
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(booking);

        assertNotNull(createdBooking);
        assertEquals("test@example.com", createdBooking.getEmail());
        assertEquals("Castro", createdBooking.getRestaurant());
        assertEquals(BookingStatus.CONFIRMED, createdBooking.getStatus());
        assertNotNull(createdBooking.getCreatedAt());
        
        verify(bookingRepository, times(1)).save(booking);
    }
}