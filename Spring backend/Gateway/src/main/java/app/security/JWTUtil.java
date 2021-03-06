package app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Optional;

@Configuration
public class JWTUtil {
    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String userId) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("userId", userId)
                .withIssuedAt(new Date())
                .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("userId").asString();
    }

    public boolean validateHeader(Optional<String> header) {
        if (header.isPresent()) {
            try {
                String parsedHeader = header.get().split(" ")[1];
                String userId = this.validateTokenAndRetrieveSubject(parsedHeader);
                return userId != null;
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    public String getUserIdFromToken(Optional<String> header) {
        if (header.isPresent()) {
            try {
                String parsedHeader = header.get().split(" ")[1];
                return this.validateTokenAndRetrieveSubject(parsedHeader);
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }


}
