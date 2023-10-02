package com.example.UrbnMobility.service;

import com.example.UrbnMobility.Database.Database;
import com.example.UrbnMobility.model.Supplier;
import com.example.UrbnMobility.model.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {
    @Mock
    private Database db;
    @InjectMocks
    private TripService tripService;
    private Trip trip;
    private Trip trip1;
    private Trip trip2;
    private List<Trip> trips;

    @BeforeEach
    public void setup() {
        // Mock data
        trip = Trip.builder()
                .id(1)
                .transportationType("bus")
                .placeOfDeparture("toleredsgatan")
                .placeOfArrival("vågmasterplatsen")
                .departureTime(LocalTime.parse("09:00"))
                .arrivalTime(LocalTime.parse("09:40"))
                .discount(10)
                .price(100)
                .supplier(getSupplier("västtrafik"))
                .build();

        trip1 = Trip.builder()
                .id(2)
                .transportationType("spår")
                .placeOfDeparture("toleredsgatan")
                .placeOfArrival("vågmasterplatsen")
                .departureTime(LocalTime.parse("09:30"))
                .arrivalTime(LocalTime.parse("10:10"))
                .discount(0).price(90).supplier(getSupplier("SJ")).build();

        trip2 = Trip.builder()
                .id(4)
                .transportationType("bus")
                .placeOfDeparture("backaplan")
                .placeOfArrival("liseberg")
                .departureTime(LocalTime.parse("16:15"))
                .arrivalTime(LocalTime.parse("17:00"))
                .discount(0).price(90).supplier(getSupplier("mälartåg")).build();
        trips = List.of(trip, trip1,trip2);
    }

    @Test
    void should_GetSearchedTrips_FromDifferentSupplier() {

        // Mock behavior
        when(db.getTrips()).thenReturn(trips);

        // Call the method to be tested
        List<Trip> result = tripService.searchTrips("toleredsgatan", "vågmasterplatsen");

        // Verify the result
        assertNotNull(result);
        assertThat(result).containsExactly(trip, trip1);

    }


    @Test
    public void testFindTripById() {
        // Mock data
        int tripId = 4;
        Trip expectedTrip = trip2;
        trips = Collections.singletonList(expectedTrip);

        // Mock behavior
        when(db.getTrips()).thenReturn(trips);

        // Call the method to be tested
        Trip result = tripService.findTripById(tripId);

        // Verify the result
        assertNotNull(expectedTrip);
        assertThat(result).isEqualTo(expectedTrip);

    }

    public Supplier getSupplier(String name) {
        var opSupplier = dummySuppliers().stream().filter(supplier -> supplier.getName().equals(name)).findFirst();
        return opSupplier.orElse(null);
    }

    private List<Supplier> dummySuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(Supplier.builder().name("västtrafik").id(1).build());
        suppliers.add(Supplier.builder().name("SJ").id(2).build());
        suppliers.add(Supplier.builder().name("mälartåg").id(3).build());
        return suppliers;
    }
}