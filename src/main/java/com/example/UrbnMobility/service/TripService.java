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
            if (trip.getPlaceOfDeparture().contains(origin) && trip.getPlaceOfArrival().contains(destination)) {
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
}










