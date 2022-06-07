package com.micropos.cart.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.cart.mapper.CartMapper;
import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import com.micropos.cart.repository.CartRepository;
import com.micropos.cart.repository.ItemRepository;
import com.micropos.dto.CartDto;
import com.micropos.dto.OrderDto;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private ItemRepository itemRepository;

    private final String COUNTER_URL = "http://POS-COUNTER/counter/";
    private final String ORDER_URL = "http://POS-ORDER/order/";

    private CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @LoadBalanced
    private RestTemplate restTemplate;

    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setCircuitBreakerFactory(CircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public Double checkout(Cart cart) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

        CartDto cartDto = cartMapper.toCartDto(cart);
        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = null;
        try {
            System.out.println("checkout: " + cartDto);
            request = new HttpEntity<>(mapper.writeValueAsString(cartDto), headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpEntity<String> finalRequest = request;
        
        OrderDto orderDto = circuitBreaker.run(() -> restTemplate.postForObject(ORDER_URL, finalRequest, OrderDto.class), throwable -> null);
        if (orderDto == null) {
            return -1.0; 
        }
        Double total =
                circuitBreaker.run(() -> restTemplate.postForObject(COUNTER_URL + "/checkout", finalRequest, Double.class), throwable -> -1.0);
        
        return total;
    }

    public Integer test() {
        Integer test = restTemplate.getForObject(COUNTER_URL + "/test", Integer.class);
        return test;
    }

    @Override
    public Double checkout(Integer cartId) {
        Optional<Cart> cart = this.cartRepository.findById(cartId);

        if (cart.isEmpty()) return Double.valueOf(-1);

        return this.checkout(cart.get());
    }

    @Override
    public Cart add(Cart cart, Item item) {
        System.out.println(cart);
        System.out.println(item);
        if (item.id() == null || itemRepository.findById(item.id()).isEmpty()) {
            item.id(null);
        }
        if (cart.addItem(item)) {
            System.out.println(cart);
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    public List<Cart> getAllCarts() {
        return Streamable.of(cartRepository.findAll()).toList();
    }

    @Override
    public Optional<Cart> getCart(Integer cartId) {
        return cartRepository.findById(cartId);
    }

    public Cart createCart() {
        Cart cart = new Cart();
        System.out.println(cart);
        return cartRepository.save(cart);
    }

}
