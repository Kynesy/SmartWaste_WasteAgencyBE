package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.Bin;

import java.util.ArrayList;

public interface IBinService {
    int createBin(Bin bin);
    int deleteBinByID(String binID);
    int updateBin(Bin bin);
    Bin getBinbyID(String binID);
    ArrayList<Bin> getAllBins();
    int addWaste(String binID, int sortedWaste, int unsortedWaste);
    int unloadBin(String binID);

}
