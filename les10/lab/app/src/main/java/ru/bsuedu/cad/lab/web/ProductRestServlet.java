package ru.bsuedu.cad.lab.web;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bsuedu.cad.lab.entity.Product;
import ru.bsuedu.cad.lab.repository.ProductRepository;

@WebServlet("/api/products")
public class ProductRestServlet extends HttpServlet {
    private ProductRepository productRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        productRepository = ctx.getBean(ProductRepository.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Product> products = productRepository.findAll();

        // Преобразуем в DTO для вывода только нужных полей
        List<ProductDto> dtos = products.stream().map(p -> new ProductDto(
                p.getName(),
                p.getCategory() != null ? p.getCategory().getName() : "Без категории",
                p.getStockQuantity()
        )).collect(Collectors.toList());

        resp.getWriter().write(objectMapper.writeValueAsString(dtos));
    }

    // Простой класс для JSON-ответа
    public static class ProductDto {
        public String name;
        public String categoryName;
        public Integer stockQuantity;

        public ProductDto(String name, String categoryName, Integer stockQuantity) {
            this.name = name;
            this.categoryName = categoryName;
            this.stockQuantity = stockQuantity;
        }
    }
}