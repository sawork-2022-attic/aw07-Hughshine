package com.micropos.deliver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DeliverApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliverApplication.class, args);
    }
}
