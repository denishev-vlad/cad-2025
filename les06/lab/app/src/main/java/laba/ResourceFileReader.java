package laba;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ResourceFileReader {

    // Используем SpEL для получения значения из properties
    @Value("#{ '${csv.file.name}' }")
    private String fileName;

    @PostConstruct
    public void init() {
        System.out.println("[LIFECYCLE] Бин ResourceFileReader успешно инициализирован: " + LocalDateTime.now());
        System.out.println("[INFO] Имя файла для чтения: " + fileName);
    }

    public List<String[]> parseCsv() {
        List<String[]> rows = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                System.err.println("Файл не найден: " + fileName);
                return rows;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false; // Пропускаем заголовки
                        continue;
                    }
                    // Разделяем по запятой, игнорируя запятые внутри кавычек (простая реализация)
                    rows.add(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1));
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return rows;
    }
}