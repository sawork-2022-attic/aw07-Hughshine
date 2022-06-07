package com.micropos.deliver;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.micropos.dto.OrderDto;

@SpringBootApplication
@EnableDiscoveryClient
public class DeliverApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliverApplication.class, args);
    }

    @Bean
    public Consumer<OrderDto> makeDeliver() {
        return new DeliverMaker();
    }
}
