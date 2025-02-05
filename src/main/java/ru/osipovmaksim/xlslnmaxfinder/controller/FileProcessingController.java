package ru.osipovmaksim.xlslnmaxfinder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.osipovmaksim.xlslnmaxfinder.dto.FileProcessingRequestDto;
import ru.osipovmaksim.xlslnmaxfinder.dto.FileProcessingResponseDto;
import ru.osipovmaksim.xlslnmaxfinder.service.FileProcessingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file-processing")
public class FileProcessingController {
    private final FileProcessingService fileProcessingService;

    @PostMapping("/nth-max")
    @Operation(summary = "Find the N-th maximum number in an XLSX file")
    public FileProcessingResponseDto findNthMaximum(
            @Parameter(description = "Path to the local XLSX file and N-th maximum to find in JSON")
            @RequestBody FileProcessingRequestDto fileProcessingRequestDto){
        return fileProcessingService.findNthMaximum(fileProcessingRequestDto);
    }


}
