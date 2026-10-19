package laba;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequest {

    private static final Logger log = LoggerFactory.getLogger(CategoryRequest.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryRequest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void execute() {
        String sql = "SELECT c.name AS category_name, COUNT(p.id) AS product_count " +
                     "FROM CATEGORIES c " +
                     "JOIN PRODUCTS p ON c.id = p.category_id " +
                     "GROUP BY c.id, c.name " +
                     "HAVING COUNT(p.id) > 1";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        log.info("=== Категории с количеством товаров > 1 ===");
        if (results.isEmpty()) {
            log.info("Таких категорий не найдено.");
        } else {
            for (Map<String, Object> row : results) {
                log.info("Категория: {}, Количество товаров: {}", row.get("category_name"), row.get("product_count"));
            }
        }
    }
}