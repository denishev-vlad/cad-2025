package ru.bsuedu.cad.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.bsuedu.cad.lab.entity.Customer;
import ru.bsuedu.cad.lab.entity.Order;
import ru.bsuedu.cad.lab.repository.CustomerRepository;
import ru.bsuedu.cad.lab.service.OrderService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/create-order")
public class CreateOrderServlet extends HttpServlet {
    private OrderService orderService;
    private CustomerRepository customerRepository;

    @Override
    public void init() throws ServletException {
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        orderService = ctx.getBean(OrderService.class);
        customerRepository = ctx.getBean(CustomerRepository.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        List<Customer> customers = customerRepository.findAll();

        StringBuilder html = new StringBuilder("<html><body>");
        html.append("<h1>Создание заказа</h1>");
        html.append("<form action='create-order' method='POST'>");
        html.append("Клиент: <select name='customerId'>");
        for (Customer c : customers) {
            html.append(String.format("<option value='%d'>%s</option>", c.getId(), c.getName()));
        }
        html.append("</select><br>");
        html.append("Сумма заказа: <input type='number' step='0.01' name='totalPrice' required><br>");
        html.append("<input type='submit' value='Создать'>");
        html.append("</form></body></html>");
        resp.getWriter().write(html.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long customerId = Long.parseLong(req.getParameter("customerId"));
        BigDecimal totalPrice = new BigDecimal(req.getParameter("totalPrice"));

        Customer customer = customerRepository.findById(customerId).orElseThrow();
        
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("NEW");
        order.setTotalPrice(totalPrice);
        order.setShippingAddress(customer.getAddress());

        orderService.createOrder(order);

        resp.sendRedirect("orders");
    }
}