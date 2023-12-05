package it.unisalento.pas.wastedisposalagencybe.controllers;

import it.unisalento.pas.wastedisposalagencybe.domains.User;
import it.unisalento.pas.wastedisposalagencybe.dto.UserDTO;
import it.unisalento.pas.wastedisposalagencybe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller che gestisce le operazioni relative agli utenti.
 */
@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('OPERATOR')")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Verifica l'esistenza di un utente con l'ID specificato.
     *
     * @param userID ID dell'utente da verificare
     * @return ResponseEntity con stato HTTP OK e corpo true se l'utente esiste, altrimenti NOT_FOUND e false
     */
    @GetMapping("/exist/{userID}")
    public ResponseEntity<Boolean> existUser(@PathVariable String userID) {
        if (userService.existUser(userID) == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    /**
     * Crea un nuovo utente con i dati forniti.
     *
     * @param userDTO Dati dell'utente da creare
     * @return ResponseEntity con stato HTTP OK e un messaggio JSON se l'utente è stato creato con successo,
     * altrimenti INTERNAL_SERVER_ERROR
     */
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        User user = fromUserDTOtoUser(userDTO);

        int result = userService.createUser(user);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User created successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"User creation failed\"}");
        }
    }

    /**
     * Aggiorna un utente esistente con i dati forniti.
     *
     * @param userDTO Dati dell'utente da aggiornare
     * @return ResponseEntity con stato HTTP OK e un messaggio JSON se l'utente è stato aggiornato con successo,
     * altrimenti INTERNAL_SERVER_ERROR
     */
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        User user = fromUserDTOtoUser(userDTO);
        int result = userService.updateUser(user);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User updated successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"User update failed\"}");
        }
    }

    /**
     * Elimina un utente con l'ID specificato.
     *
     * @param userID ID dell'utente da eliminare
     * @return ResponseEntity con stato HTTP OK e un messaggio JSON se l'utente è stato eliminato con successo,
     * altrimenti INTERNAL_SERVER_ERROR
     */
    @DeleteMapping("/delete/{userID}")
    public ResponseEntity<String> deleteUser(@PathVariable String userID) {
        int result = userService.deleteUser(userID);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User deleted successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"User deletion failed\"}");
        }
    }

    /**
     * Ottiene un utente con l'ID specificato.
     *
     * @param userID ID dell'utente da ottenere
     * @return ResponseEntity con lo stato HTTP OK e il corpo JSON dell'utente se trovato,
     * altrimenti NOT_FOUND
     */
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

    /**
     * Converte un oggetto UserDTO in un oggetto User.
     *
     * @param userDTO Oggetto UserDTO da convertire
     * @return Oggetto User convertito
     */
    private User fromUserDTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setBdate(userDTO.getBdate());

        return user;
    }

    /**
     * Converte un oggetto User in un oggetto UserDTO.
     *
     * @param user Oggetto User da convertire
     * @return Oggetto UserDTO convertito
     */
    private UserDTO fromUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(""); // Non voglio che l'ID sia reperibile dal backend
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setBdate(user.getBdate());

        return userDTO;
    }
}
