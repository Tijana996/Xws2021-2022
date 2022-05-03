package app.controller;

import app.dto.*;
import app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.repository.UserRepository;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserRepository repository;

    @GetMapping("/{userId}")
    ResponseEntity<?> getUserDataById(@PathVariable String userId) {
        Optional<User> optional = repository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            UserDataDTO userData = new UserDataDTO();
            userData.setName(user.getName());
            userData.setSurname(user.getLastName());
            userData.setAddress(user.getAddress());
            userData.setPhoneNumber(user.getPhoneNumber());
            userData.setBirthDate(user.getBirthDate());
            userData.setEducation(user.getEducation());
            userData.setEmail(user.getEmail());
            userData.setHoby(user.getHoby());
            userData.setWorkingExperience(user.getWorkingExperience());
            userData.setPrivateProfile(user.isPrivateProfile());
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}")
    ResponseEntity<?> updateUserDataById(@PathVariable String userId, @RequestBody UserDataDTO userData) {
        Optional<User> optional = repository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setName(userData.getName());
            user.setLastName(userData.getSurname());
            user.setAddress(userData.getAddress());
            user.setBirthDate(userData.getBirthDate());
            user.setEducation(userData.getEducation());
            user.setEmail(userData.getEmail());
            user.setHoby(userData.getHoby());
            user.setPhoneNumber(userData.getPhoneNumber());
            user.setWorkingExperience(userData.getWorkingExperience());
            user.setPrivateProfile(userData.isPrivateProfile());
            repository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad data sent", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find")
    ResponseEntity<?> findUsersByName(@RequestParam String name, @RequestParam Optional<String> ignoredId) {
        Optional<List<User>> optional = repository.findByNameStartsWithIgnoreCase(name);
        String idToIgnore = ignoredId.orElse("");
        if (optional.isPresent()) {
            List<User> users = optional.get();
            List<UserDataDTO> dtos = new ArrayList<>();
            users.forEach(user -> {
                UserDataDTO userData = new UserDataDTO();
                userData.setId(user.getId());
                userData.setName(user.getName());
                userData.setSurname(user.getLastName());
                userData.setAddress(user.getAddress());
                userData.setPhoneNumber(user.getPhoneNumber());
                userData.setBirthDate(user.getBirthDate());
                userData.setEducation(user.getEducation());
                userData.setEmail(user.getEmail());
                userData.setHoby(user.getHoby());
                userData.setWorkingExperience(user.getWorkingExperience());
                userData.setPrivateProfile(user.isPrivateProfile());
                if (!user.getId().equals(idToIgnore)) {
                    dtos.add(userData);
                }
            });

            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
    }

}
