package laba;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConcreteCategoryProvider implements CategoryProvider {

    @Value("#{ '${csv.category.file}' }")
    private String fileName;

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) return categories;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                boolean isFirst = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirst) { isFirst = false; continue; }
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        categories.add(new Category(Integer.parseInt(parts[0].trim()), parts[1].trim()));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения категорий: " + e.getMessage());
        }
        return categories;
    }
}