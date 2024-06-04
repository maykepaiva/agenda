package br.com.project.x.service;

import br.com.project.x.domain.dto.UserRequest;
import br.com.project.x.domain.dto.UserResponse;
import br.com.project.x.domain.entity.User;
import br.com.project.x.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    private User user;
    private final ModelMapper mapper = new ModelMapper();

    public UserResponse createUser(UserRequest userRequest) {
        if(repository.findByEmail(userRequest.getEmail())){
            throw new RuntimeException("Email já cadastrado");
        }
        User userEntity = repository.save(mapper.map(userRequest, User.class));
        return mapper.map(userEntity,UserResponse.class);
    }
}
