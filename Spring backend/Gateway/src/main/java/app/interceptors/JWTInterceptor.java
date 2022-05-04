package app.interceptors;


import app.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        
        if (header == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        if (header.isEmpty()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        String token = header.split(" ")[1];
        String result = jwtUtil.validateTokenAndRetrieveSubject(token);
        if (result.isEmpty()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        return true;
    }
}
