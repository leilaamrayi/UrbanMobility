package com.example.UrbnMobility.Controller;

import com.example.UrbnMobility.model.Trip;
import com.example.UrbnMobility.service.BookingService;
import com.example.UrbnMobility.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
    @Autowired
    private TripService tripService;

    @Autowired
    private BookingService bookingService;

    //localhost:8081/trips?origin=&destination=vågmasterplatsen
    @GetMapping("")
    public ResponseEntity<List<Trip>> getListOfTripsByDifferentSuppliers(@RequestParam String origin,
                                               @RequestParam String destination) {
        List<Trip> trips = tripService.searchTrips(origin,destination);
        return ResponseEntity.ok(trips);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/book")
    //http://localhost:8081/trips/2/book

    public ResponseEntity<String> createBooking(@PathVariable("id") int tripId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Get the authenticated user's username
            String username = userDetails.getUsername();
            bookingService.createBooking(username, tripId);
            return ResponseEntity.ok().body("Trip booked.");
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Trip not found. Cannot book it.");
        }

    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}/delete")
    //http://localhost:8081/trips/2/book

    public ResponseEntity<String> deleteBooking(@PathVariable("id") int bookingId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Get the authenticated user's username
            String username = userDetails.getUsername();
            bookingService.deleteBooking(username, bookingId);
            return ResponseEntity.ok().body("booking deleted.");
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Booking not found. Cannot delete it.");
        }

    }

}

