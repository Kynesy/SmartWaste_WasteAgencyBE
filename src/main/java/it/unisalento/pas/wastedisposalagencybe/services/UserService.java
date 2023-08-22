package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.User;
import it.unisalento.pas.wastedisposalagencybe.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int createUser(User user) {
        try {
            userRepository.save(user);
            return 0;  // Return success
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return failure
        }
    }

    @Override
    public int updateUser(User user) {
        try {
            userRepository.save(user);
            return 0;  // Return success
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return failure
        }
    }

    @Override
    public int deleteUser(String ID) {
        try {
            userRepository.deleteById(ID);
            return 1;  // Return success
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return failure
        }
    }

    @Override
    public int existUser(String email) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(email);
            return existingUser.isPresent() ? 1 : 0;  // Return 1 if the user exists, 0 otherwise
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return failure
        }
    }

    @Override
    public User findByID(String ID) {
        try {
            return userRepository.findById(ID).orElse(null);  // Find user by ID from MongoDB
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null in case of failure
        }
    }
}
