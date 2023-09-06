package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.Bin;
import it.unisalento.pas.wastedisposalagencybe.repositories.IBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Questa classe gestisce le operazioni relative ai cestini.
 */
@Service
public class BinService implements IBinService {

    private final IBinRepository binRepository;

    @Autowired
    public BinService(IBinRepository binRepository) {
        this.binRepository = binRepository;
    }

    /**
     * Crea un nuovo cestino.
     *
     * @param bin Il cestino da creare
     * @return 0 in caso di successo, -1 in caso di errore
     */
    @Override
    public int createBin(Bin bin) {
        try {
            bin.setId(null); // Imposta l'ID a null per garantire la creazione di un nuovo cestino
            binRepository.save(bin);
            return 0; // Successo
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Errore
        }
    }

    /**
     * Elimina un cestino tramite il suo ID.
     *
     * @param binID L'ID del cestino da eliminare
     * @return 0 in caso di successo, -1 in caso di errore
     */
    @Override
    public int deleteBinByID(String binID) {
        try {
            binRepository.deleteById(binID);
            return 0; // Successo
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Errore
        }
    }

    /**
     * Aggiorna un cestino esistente.
     *
     * @param bin Il cestino da aggiornare
     * @return 0 in caso di successo, -1 in caso di errore
     */
    @Override
    public int updateBin(Bin bin) {
        try {
            binRepository.save(bin);
            return 0; // Successo
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Errore
        }
    }

    /**
     * Restituisce un cestino dato il suo ID.
     *
     * @param binID L'ID del cestino da recuperare
     * @return Il cestino corrispondente all'ID o null se non trovato
     */
    @Override
    public Bin getBinbyID(String binID) {
        try {
            return binRepository.findById(binID).orElse(null); // Trova il cestino per ID da MongoDB
        } catch (Exception e) {
            return null; // Restituisce null in caso di errore
        }
    }

    /**
     * Restituisce una lista di tutti i cestini.
     *
     * @return Una lista di tutti i cestini o null in caso di errore
     */
    @Override
    public ArrayList<Bin> getAllBins() {
        try {
            return new ArrayList<>(binRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Errore
        }
    }
}
