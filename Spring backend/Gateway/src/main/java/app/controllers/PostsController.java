package app.controllers;

import app.dtos.*;
import app.handlers.CustomRestTemplateError;
import app.security.JWTUtil;
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
@RequestMapping(path = "/posts")
public class PostsController {

    @Autowired
    @Qualifier("postServicesRestTemplate")
    RestTemplate postServicesRestTemplate;

    @Autowired
    @Qualifier("usersServiceRestTemplate")
    RestTemplate userServicesRestTemplate;

    @Autowired
    JWTUtil jwtUtil;

    @GetMapping("/user/{userId}")
    ResponseEntity<?> getPostsByUser(@RequestHeader Optional<String> authorization, @PathVariable String userId) {

        try {
            UserDataDTO userData = userServicesRestTemplate.getForEntity("/users/" + userId, UserDataDTO.class).getBody();
            try {
                List posts = postServicesRestTemplate.getForEntity("/posts/user/" + userId, List.class).getBody();
                MyProfileDTO myProfile = new MyProfileDTO();
                myProfile.setName(userData.getName());
                myProfile.setLastName(userData.getSurname());
                myProfile.setPrivateProfile(userData.isPrivateProfile());
                myProfile.setPosts(posts);
                return new ResponseEntity<>(myProfile, HttpStatus.OK);
            } catch (CustomRestTemplateError error) {
                return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
            }
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }

    @PostMapping("")
    ResponseEntity<?> saveNewPost(@RequestHeader Optional<String> authorization, @RequestBody SavePostDTO requestData) {
        if (!jwtUtil.validateHeader(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<SavePostDTO> requestBody = new HttpEntity<>(requestData, headers);
        try {
            return postServicesRestTemplate.postForEntity("/posts", requestBody, String.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }

    @PostMapping("/like")
    ResponseEntity<?> likePost(@RequestHeader Optional<String> authorization, @RequestBody PostReactionDTO requestData) {
        if (!jwtUtil.validateHeader(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<PostReactionDTO> requestBody = new HttpEntity<>(requestData, headers);
        try {
            return postServicesRestTemplate.postForEntity("/posts/like", requestBody, String.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }

    @PostMapping("/dislike")
    ResponseEntity<?> dislikePost(@RequestHeader Optional<String> authorization, @RequestBody PostReactionDTO requestData) {
        if (!jwtUtil.validateHeader(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<PostReactionDTO> requestBody = new HttpEntity<>(requestData, headers);
        try {
            return postServicesRestTemplate.postForEntity("/posts/dislike", requestBody, String.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }
}
