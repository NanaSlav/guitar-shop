package ru.nanaslav.guitarshop.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nanaslav.guitarshop.repository.CartItemRepository;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    User user;

    @OneToMany(
            mappedBy = "cart",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, orphanRemoval = true
    )
    Set<CartItem> items;

    public Cart(User user) {
        this.user = user;
        this.items = new HashSet<>();
    }

    public Cart() {}

    public Set<CartItem> getItems() { return items; }

    public void setItems(Set<CartItem> items) { this.items = items; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public void addProduct(CartItemRepository cartItemRepository, Product product) {
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(1);
        item.setCart(this);
        cartItemRepository.save(item);
    }
}
