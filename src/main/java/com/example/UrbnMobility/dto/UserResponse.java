package com.example.UrbnMobility.dto;

import com.example.UrbnMobility.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private User user;
    private String message;
}
