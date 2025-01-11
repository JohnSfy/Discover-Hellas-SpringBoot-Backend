package com.OlympusRiviera.controller.Authentication;


import com.OlympusRiviera.model.AuthenticationResponse;
import com.OlympusRiviera.model.User.User;
import com.OlympusRiviera.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "https://olumpus-riviera-frontend.vercel.app")
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authService;


    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }
}
