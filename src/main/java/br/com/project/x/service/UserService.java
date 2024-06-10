package br.com.project.x.service;

import br.com.project.x.domain.dto.UserRequest;
import br.com.project.x.domain.dto.UserResponse;
import br.com.project.x.domain.entity.User;
import br.com.project.x.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {
        Optional<User> usuarioOptional = userRepository.findByEmail(userRequest.getEmail());
        if (usuarioOptional.isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User userEntity = userRepository.save(mapper.map(userRequest, User.class));
        return mapper.map(userEntity, UserResponse.class);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
