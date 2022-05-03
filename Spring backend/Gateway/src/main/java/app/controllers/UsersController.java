package app.controllers;

import app.dtos.UserDataDTO;
import app.handlers.CustomRestTemplateError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/users")
public class UsersController {

    @Autowired
    @Qualifier("usersServiceRestTemplate")
    RestTemplate userServicesRestTemplate;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDataById(@PathVariable String userId) {
        try {
            return userServicesRestTemplate.getForEntity("/users/" + userId, UserDataDTO.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }

    @PutMapping("/{userId}")
    ResponseEntity<?> updateUserDataById(@PathVariable String userId, @RequestBody UserDataDTO userData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<?> requestBody = new HttpEntity<>(userData, headers);
        try {
            userServicesRestTemplate.put("/users/" + userId, requestBody);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }

    @GetMapping("/find")
    ResponseEntity<?> findUsersByName(@RequestParam String name, @RequestParam Optional<String> ignoredId) {
        String url = "/users/find?name=" + name;
        if (!ignoredId.isEmpty()) {
            url += "&ignoredId=" + ignoredId.get();
        }

        try {
            return userServicesRestTemplate.getForEntity(url, List.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }

}
