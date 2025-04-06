package controllers;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ua.tqs.MealReservationApplication;
import ua.tqs.controller.BookingController;
import ua.tqs.model.Booking;
import ua.tqs.model.BookingStatus;
import ua.tqs.service.BookingService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(BookingController.class)
@ContextConfiguration(classes = MealReservationApplication.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    void whenPostBooking_thenReturnBooking() {

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setDayIndex(1);
        booking.setEmail("email@email.com");
        booking.setRestaurant("Castro");
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setWeek("13");

        when(bookingService.createBooking(any(Booking.class)))
                .thenReturn(booking);

        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(booking)
                .when()
                .post("/api/bookings")
                .then()
                .statusCode(201)
                .body("id", is(1))
                .body("dayIndex", is(1))
                .body("email", is("email@email.com"))
                .body("restaurant", is("Castro"))
                .body("status", is("CONFIRMED"))
                .body("week", is("13"));

        verify(bookingService, times(1)).createBooking(Mockito.any());
    }

    @Test
    void whenGetBookingByInvalidEmail_thenReturn404() {
        when(bookingService.getBookingByEmail("@invalidemail"))
                .thenReturn(List.of());

        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .when()
                .get("/api/bookings?email=@invalidemail")
                .then()
                .statusCode(404);

        verify(bookingService, times(1)).getBookingByEmail("@invalidemail");
    }

    @Test
    void whenGetBookingByEmail_thenReturnBookings() {
        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCreatedAt(LocalDateTime.now());
        booking1.setDayIndex(1);
        booking1.setEmail("test@example.com");
        booking1.setRestaurant("Castro");
        booking1.setStatus(BookingStatus.CONFIRMED);
        booking1.setWeek("13");

        Booking booking2 = new Booking();
        booking2.setId(2L);
        booking2.setCreatedAt(LocalDateTime.now());
        booking2.setDayIndex(2);
        booking2.setEmail("test@example.com");
        booking2.setRestaurant("Santiago");
        booking2.setStatus(BookingStatus.CONFIRMED);
        booking2.setWeek("13");

        when(bookingService.getBookingByEmail("test@example.com"))
                .thenReturn(Arrays.asList(booking1, booking2));

        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .when()
                .get("/api/bookings?email=test@example.com")
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("[0].id", is(1))
                .body("[0].dayIndex", is(1))
                .body("[0].email", is("test@example.com"))
                .body("[0].restaurant", is("Castro"))
                .body("[0].status", is("CONFIRMED"))
                .body("[0].week", is("13"))
                .body("[1].id", is(2))
                .body("[1].dayIndex", is(2))
                .body("[1].email", is("test@example.com"))
                .body("[1].restaurant", is("Santiago"))
                .body("[1].status", is("CONFIRMED"))
                .body("[1].week", is("13"));

        verify(bookingService, times(1)).getBookingByEmail("test@example.com");
    }

    @Test
    void whenBookingCancelled_thenReturn200() {
        String bookingId = "1";
        String email = "email@email.com";

        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .param("email", email)
                .when()
                .put("/api/bookings/{id}/cancel", bookingId)
                .then()
                .statusCode(200);

        verify(bookingService, times(1)).cancelBooking(1L, email);
    }
}
