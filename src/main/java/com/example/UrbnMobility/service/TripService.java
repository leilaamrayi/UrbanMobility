package com.example.UrbnMobility.service;

import com.example.UrbnMobility.Database.Database;
import com.example.UrbnMobility.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {
    @Autowired
    private Database db;

    @Autowired
    private UserService userService;
    public List<Trip> searchTrips(String origin, String destination) {
        List<Trip> foundTrips = new ArrayList<>();

        for (Trip trip : db.getTrips()) {
            if (trip.getPlaceOfDeparture().equals(origin) && trip.getPlaceOfArrival().equals(destination)) {
                foundTrips.add(trip);
            }
        }
        return foundTrips;
    }

    public Trip findTripById(int id) {
        for (Trip trip : db.getTrips()) {
            if (trip.getId() == id) {
                return trip;
            }
        }
        return null; // Trip with the specified ID not found
    }

    /*public Trip updateTrip(User user,int tripId, Trip trip) {
        String message = "Trip updated";
        user = userService.findUserById(tripId);
        if (user != null) {
            if (user.getType().equals("supplier")) {
                Trip foundTrip = findTripById(tripId);
                foundTrip.setDiscount(trip.getDiscount());
                foundTrip.setArrivalTime(trip.getArrivalTime());
                foundTrip.setDepartureTime(trip.getDepartureTime());
                foundTrip.setPlaceOfDeparture(trip.getPlaceOfDeparture());
                foundTrip.setPlaceOfArrival(trip.getPlaceOfArrival());
                foundTrip.setPrice(trip.getPrice());
                foundTrip.setTransportationType(trip.getTransportationType());
                foundTrip.setSupplier(trip.getSupplier());
            }
        }
        return new UserResponse(trip, message);
    }*/
}









