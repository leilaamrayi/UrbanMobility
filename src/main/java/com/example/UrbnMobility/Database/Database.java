package com.example.UrbnMobility.Database;

import com.example.UrbnMobility.model.Booking;
import com.example.UrbnMobility.model.Supplier;
import com.example.UrbnMobility.model.Trip;
import com.example.UrbnMobility.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Database {

    private List<Supplier> suppliers = dummySuppliers();
    private List<Trip> trips = dummyTrips();
    private List<Booking> bookings = dummyBookings();
    ;
    private List<User> users = dummyUsers();
    ;


    public List<Trip> getTrips() {
        return trips;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public User getUserByUsername(String username) {
        return getUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public Supplier getSupplier(String name) {
        var opSupplier = suppliers.stream().filter(supplier -> supplier.getName().equals(name)).findFirst();
        return opSupplier.orElse(null);
    }

    private List<User> dummyUsers() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> users = new ArrayList<>();
        users.add((User.builder().username("leila").id(1).password(passwordEncoder.encode("Leila123"))
                .roles(Collections.singletonList("USER")).type("customer")
               // .authorities(Collections.singletonList(new SimpleGrantedAuthority("USER")))
                .bookings(new ArrayList<>())
                .build()));
        users.add((User.builder().username("bob").id(2).password(passwordEncoder.encode("bob123"))
                .roles(Collections.singletonList("ADMIN")))
                // .authorities(Collections.singletonList(new SimpleGrantedAuthority("USER")))
                .bookings(new ArrayList<>())
                .build());
        users.add((User.builder().username("ava").id(3).password(passwordEncoder.encode("ava123"))
                .roles(Collections.singletonList("USER")).type("supplier")
                // .authorities(Collections.singletonList(new SimpleGrantedAuthority("USER")))
                .bookings(new ArrayList<>())
                .build()));
        return users;
    }

    private List<Booking> dummyBookings() {
        List<Booking> bookings = new ArrayList<>();
        return bookings;
    }


    private List<Supplier> dummySuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(Supplier.builder().name("västtrafik").id(1).build());
        suppliers.add(Supplier.builder().name("SJ").id(2).build());
        suppliers.add(Supplier.builder().name("mälartåg").id(3).build());
        return suppliers;
    }

    private List<Trip> dummyTrips() {
        List<Trip> tripList = new ArrayList<>();
        Trip trip = Trip.builder()
                .id(1)
                .transportationType("bus")
                .placeOfDeparture("toleredsgatan")
                .placeOfArrival("vågmasterplatsen")
                .departureTime(LocalTime.parse("09:00"))
                .arrivalTime(LocalTime.parse("09:40"))
                .discount(10).price(100).supplier(getSupplier("västtrafik")).build();
        tripList.add(trip);

        Trip trip1 = Trip.builder()
                .id(2)
                .transportationType("spår")
                .placeOfDeparture("toleredsgatan")
                .placeOfArrival("vågmasterplatsen")
                .departureTime(LocalTime.parse("09:30"))
                .arrivalTime(LocalTime.parse("10:10"))
                .discount(0).price(90).supplier(getSupplier("SJ")).build();
        tripList.add(trip1);

        Trip trip2 = Trip.builder()
                .id(3)
                .transportationType("tåg")
                .placeOfDeparture("toleredsgatan")
                .placeOfArrival("vågmasterplatsen")
                .departureTime(LocalTime.parse("09:10"))
                .arrivalTime(LocalTime.parse("10:00"))
                .discount(5).price(90).supplier(getSupplier("mälartåg")).build();
        tripList.add(trip2);


        Trip trip3 = Trip.builder()
                .id(4)
                .transportationType("bus")
                .placeOfDeparture("backaplan")
                .placeOfArrival("liseberg")
                .departureTime(LocalTime.parse("16:15"))
                .arrivalTime(LocalTime.parse("17:00"))
                .discount(0).price(90).supplier(getSupplier("mälartåg")).build();
        tripList.add(trip3);

        Trip trip4 = Trip.builder()
                .id(5)
                .transportationType("tåg")
                .placeOfDeparture("backaplan")
                .placeOfArrival("liseberg")
                .departureTime(LocalTime.parse("16:12"))
                .arrivalTime(LocalTime.parse("16:34"))
                .discount(0).price(90).supplier(getSupplier("SJ")).build();

        tripList.add(trip4);

        Trip trip5 = Trip.builder()
                .transportationType("spår")
                .id(6)
                .placeOfDeparture("backaplan")
                .placeOfArrival("liseberg")
                .departureTime(LocalTime.parse("15:07"))
                .arrivalTime(LocalTime.parse("15:25"))
                .discount(0).price(90).supplier(getSupplier("västtrafik")).build();
        tripList.add(trip5);
        return tripList;
    }
    public void addUser(User user) {
        users.add(user);
    }
}

