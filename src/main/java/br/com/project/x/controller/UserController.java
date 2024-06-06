package br.com.project.x.controller;

import br.com.project.x.domain.dto.UserRequest;
import br.com.project.x.domain.dto.UserResponse;
import br.com.project.x.domain.entity.User;
import br.com.project.x.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping
    public List<User> findAllUser() {
         var response = userService.findAllUser();
        return response;
    }
}