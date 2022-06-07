package com.micropos.deliver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.micropos.deliver.model.Deliver;

@Repository
public interface DeliverRepository extends CrudRepository<Deliver, Integer> {
    
}
