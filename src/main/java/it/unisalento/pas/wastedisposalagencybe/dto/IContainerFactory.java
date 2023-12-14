package it.unisalento.pas.wastedisposalagencybe.dto;

public interface IContainerFactory {
    Container getContainerType(WasteType wasteType);
}
