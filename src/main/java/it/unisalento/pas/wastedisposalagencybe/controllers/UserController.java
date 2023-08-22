package it.unisalento.pas.wastedisposalagencybe.controllers;

import it.unisalento.pas.wastedisposalagencybe.domains.User;
import it.unisalento.pas.wastedisposalagencybe.dto.UserDTO;
import it.unisalento.pas.wastedisposalagencybe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/exist/{userID}")
    public ResponseEntity<Boolean> existUser(@PathVariable String userID) {
        if(userService.existUser(userID)==1){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        User user = fromUserDTOtoUser(userDTO);

        int result = userService.createUser(user);
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User created successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"User creation failed\"}");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        User user = fromUserDTOtoUser(userDTO);
        int result = userService.updateUser(user);
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User updated successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"User update failed\"}");
        }
    }

    @DeleteMapping("/delete/{userID}")
    public ResponseEntity<String> deleteUser(@PathVariable String userID) {
        int result = userService.deleteUser(userID);
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User deleted successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"User deletion failed\"}");
        }
    }

    @GetMapping("/get/{userID}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userID) {
        User user = userService.findByID(userID);

        if (user != null) {
            UserDTO userDTO = fromUserToUserDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // MAPPING
    private User fromUserDTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setBdate(userDTO.getBdate());

        return user;
    }

    private UserDTO fromUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(""); //non voglio l'id sia reperibile dal backend
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setBdate(user.getBdate());

        return userDTO;
    }
}
