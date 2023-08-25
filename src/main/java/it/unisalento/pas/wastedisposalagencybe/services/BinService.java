package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.Bin;
import it.unisalento.pas.wastedisposalagencybe.repositories.IBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BinService implements IBinService{

    private final IBinRepository binRepository;

    @Autowired
    public BinService(IBinRepository binRepository) {
        this.binRepository = binRepository;
    }

    @Override
    public int createBin(Bin bin) {
        try {
            bin.setId(null);
            binRepository.save(bin);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteBinByID(String binID) {
        try{
            binRepository.deleteById(binID);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateBin(Bin bin) {
        try{
            binRepository.save(bin);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Bin getBinbyID(String binID) {
        try{
            return binRepository.findById(binID).orElse(null);  // Find user by ID from MongoDB
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public ArrayList<Bin> getAllBins() {
        try {
            return new ArrayList<>(binRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
