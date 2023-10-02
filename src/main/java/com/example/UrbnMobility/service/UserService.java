package com.example.UrbnMobility.service;

import com.example.UrbnMobility.Database.Database;
import com.example.UrbnMobility.dto.UserResponse;
import com.example.UrbnMobility.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private Database db;
    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder bean
   public User createUser(User user) {
        // Check if the user already exists
        if (userExists(user.getUsername())) {
            throw new RuntimeException("User with this username already exists.");
        }
        // Encode the user's password before storing it
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Collections.singletonList("USER"));
        }
        db.getUsers().add(user);
        return user;
    }

      public User findUserById(int id) {
        return db.getUsers().stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    public User getUserByUsername(String username) {
        return db.getUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public UserResponse updateUser(int id, User user) {
       String message = "User updated";
        User foundUser = findUserById(id);
        if (foundUser != null) {
            if (foundUser.getAuthorities().contains(new SimpleGrantedAuthority(("ADMIN")))){
                // Allow role update only if the current user is an admin
                foundUser.setRoles(user.getRoles());
            } else {
                message = "User's fields are updated except role, to update the roles you need to be ADMIN";
            }
            foundUser.setUsername(user.getUsername());
            foundUser.setPassword(user.getPassword());
            foundUser.setAccountNumber(user.getAccountNumber());
            foundUser.setEmail(user.getEmail());
            foundUser.setPhone(user.getPhone());
            foundUser.setSwishNumber(user.getSwishNumber());
        }
        return new UserResponse(foundUser, message);
    }

    public void deleteUser(int id) {
        User user =findUserById(id);
        db.getUsers().remove(user);
    }

    // Check if a user already exists (e.g., by username)
    public boolean userExists(String username) {
        return db.getUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }

   @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = getUserByUsername(username);
            return new org.springframework.security.core.userdetails.User(
               user.getUsername(),
               user.getPassword(),
               mapRolesToAuthorities(user.getRoles()));
          }

    private List<GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

}
