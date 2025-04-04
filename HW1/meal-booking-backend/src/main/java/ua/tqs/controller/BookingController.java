package ua.tqs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.model.Booking;
import ua.tqs.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor
@Tag(name = "Booking")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new booking")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking created =  bookingService.createBooking(booking);
        HttpStatus status = created != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(created, status);
    }

    @GetMapping
    @Operation(summary = "Get all bookings of an email")
    public ResponseEntity<List<Booking>> getBookingsByEmail(
            @RequestParam String email) {
        List<Booking> bookings = bookingService.getBookingByEmail(email);
        HttpStatus status = bookings.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(bookings, status);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel a booking")
    public ResponseEntity<Booking> cancelBooking(
            @PathVariable Long id,
            @RequestParam String email) {
        bookingService.cancelBooking(id, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.deleteBooking(id));
    }
}
