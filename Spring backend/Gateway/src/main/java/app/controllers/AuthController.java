package app.controllers;

import app.dtos.LoginRequestDTO;
import app.dtos.LoginResponseDTO;
import app.dtos.RegistrationDTO;
import app.dtos.ValidateTokenResponseDTO;
import app.handlers.CustomRestTemplateError;
import app.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    @Qualifier("usersServiceRestTemplate")
    RestTemplate userServicesRestTemplate;


    @PostMapping("login")
    ResponseEntity<?> login(@RequestBody LoginRequestDTO requestData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<LoginRequestDTO> requestBody = new HttpEntity<>(requestData, headers);

        try {
            ResponseEntity<LoginResponseDTO> loginResponse = userServicesRestTemplate.postForEntity("/auth/login", requestBody, LoginResponseDTO.class);
            String token = jwtUtil.generateToken(loginResponse.getBody().getUserId());
            loginResponse.getBody().setToken(token);
            return new ResponseEntity<>(loginResponse.getBody(), HttpStatus.OK);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getStatusCode());
        }
    }

    @PostMapping("register")
    ResponseEntity<?> register(@RequestBody RegistrationDTO requestData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<RegistrationDTO> requestBody = new HttpEntity<>(requestData, headers);
        try {
            return userServicesRestTemplate.postForEntity("/auth/register", requestBody, String.class);
        } catch (CustomRestTemplateError error) {
            return new ResponseEntity<>(error.getMessage(), error.getStatusCode());
        }
    }

    @GetMapping("validate-token")
    ResponseEntity<?> validateToken(@RequestHeader String authorization) {
        if (authorization.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        String token = authorization.split(" ")[1];
        String userId = jwtUtil.validateTokenAndRetrieveSubject(token);
        ValidateTokenResponseDTO dto = new ValidateTokenResponseDTO();
        dto.setUserId(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
