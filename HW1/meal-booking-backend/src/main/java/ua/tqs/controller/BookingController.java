package ua.tqs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.model.Booking;
import ua.tqs.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not created"),
    })
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking created =  bookingService.createBooking(booking);
        HttpStatus status = created != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(created, status);
    }

    @GetMapping
    @Operation(summary = "Get all bookings of an email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No bookings found for the email"),
    })
    public ResponseEntity<List<Booking>> getBookingsByEmail(
            @RequestParam String email) {
        List<Booking> bookings = bookingService.getBookingByEmail(email);
        HttpStatus status = bookings.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(bookings, status);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "400", description = "Invalid booking ID or email"),
    })
    public ResponseEntity<Booking> cancelBooking(
            @PathVariable Long id,
            @RequestParam String email) {
        try {
            bookingService.cancelBooking(id, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
