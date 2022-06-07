package com.micropos.deliver.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.micropos.api.DeliverApi;
import com.micropos.deliver.mapper.DeliverMapper;
import com.micropos.deliver.model.Deliver;
import com.micropos.deliver.repository.DeliverRepository;
import com.micropos.deliver.service.DeliverService;
import com.micropos.dto.DeliverDto;

@RestController
public class DeliverController implements DeliverApi {

    @Autowired
    DeliverService deliverService;

    @Autowired
    DeliverMapper deliverMapper;
    
    @Override
    public
    ResponseEntity<List<DeliverDto>> listDelivers() {
        List<Deliver> delivers = deliverService.getDelivers();
        return new ResponseEntity<>(deliverMapper.toDeliverDtos(delivers), HttpStatus.OK);
    }
}
