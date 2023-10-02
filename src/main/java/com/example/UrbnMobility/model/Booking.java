package com.example.UrbnMobility.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private int id;
    private Trip trip;
}
