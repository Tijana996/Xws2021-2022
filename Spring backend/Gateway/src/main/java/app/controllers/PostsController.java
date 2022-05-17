package app.controllers;

import app.dtos.*;
import app.handlers.CustomRestTemplateError;
import app.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/posts")
public class PostsController {
    private final String IMAGES_FOLDER = "Gateway/src/main/resources/static/upload/";
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

    @PostMapping(path = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<?> saveNewPost(@RequestHeader Optional<String> authorization, @ModelAttribute() SavePostDTO requestData, @RequestParam(value ="file", required=false) MultipartFile file) {
        if (!jwtUtil.validateHeader(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String fileName = "";
        if (file != null) {
            Path parentDir = Paths.get(IMAGES_FOLDER);
            if (!Files.exists(parentDir))
                try {
                    Files.createDirectories(parentDir);
                } catch (IOException ex) {
                    return ResponseEntity.internalServerError().body("Server error");
                }


            fileName = System.currentTimeMillis() + file.getOriginalFilename();
            Path path = Paths.get(IMAGES_FOLDER + fileName);
            try {
                Files.write(path, file.getBytes());
            } catch (IOException exception) {
                exception.printStackTrace();
                return ResponseEntity.badRequest().body("Bad image sent");
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<SavePostStringImageDTO> requestBody = new HttpEntity<>(new SavePostStringImageDTO(requestData.getContent(),
                requestData.getUserId(), requestData.getUserName(), requestData.getUserLastName(), fileName, requestData.getLink()), headers);
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
