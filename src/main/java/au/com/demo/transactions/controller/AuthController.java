package au.com.demo.transactions.controller;

import au.com.demo.transactions.exception.BusinessException;
import au.com.demo.transactions.model.UserDto;
import au.com.demo.transactions.model.request.LoginRequest;
import au.com.demo.transactions.model.response.ErrorResponse;
import au.com.demo.transactions.model.response.LoginResponse;
import au.com.demo.transactions.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @ResponseBody
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            String email = authentication.getName();
            UserDto user = new UserDto(email,
                    "",
                    authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
            String token = jwtUtil.createToken(user);
            LoginResponse loginResponse = new LoginResponse(email, token);

            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e) {
            log.error("Bad request error: {}", e.getMessage());
            throw BusinessException.badRequest(e.getMessage());
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid username or password");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
//        catch (Exception e) {
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//        }
    }
}
