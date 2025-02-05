package ru.osipovmaksim.xlslnmaxfinder.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileProcessingResponseDto {
    private int nthMaxFromFile;
}
