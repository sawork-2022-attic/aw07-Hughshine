package com.micropos.deliver;

import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.micropos.deliver.model.Deliver;
import com.micropos.deliver.repository.DeliverRepository;
import com.micropos.dto.OrderDto;

public class DeliverMaker implements Consumer<OrderDto> {
    public static final Logger log = LoggerFactory.getLogger(DeliverMaker.class);

    @Autowired
    private DeliverRepository deliverRepository;

    @Override
    public void accept(OrderDto orderDto) {
        log.info("Make delivery for order {} of cartid {}", orderDto.getId(), orderDto.getCartid());
        Deliver deliver = new Deliver();
        deliver.setOrderid(orderDto.getId());
        deliverRepository.save(deliver);
    }
    
}
