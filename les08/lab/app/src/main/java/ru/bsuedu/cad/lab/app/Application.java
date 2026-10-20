package ru.bsuedu.cad.lab.app;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.bsuedu.cad.lab.config.AppConfig;
import ru.bsuedu.cad.lab.entity.Category;
import ru.bsuedu.cad.lab.entity.Customer;
import ru.bsuedu.cad.lab.entity.Order;
import ru.bsuedu.cad.lab.entity.OrderDetail;
import ru.bsuedu.cad.lab.entity.Product;
import ru.bsuedu.cad.lab.repository.CategoryRepository;
import ru.bsuedu.cad.lab.repository.CustomerRepository;
import ru.bsuedu.cad.lab.repository.ProductRepository;
import ru.bsuedu.cad.lab.service.OrderService;

public class Application {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            CategoryRepository catRepo = ctx.getBean(CategoryRepository.class);
            ProductRepository prodRepo = ctx.getBean(ProductRepository.class);
            CustomerRepository custRepo = ctx.getBean(CustomerRepository.class);
            OrderService orderService = ctx.getBean(OrderService.class);

            // 1. Инициализация данных (Имитация чтения из CSV)
            log.info("Заполнение базы данных...");
            
            Category cat1 = new Category(); cat1.setName("Корма"); cat1.setDescription("Сухие корма");
            cat1 = catRepo.save(cat1);

            Customer customer = new Customer();
            customer.setName("Иван Иванов");
            customer.setEmail("ivan@example.com");
            customer.setAddress("г. Москва");
            customer = custRepo.save(customer);

            Product prod1 = new Product();
            prod1.setName("Корм для собак");
            prod1.setPrice(new BigDecimal("1500.00"));
            prod1.setStockQuantity(50);
            prod1.setCategory(cat1);
            prod1.setCreatedAt(LocalDateTime.now());
            prod1.setUpdatedAt(LocalDateTime.now());
            prod1 = prodRepo.save(prod1);

            // 2. Создание заказа в транзакции
            log.info("Создание заказа...");
            Order order = new Order();
            order.setCustomer(customer);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus("NEW");
            order.setShippingAddress(customer.getAddress());
            order.setTotalPrice(new BigDecimal("1500.00"));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(prod1);
            detail.setQuantity(1);
            detail.setPrice(prod1.getPrice());

            List<OrderDetail> details = new ArrayList<>();
            details.add(detail);
            order.setOrderDetails(details);

            Order savedOrder = orderService.createOrder(order);
            log.info("Заказ успешно создан! ID заказа: {}", savedOrder.getId());

            // 3. Доказательство сохранения (запрос из БД)
            log.info("Проверка сохранения: всего заказов в БД: {}", orderService.getAllOrders().size());
            Order fetchedOrder = orderService.getAllOrders().get(0);
            log.info("Детали заказа из БД: Клиент - {}, Сумма - {}", 
                    fetchedOrder.getCustomer().getName(), fetchedOrder.getTotalPrice());
        }
    }
}