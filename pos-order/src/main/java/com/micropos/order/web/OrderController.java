package com.micropos.order.web;

import com.micropos.api.OrderApi;
import com.micropos.order.service.OrderService;
import com.micropos.dto.CartDto;
import com.micropos.dto.OrderDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class OrderController implements OrderApi {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderDto> createOrder(CartDto cartDto) {
        
        return null;
    }

    @Override
    public ResponseEntity<List<OrderDto>> listOrders() {
        return null;
    }
}
