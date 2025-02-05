package ru.osipovmaksim.xlslnmaxfinder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileProcessingRequestDto {
    private String filePath;
    private int n;
}
