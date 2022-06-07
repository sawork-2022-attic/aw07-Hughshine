package com.micropos.deliver.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "delivers")
public class Deliver {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    Integer id;

    @Getter
    @Setter
    Integer orderid;
}
