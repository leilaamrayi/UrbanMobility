package com.example.UrbnMobility.service;

import com.example.UrbnMobility.Database.Database;
import com.example.UrbnMobility.model.Booking;
import com.example.UrbnMobility.model.Trip;
import com.example.UrbnMobility.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookingService {
    @Autowired
    TripService tripService;
    @Autowired
    Database db;
    @Autowired
    UserService userService;

    public void createBooking(String username, int tripId) throws Exception {
        // find trip by id, add it to user.bookings
        Trip trip = tripService.findTripById(tripId);
        if (trip != null) {
            User user = userService.getUserByUsername(username);
            if (user != null) {
                user.getBookings().add(Booking.builder().trip(trip).build());
            }
        } else {
            throw new Exception("Trip not found!");
        }
    }



    public void deleteBooking(String username, int bookingId) throws Exception {
        // find trip by id, add it to user.bookings

        User user = userService.getUserByUsername(username);
        if (user != null) {
            Booking foundBooking = user.getBookings().stream()
                    .filter(booking -> booking.getId() == bookingId)
                    .findFirst().orElse(null);
            if (foundBooking != null) {
                user.getBookings().remove(foundBooking);
            } else {
                throw new Exception("Booking not found!");
            }
        } else {
            throw new Exception("User not found!");
        }
    }

    public Booking findBookingById(String username, int bookingId) {
        User user = userService.getUserByUsername(username);
        Booking foundBooking = null;
        if (user != null) {
            foundBooking = user.getBookings().stream()
                    .filter(booking -> booking.getId() == bookingId)
                    .findFirst().orElse(null);

        }
        return foundBooking;
    }
}


