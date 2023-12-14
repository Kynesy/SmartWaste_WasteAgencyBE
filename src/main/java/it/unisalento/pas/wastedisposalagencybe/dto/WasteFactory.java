package it.unisalento.pas.wastedisposalagencybe.dto;

public class WasteFactory implements IWasteFactory{

    @Override
    public Waste getWasteType(WasteType wasteType) {
        switch (wasteType){
            case SORTED_UNSORTED -> {
                return new TrashDTO();
            }
            default -> {
                return null;
            }
        }
    }
}
