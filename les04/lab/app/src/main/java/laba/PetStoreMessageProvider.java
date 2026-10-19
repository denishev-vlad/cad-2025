package laba;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetStoreMessageProvider implements MessageProvider {

    private final ResourceFileReader fileReader;

    @Autowired
    public PetStoreMessageProvider(ResourceFileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public String getMessage() {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset='UTF-8'><title>Pet Store</title>");
        html.append("<style>table { border-collapse: collapse; width: 100%; } th, td { border: 1px solid black; padding: 8px; text-align: left; }</style>");
        html.append("</head><body>");
        html.append("<h2>Товары для животных</h2>");
        html.append("<table><tr><th>ID</th><th>Название</th><th>Описание</th><th>Цена</th><th>Кол-во</th></tr>");

        List<String[]> rows = fileReader.parseCsv();
        for (String[] parts : rows) {
            if (parts.length >= 6) {
                String id = parts[0].trim();
                String name = parts[1].trim().replace("\"", "");
                String description = parts[2].trim();
                String price = parts[4].trim();
                String stock = parts[5].trim();

                html.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        id, name, description, price, stock));
            }
        }
        html.append("</table></body></html>");
        return html.toString();
    }
}