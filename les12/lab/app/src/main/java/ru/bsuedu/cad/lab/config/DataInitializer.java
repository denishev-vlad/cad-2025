package ru.bsuedu.cad.lab.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import ru.bsuedu.cad.lab.entity.Category;
import ru.bsuedu.cad.lab.entity.Customer;
import ru.bsuedu.cad.lab.entity.Product;
import ru.bsuedu.cad.lab.repository.CategoryRepository;
import ru.bsuedu.cad.lab.repository.CustomerRepository;
import ru.bsuedu.cad.lab.repository.ProductRepository;

@Component
public class DataInitializer {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void initData() {
        // Проверяем, пустая ли база, чтобы не дублировать данные при перезапуске
        if (customerRepository.count() == 0) {
            
            // 1. Создаем категорию
            Category cat = new Category();
            cat.setName("Корма");
            cat.setDescription("Сухие и влажные корма");
            categoryRepository.save(cat);

            // 2. Создаем клиентов
            Customer c1 = new Customer();
            c1.setName("Иван Иванов");
            c1.setEmail("ivan@example.com");
            c1.setPhone("+79991112233");
            c1.setAddress("г. Москва, ул. Ленина 1");
            customerRepository.save(c1);

            Customer c2 = new Customer();
            c2.setName("Петр Петров");
            c2.setEmail("petr@example.com");
            c2.setPhone("+79994445566");
            c2.setAddress("г. Санкт-Петербург, ул. Мира 2");
            customerRepository.save(c2);

            // 3. Создаем продукт
            Product p = new Product();
            p.setName("Корм для собак");
            p.setDescription("Полнорационный корм");
            p.setCategory(cat);
            p.setPrice(new BigDecimal("1500.00"));
            p.setStockQuantity(50);
            p.setCreatedAt(LocalDateTime.now());
            p.setUpdatedAt(LocalDateTime.now());
            productRepository.save(p);
            
            System.out.println("================ БАЗА ДАННЫХ УСПЕШНО ЗАПОЛНЕНА ================");
        }
    }
}