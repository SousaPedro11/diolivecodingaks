package dio.livecoding.springboot.service;

import dio.livecoding.springboot.dto.UserDTO;
import dio.livecoding.springboot.model.UserModel;
import dio.livecoding.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<UserModel> getUsers() {
        return this.userRepository.findAll();
    }

    public UserModel getUser(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id {}", id);
            return new RuntimeException("User not found with id " + id);
        });
    }

    public UserModel create(@NotNull UserDTO userDTO) {
        UserModel userModel = UserModel.builder().name(userDTO.getName()).email(userDTO.getEmail()).build();
        return this.userRepository.save(userModel);
    }

    public UserModel update(Long id, UserDTO userDTO) {
        UserModel userModel = this.getUser(id);
        userModel.setEmail(userDTO.getEmail());
        userModel.setName(userDTO.getName());
        return this.userRepository.save(userModel);
    }

    public UserModel delete(Long id) {
        UserModel userModel = this.getUser(id);
        this.userRepository.delete(userModel);
        return userModel;
    }
}
