package ru.osipovmaksim.xlslnmaxfinder.service;


import ru.osipovmaksim.xlslnmaxfinder.dto.FileProcessingRequestDto;
import ru.osipovmaksim.xlslnmaxfinder.dto.FileProcessingResponseDto;

public interface FileProcessingService {
    FileProcessingResponseDto findNthMaximum(FileProcessingRequestDto fileProcessingRequestDto);
}
