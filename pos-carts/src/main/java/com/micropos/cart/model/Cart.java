package com.micropos.cart.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Accessors(fluent = true, chain = true)
public class Cart implements Serializable {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "carts_items", 
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    @Setter
    @Getter
    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        item.updateId();
        return items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }
}
