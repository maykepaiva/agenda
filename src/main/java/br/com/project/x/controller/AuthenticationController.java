package br.com.project.x.controller;

import br.com.project.x.domain.dto.AuthRequest;
import br.com.project.x.domain.dto.TokenResponse;
import br.com.project.x.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authentication){
        var userToken = new UsernamePasswordAuthenticationToken(authentication.getEmail(), authentication.getPassword());
        authenticationManager.authenticate(userToken);
        return ResponseEntity.ok(authenticationService.obterToken(authentication));
    }
}
