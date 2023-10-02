package com.example.UrbnMobility.Controller;

import com.example.UrbnMobility.dto.UserResponse;
import com.example.UrbnMobility.model.User;
import com.example.UrbnMobility.Database.Database;
import com.example.UrbnMobility.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    Database db;
    @Autowired
    UserService userService;

    @PostMapping("")
    public ResponseEntity<User> CreateUser(@RequestBody User user) {
        userService.createUser(user);
        db.getUsers().add(user);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("(hasAuthority('ROLE_ADMIN'))")
    @GetMapping("{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") int userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") int userId, @RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully!.");
    }
}
