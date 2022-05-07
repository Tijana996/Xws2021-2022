package app.controllers;

import app.dtos.CommentRequestDTO;
import app.handlers.CustomRestTemplateError;
import app.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    @Qualifier("postServicesRestTemplate")
    RestTemplate postServicesRestTemplate;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("")
    ResponseEntity<?> saveNewPost(@RequestHeader Optional<String> authorization, @RequestBody CommentRequestDTO requestData) {

        if (!jwtUtil.validateHeader(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<CommentRequestDTO> requestBody = new HttpEntity<>(requestData, headers);
        try {
            return postServicesRestTemplate.postForEntity("/comments", requestBody, String.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }
}
