package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.User;
import it.unisalento.pas.wastedisposalagencybe.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Questa classe gestisce le operazioni relative agli utenti.
 */
@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Crea un nuovo utente nel sistema.
     *
     * @param user L'oggetto User da creare
     * @return 0 in caso di successo, -1 in caso di errore
     */
    @Override
    public int createUser(User user) {
        try {
            userRepository.save(user);
            return 0;  // Restituisce successo
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Restituisce errore
        }
    }

    /**
     * Aggiorna le informazioni di un utente esistente nel sistema.
     *
     * @param user L'oggetto User con le informazioni aggiornate
     * @return 0 in caso di successo, -1 in caso di errore
     */
    @Override
    public int updateUser(User user) {
        try {
            userRepository.save(user);
            return 0;  // Restituisce successo
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Restituisce errore
        }
    }

    /**
     * Elimina un utente dal sistema in base al suo ID.
     *
     * @param ID L'ID dell'utente da eliminare
     * @return 1 in caso di successo (utente eliminato), -1 in caso di errore
     */
    @Override
    public int deleteUser(String ID) {
        try {
            userRepository.deleteById(ID);
            return 1;  // Restituisce successo
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Restituisce errore
        }
    }

    /**
     * Verifica se un utente con l'email specificata esiste nel sistema.
     *
     * @param email L'email dell'utente da verificare
     * @return 1 se l'utente esiste, 0 se non esiste, -1 in caso di errore
     */
    @Override
    public int existUser(String email) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(email);
            return existingUser.isPresent() ? 1 : 0;  // Restituisce 1 se l'utente esiste, 0 altrimenti
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Restituisce errore
        }
    }

    /**
     * Trova un utente in base al suo ID.
     *
     * @param ID L'ID dell'utente da trovare
     * @return L'oggetto User corrispondente all'ID o null in caso di errore o utente non trovato
     */
    @Override
    public User findByID(String ID) {
        try {
            return userRepository.findById(ID).orElse(null);  // Trova l'utente per ID da MongoDB
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Restituisce null in caso di errore
        }
    }
}
