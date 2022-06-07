package com.micropos.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.micropos.dto.CartDto;
import com.micropos.order.model.Order;
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
