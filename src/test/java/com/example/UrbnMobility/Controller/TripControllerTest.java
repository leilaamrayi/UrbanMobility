package com.example.UrbnMobility.Controller;

import com.example.UrbnMobility.model.Trip;
import com.example.UrbnMobility.service.TripService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TripController.class)
class TripControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @Test
    public void testGetListOfTripsWhenTripsFound() throws Exception {
        // Mock the behavior of the tripService when trips are found
        List<Trip> mockedTrips = new ArrayList<>();
        mockedTrips.add(new Trip(1, "toleredsgatan", "friskväderstorget", "bus", null, null, 10.0, 0.0, null));
        when(tripService.searchTrips("toleredsgatan", "friskväderstorget")).thenReturn(mockedTrips);

        // Perform the GET request
        mockMvc.perform(get("/trips")
                        .param("origin", "toleredsgatan")
                        .param("destination", "friskväderstorget")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void testGetListOfTripsWhenNoTripsFound() throws Exception {
        // Mock the behavior of the tripService when no trips are found
        when(tripService.searchTrips("nonexistent", "place")).thenReturn(new ArrayList<>());

        // Perform the GET request
        mockMvc.perform(get("/trips")
                        .param("origin", "nonexistent")
                        .param("destination", "place")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}