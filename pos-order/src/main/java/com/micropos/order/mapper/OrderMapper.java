package com.micropos.order.mapper;

import java.util.ArrayList;
import java.util.List;

import com.micropos.order.model.Order;
import com.micropos.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper
public class OrderMapper {

    public List<OrderDto> toOrderDtos(List<Order> orders) {
        List<OrderDto> list = new ArrayList<>(orders.size());
        for (Order order : orders) {
            list.add(toOrderDto(order));
        }
        return list;
    }

    public OrderDto toOrderDto(Order order) {
        return new OrderDto().id(order.getId()).cartid(order.getCartId());
    }
}
