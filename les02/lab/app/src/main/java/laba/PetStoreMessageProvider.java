package laba;

import java.io.InputStream;
import java.util.Scanner;

public class PetStoreMessageProvider implements MessageProvider {

    @Override
    public String getMessage() {
        StringBuilder result = new StringBuilder();
        // Длина разделителя подобрана так, чтобы точно совпадать с шириной таблицы
        String lineSeparator = "------------------------------------------------------------------------------------------------------\n";

        result.append(lineSeparator);
        // Формируем заголовок таблицы
        result.append(String.format("| %-4s | %-30s | %-40s | %-8s | %-6s |\n", 
                "ID", "Название", "Описание", "Цена", "Кол-во"));
        result.append(lineSeparator);

        // Читаем файл products.csv из папки resources. 
        // try-with-resources автоматически закроет поток is, когда закончит работу.
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("products.csv")) {
            
            if (is == null) {
                return "Ошибка: файл products.csv не найден в src/main/resources!";
            }

            // Создаем Scanner с кодировкой UTF-8 и тоже в try-with-resources для авто-закрытия
            try (Scanner scanner = new Scanner(is, "UTF-8")) {
                
                // Пропускаем первую строку (заголовки колонок)
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    
                    // Разделяем строку по запятой
                    String[] parts = line.split(",");

                    // В CSV 9 колонок. Проверяем, что строка не битая, и берем нужные данные:
                    // [0] - ID, [1] - Название, [2] - Описание, [4] - Цена, [5] - Количество
                    if (parts.length >= 6) {
                        String id = parts[0].trim();
                        String name = parts[1].trim().replace("\"", ""); // Убираем кавычки (например, у "Мышка")
                        String description = parts[2].trim();
                        String price = parts[4].trim();
                        String stock = parts[5].trim();

                        // Обрезаем слишком длинные строки, чтобы таблица не разъезжалась
                        if (name.length() > 30) {
                            name = name.substring(0, 27) + "...";
                        }
                        if (description.length() > 40) {
                            description = description.substring(0, 37) + "...";
                        }

                        // Добавляем отформатированную строку в таблицу
                        result.append(String.format("| %-4s | %-30s | %-40s | %-8s | %-6s |\n", 
                                id, name, description, price, stock));
                    }
                }
            } // здесь scanner закроется автоматически
        } catch (Exception e) {
            return "Ошибка чтения файла: " + e.getMessage();
        }

        result.append(lineSeparator);
        return result.toString();
    }
}