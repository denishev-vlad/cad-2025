package laba;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Primary // Делаем его главным
public class DataBaseRenderer implements MessageRenderer {

    private final JdbcTemplate jdbcTemplate;
    private final ProductProvider productProvider;
    private final CategoryProvider categoryProvider;

    @Autowired
    public DataBaseRenderer(JdbcTemplate jdbcTemplate, ProductProvider productProvider, CategoryProvider categoryProvider) {
        this.jdbcTemplate = jdbcTemplate;
        this.productProvider = productProvider;
        this.categoryProvider = categoryProvider;
    }

    @Override
    public void render() {
        // Сначала сохраняем категории (из-за внешнего ключа)
        List<Category> categories = categoryProvider.getCategories();
        for (Category c : categories) {
            jdbcTemplate.update("INSERT INTO CATEGORIES (id, name) VALUES (?, ?)", c.getId(), c.getName());
        }

        // Затем сохраняем продукты
        List<Product> products = productProvider.getProducts();
        for (Product p : products) {
            jdbcTemplate.update("INSERT INTO PRODUCTS (id, name, description, category_id, price, stock_quantity) VALUES (?, ?, ?, ?, ?, ?)",
                    p.getId(), p.getName(), p.getDescription(), p.getCategoryId(), p.getPrice(), p.getStockQuantity());
        }
        
        System.out.println("[DB] Данные успешно сохранены в БД H2!");
    }
}