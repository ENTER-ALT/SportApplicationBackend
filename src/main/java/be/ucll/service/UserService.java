package be.ucll.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.ucll.model.User;
import be.ucll.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User addUser(User userData) {
        User existingUser = userRepository.getUserByEmail(userData.getEmail());
        if (existingUser != null) {
            throw new ServiceException("User already exists.");
        }
        userRepository.addUser(userData);
        return userData;
    }
}
