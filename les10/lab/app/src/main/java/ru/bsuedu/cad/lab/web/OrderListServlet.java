package ru.bsuedu.cad.lab.web;

import java.io.IOException;
import java.util.List;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.bsuedu.cad.lab.entity.Order;
import ru.bsuedu.cad.lab.service.OrderService;

@WebServlet("/orders")
public class OrderListServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        orderService = ctx.getBean(OrderService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        List<Order> orders = orderService.getAllOrders();

        StringBuilder html = new StringBuilder("<html><body>");
        html.append("<h1>Список заказов</h1>");
        html.append("<a href='create-order'>Создать новый заказ</a><br><br>");
        html.append("<table border='1'><tr><th>ID</th><th>Клиент</th><th>Дата</th><th>Сумма</th></tr>");
        
        for (Order order : orders) {
            html.append(String.format("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                    order.getId(), order.getCustomer().getName(), order.getOrderDate(), order.getTotalPrice()));
        }
        
        html.append("</table></body></html>");
        resp.getWriter().write(html.toString());
    }
}