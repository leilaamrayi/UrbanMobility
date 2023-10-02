package com.example.UrbnMobility.service;

import com.example.UrbnMobility.Database.Database;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.UrbnMobility.model.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private Database db;
    @Mock
    private List<User> userList;  // Mocked list
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void should_createAUser_Successfully() {
        // Mock data
        User user = User.builder()
                .username("leila")
                .id(1)
                .password("Leila123") // Assuming you have the plain password here
                .roles(Collections.singletonList("USER"))
                .type("customer")
                .build();

        // Mock behavior
        when(db.getUsers()).thenReturn(userList);
        when(passwordEncoder.encode(Mockito.any(CharSequence.class))).thenReturn("encodedPassword");

        // Call the method to be tested
        User createdUser = userService.createUser(user);

        // Verify the result
        assertNotNull(createdUser);
        assertEquals("leila", createdUser.getUsername());
        assertEquals("encodedPassword", createdUser.getPassword());

        // Verify that userList.add was called
        Mockito.verify(userList).add(any(User.class));
    }


    @Test
    void testCreateUser_UserAlreadyExists() {
        // Mock data
        User user = new User();
        user.setUsername("Leila");

        // Mock behavior
        when(db.getUsers()).thenReturn(Collections.singletonList(user));

        // Call the method to be tested and expect an exception
        assertThrows(RuntimeException.class, () -> {
            User newUser = new User();
            newUser.setUsername("Leila");
            newUser.setPassword("123");
            userService.createUser(newUser);
        });

        // Verify that db.getUsers() was not modified
        Mockito.verify(db, Mockito.never()).addUser(Mockito.any(User.class));
    }
}