package com.micropos.order.service;

import org.springframework.stereotype.Service;

// import com.micropos.cart.mapper.OrderMapper;
// import com.micropos.cart.model.Cart;
import com.micropos.order.model.Order;

// import com.micropos.cart.model.Item;
import com.micropos.order.repository.OrderRepository;
import com.micropos.dto.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import java.util.List;
@Service
public class OrderService {

    private OrderRepository orderRepository;

    // private final String COUNTER_URL = "http://POS-COUNTER/counter/";
    // private final String ORDER_URL = "http://POS-ORDER/order/";

    // private OrderMapper orderMapper;

    // @Autowired
    // public void OrderMapper(CartMapper orderMapper) {
    //     this.orderMapper = orderMapper;
    // }

    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // public double getOrders() {

    //     // System.out.println("checkout: " + cart);
    //     // if (cart.getItems() == null)
    //     //     return 0.0;
    //     // double total = 0;
    //     // for (CartItemDto item : cart.getItems()) {
    //     //     total += item.getAmount() * item.getProduct().getPrice();
    //     // }
    //     // return total;
    //     return 0.0;
    // }

    // @Override
    public List<Order> getOrders() {
        return Streamable.of(orderRepository.findAll()).toList();
    }

}
