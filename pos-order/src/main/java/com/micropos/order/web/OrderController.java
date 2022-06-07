package com.micropos.order.web;

import com.micropos.api.OrderApi;
import com.micropos.order.service.OrderService;
import com.micropos.dto.CartDto;
import com.micropos.dto.OrderDto;
import com.micropos.order.model.Order;
import com.micropos.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private OrderMapper orderMapper;
    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public ResponseEntity<OrderDto> createOrder(CartDto cartDto) {
        Order order = this.orderService.createOrder(cartDto);
        if (order != null) {
            return new ResponseEntity<OrderDto>(orderMapper.toOrderDto(order), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<OrderDto>> listOrders() {
        List<Order> orders = orderService.getOrders();
        return new ResponseEntity<>(orderMapper.toOrderDtos(orders), HttpStatus.OK);
    }
}
