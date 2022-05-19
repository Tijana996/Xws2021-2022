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
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/following")
public class FollowingController {

    @Autowired
    @Qualifier("followingServicesRestTemplate")
    RestTemplate followingServicesRestTemplate;

    @Autowired
    @Qualifier("usersServiceRestTemplate")
    RestTemplate userServicesRestTemplate;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping
    ResponseEntity<?> follow(@RequestHeader Optional<String> authorization, @RequestBody() FollowingRequestDTO requestData) {
        if (!jwtUtil.validateHeader(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            UserDataDTO userData = userServicesRestTemplate.getForEntity("/users/" + requestData.getUserToFollowId(), UserDataDTO.class).getBody();
            FollowingRequestExtendedDTO extendedRequestBody = new FollowingRequestExtendedDTO(requestData.getUserToFollowId(),
                    requestData.getCurrentUserId(), requestData.getCurrentUserName(), requestData.getCurrentUserLastName(), userData.isPrivateProfile());

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<FollowingRequestExtendedDTO> requestBody = new HttpEntity<>(extendedRequestBody);

            return followingServicesRestTemplate.postForEntity("/following", requestBody, FollowingResponseDTO.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }

    @PostMapping("/request/accept")
    public ResponseEntity acceptRequest(@RequestHeader Optional<String> authorization, @RequestBody FollowRequestDTO requestBody) {
        if (!jwtUtil.validateHeader(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return followingServicesRestTemplate.postForEntity("/following/request/accept", requestBody, String.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }

    @PostMapping("/request/decline")
    public ResponseEntity declineRequest(@RequestHeader Optional<String> authorization, @RequestBody FollowRequestDTO requestBody) {
        if (!jwtUtil.validateHeader(authorization)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return followingServicesRestTemplate.postForEntity("/following/request/decline", requestBody, String.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }
}
