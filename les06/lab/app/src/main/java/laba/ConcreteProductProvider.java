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
public class ConcreteProductProvider implements ProductProvider {

    @Value("#{ '${csv.file.name}' }")
    private String fileName;

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) return products;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                boolean isFirst = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirst) { isFirst = false; continue; }
                    // Используем regex для корректного разделения по запятым (игнорируя запятые в кавычках)
                    String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    if (parts.length >= 6) {
                        products.add(new Product(
                                Integer.parseInt(parts[0].trim()),
                                parts[1].trim().replace("\"", ""),
                                parts[2].trim(),
                                Integer.parseInt(parts[3].trim()),
                                Double.parseDouble(parts[4].trim()),
                                Integer.parseInt(parts[5].trim())
                        ));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения продуктов: " + e.getMessage());
        }
        return products;
    }
}