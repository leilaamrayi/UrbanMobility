package com.example.UrbnMobility.service;
import com.example.UrbnMobility.Database.Database;
import com.example.UrbnMobility.model.Booking;
import com.example.UrbnMobility.model.Supplier;
import com.example.UrbnMobility.model.Trip;
import com.example.UrbnMobility.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    private Database db;
    @Mock
    private TripService tripService;
    @Mock
    private UserService userService;
    @InjectMocks
    private BookingService bookingService;


    @Test
    void createBooking_should_Create_newBooking() throws Exception {
        // Mock data
        String username = "leila";
        int tripId = 1;

        Trip trip = new Trip();
        trip.setId(tripId);

        User mockUser = new User();
        mockUser.setUsername(username);

        // Mock behavior
        when(tripService.findTripById(tripId)).thenReturn(trip);
        when(userService.getUserByUsername(username)).thenReturn(mockUser);

        // Call the method to be tested
        bookingService.createBooking(username, tripId);

        // Verify that the booking was added to the user's bookings and to the database
        assertNotNull(mockUser.getBookings());
        assertEquals(1, mockUser.getBookings().size());
        assertEquals(trip, mockUser.getBookings().get(0).getTrip());

    }



    @Test
    void testDeleteBooking_Success() throws Exception {
        // Mock data
        String username = "leila";
        int bookingId = 1;

        Booking mockBooking = new Booking();
        mockBooking.setId(bookingId);

        List<Booking> mockBookings = new ArrayList<>();
        mockBookings.add(mockBooking);

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setBookings(mockBookings);

        // Mock behavior
        when(userService.getUserByUsername(username)).thenReturn(mockUser);

        // Call the method to be tested
        bookingService.deleteBooking(username, bookingId);

        // Verify that the booking was removed from user's bookings
        assertEquals(0, mockUser.getBookings().size());

    }


    @Test
    void testDeleteBooking_BookingNotFound() {
        // Mock data
        String username = "leila";
        int bookingId = 1;

        List<Booking> mockBookings = new ArrayList<>();

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setBookings(mockBookings);

        // Mock behavior
        when(userService.getUserByUsername(username)).thenReturn(mockUser);

        // Call the method to be tested and assert exception
        Exception exception = assertThrows(Exception.class, () -> {
            bookingService.deleteBooking(username, bookingId);
        });

        // Verify that the exception message is correct
        assert (exception.getMessage().contains("Booking not found!"));
    }

    @Test
    void testDeleteBooking_UserNotFound() {
        // Mock data
        String username = "leila";
        int bookingId = 1;


        // Mock behavior
        when(userService.getUserByUsername(username)).thenReturn(null);

        // Call the method to be tested and assert exception
        Exception exception = assertThrows(Exception.class, () -> {
            bookingService.deleteBooking(username, bookingId);
        });

        // Verify that the exception message is correct
        assert (exception.getMessage().contains("User not found!"));
    }
}
