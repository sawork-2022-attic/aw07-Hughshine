package com.micropos.deliver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.micropos.deliver.model.Deliver;
import com.micropos.deliver.repository.DeliverRepository;

@Service
public class DeliverService {
    @Autowired
    private DeliverRepository deliverRepository;
    
    public List<Deliver> getDelivers() {
        return Streamable.of(deliverRepository.findAll()).toList();
    }
}
