package com.micropos.order.service;

import org.springframework.stereotype.Service;

import com.micropos.dto.CartDto;
import com.micropos.order.mapper.OrderMapper;
// import com.micropos.cart.mapper.OrderMapper;
// import com.micropos.cart.model.Cart;
import com.micropos.order.model.Order;

// import com.micropos.cart.model.Item;
import com.micropos.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.stream.function.StreamBridge;
import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderMapper orderMapper;

    private OrderRepository orderRepository;

    // @Autowired
    private StreamBridge streamBridge;

    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public List<Order> getOrders() {
        return Streamable.of(orderRepository.findAll()).toList();
    }

    public Order createOrder(CartDto cartDto) {
        System.out.println("Creating order...");
        Order order = new Order();
        order.setCartId(cartDto.getId());
        order = orderRepository.save(order);
        streamBridge.send("deliver", orderMapper.toOrderDto(order));
        return order;
    }

}
