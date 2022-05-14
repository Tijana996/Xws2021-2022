package app.controller;

import app.dto.LoginRequestDTO;
import app.dto.LoginResponseDTO;
import app.dto.RegistrationDTO;
import app.models.User;
import app.repository.UserRepository;
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
    private PasswordEncoder passwordEncoder;

    @PostMapping("login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestData) {
        LoginResponseDTO dto = new LoginResponseDTO();
        Optional<User> optional = repository.findByUsername(requestData.getUsername());
        if (optional.isPresent()) {
            User user = optional.get();
            if (passwordEncoder.matches(requestData.getPassword(), user.getPassword())) {
                dto.setUserId(user.getId());
                dto.setName(user.getName());
                dto.setLastName(user.getLastName());
                return new ResponseEntity<>(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
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
}
