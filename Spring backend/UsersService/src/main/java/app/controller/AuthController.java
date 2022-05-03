package app.controller;

import app.dto.LoginRequestDTO;
import app.dto.LoginResponseDTO;
import app.dto.RegistrationDTO;
import app.dto.ValidateTokenResponseDTO;
import app.models.User;
import app.repository.UserRepository;
import app.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository repository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("login")
    ResponseEntity<?> login(@RequestBody LoginRequestDTO requestData) {
        Optional<User> optional = repository.findByUsername(requestData.getUsername());
        if (optional.isPresent()) {
            User user = optional.get();
            if (passwordEncoder.matches(requestData.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getId());
                LoginResponseDTO dto = new LoginResponseDTO();
                dto.setToken(token);
                dto.setUserId(user.getId());
                dto.setName(user.getName());
                return new ResponseEntity<>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("register")
    ResponseEntity<?> register(@RequestBody RegistrationDTO requestData) {
        Optional<User> optional = repository.findByUsername(requestData.getUsername());
        if (optional.isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        };

        String password = passwordEncoder.encode(requestData.getPassword());

        repository.save(
                new User(requestData.getFirstName(),
                        requestData.getLastName(),
                        requestData.getUsername(),
                        password));
        return new ResponseEntity<>(HttpStatus.OK);
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
