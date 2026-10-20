package ru.bsuedu.cad.lab.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.bsuedu.cad.lab.entity.Customer;
import ru.bsuedu.cad.lab.entity.Order;
import ru.bsuedu.cad.lab.repository.CustomerRepository;
import ru.bsuedu.cad.lab.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderViewController {

    private final OrderService orderService;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderViewController(OrderService orderService, CustomerRepository customerRepository) {
        this.orderService = orderService;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders"; // Имя HTML-шаблона
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customers", customerRepository.findAll());
        return "order-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) return "redirect:/orders";
        model.addAttribute("order", order);
        model.addAttribute("customers", customerRepository.findAll());
        return "order-form";
    }

    @PostMapping("/save")
    public String saveOrder(@RequestParam Long customerId, 
                            @RequestParam BigDecimal totalPrice,
                            @RequestParam(required = false) Long id) {
        
        Order order;
        if (id != null) {
            order = orderService.getOrderById(id);
        } else {
            order = new Order();
            order.setOrderDate(LocalDateTime.now());
            order.setStatus("NEW");
        }
        
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        order.setCustomer(customer);
        order.setTotalPrice(totalPrice);
        order.setShippingAddress(customer.getAddress());

        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}