package com.micropos.deliver.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;

import com.micropos.deliver.model.Deliver;
import com.micropos.dto.DeliverDto;

@Mapper
public class DeliverMapper {
    public List<DeliverDto> toDeliverDtos(List<Deliver> delivers) {
        List<DeliverDto> list = new ArrayList<>(delivers.size());
        for (Deliver deliver : delivers) {
            list.add(toDeliverDto(deliver));
        }
        return list;
    }

    public DeliverDto toDeliverDto(Deliver deliver) {

        return new DeliverDto().id(deliver.getId()).orderid(deliver.getOrderid());
    }
}
