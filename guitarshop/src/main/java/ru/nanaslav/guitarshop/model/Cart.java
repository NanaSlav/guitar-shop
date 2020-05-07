package ru.nanaslav.guitarshop.model;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(
            mappedBy = "cart",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, orphanRemoval = true
    )
    Set<CartItem> items;

    public Set<CartItem> getItems() { return items; }

    public void setItems(Set<CartItem> items) { this.items = items; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

}
