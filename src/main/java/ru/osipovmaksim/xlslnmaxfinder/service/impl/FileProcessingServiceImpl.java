package ru.osipovmaksim.xlslnmaxfinder.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.osipovmaksim.xlslnmaxfinder.dto.FileProcessingRequestDto;
import ru.osipovmaksim.xlslnmaxfinder.dto.FileProcessingResponseDto;
import ru.osipovmaksim.xlslnmaxfinder.exception.custom.InvalidInputDataException;
import ru.osipovmaksim.xlslnmaxfinder.service.FileProcessingService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileProcessingServiceImpl implements FileProcessingService {
    @Override
    public FileProcessingResponseDto findNthMaximum(FileProcessingRequestDto fileProcessingRequestDto) {
        String filePath = fileProcessingRequestDto.getFilePath();
        int n = fileProcessingRequestDto.getN();
        log.info("Данные успешно получены: filepath: {}, n:{}",filePath,n);

        if (n < 0) {
            log.warn("Не положительное n:" + n);
            throw new InvalidInputDataException("Значение N должно быть положительным числом");
        }

        File file = new File(filePath);
        if (!file.exists() || !file.canRead()) {
            log.warn("Проблемы с доступом к файлу по адерсу: "+ filePath);
            throw new InvalidInputDataException("Файл не существует или недоступен для чтения");
        }

        List<Integer> numbers = readNumbersFromExcel(file);
        if (numbers.size() < n) {
            log.warn("Позиция максимума превышает количесвтво данных в столбце, {}>{}",n,numbers.size());
            throw new InvalidInputDataException("В файле недостаточно чисел для поиска N-го максимального");
        }

        int nthMax = quickSelect(numbers, 0, numbers.size() - 1, n);
        log.info("Элемент успешно найден");
        return FileProcessingResponseDto.builder()
                .nthMaxFromFile(nthMax)
                .build();
    }

    private List<Integer> readNumbersFromExcel(File file) {
        List<Integer> numbers = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                numbers.add((int) row.getCell(0).getNumericCellValue());
            }
        } catch (IOException | IllegalStateException exception) {
            log.warn("Не удалось прочитать файл");
            throw new InvalidInputDataException("Ошибка при чтении файла: " + exception.getMessage());
        }
        return numbers;
    }

    private int quickSelect(List<Integer> nums, int left, int right, int n) {
        if (left == right) return nums.get(left);

        int pivotIdx = partition(nums, left, right);
        int count = pivotIdx - left + 1;

        if (count == n) return nums.get(pivotIdx);
        if (count > n) return quickSelect(nums, left, pivotIdx - 1, n);
        return quickSelect(nums, pivotIdx + 1, right, n - count);

    }

    private int partition(List<Integer> nums, int left, int right) {
        int pivot = nums.get(right);
        int i = left;
        for (int j = left; j < right; j++) {
            if (nums.get(j) >= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);
        return i;
    }

    private void swap(List<Integer> nums, int i, int j) {
        int temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }


}
