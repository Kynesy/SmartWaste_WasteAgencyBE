package it.unisalento.pas.wastedisposalagencybe.dto;

public class ContainerFactory implements IContainerFactory{
    @Override
    public Container getContainerType(WasteType wasteType) {
        switch (wasteType){
            case SORTED_UNSORTED -> {
                return new BinDTO();
            }
            default -> {
                return null;
            }
        }
    }
}
