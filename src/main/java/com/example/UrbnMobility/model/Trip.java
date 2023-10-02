package com.example.UrbnMobility.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import java.time.LocalTime;
@Data
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    private int id;
    private String placeOfDeparture;
    private String placeOfArrival;
    private String transportationType;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime departureTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime arrivalTime;
    private double price;
    private double discount;
    private Supplier supplier; // Reference to the supplier of the trip

}
