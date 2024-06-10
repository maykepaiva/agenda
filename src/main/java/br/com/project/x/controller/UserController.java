package br.com.project.x.controller;

import br.com.project.x.domain.dto.RecoverUserRequest;
import br.com.project.x.domain.dto.UserRequest;
import br.com.project.x.domain.dto.UserResponse;
import br.com.project.x.domain.entity.User;
import br.com.project.x.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = {"User"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }
    @PutMapping
    public ResponseEntity<String> recoverUser(@RequestBody RecoverUserRequest recoverUserRequest) {
        return ResponseEntity.status(OK).body(userService.recoverUser(recoverUserRequest));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody RecoverUserRequest recoverUserRequest) {
        return ResponseEntity.status(OK).body(userService.deleteUser(recoverUserRequest));
    }
}