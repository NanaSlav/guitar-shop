package ru.nanaslav.guitarshop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery")
    private Delivery delivery;

    @OneToMany(
            mappedBy = "order",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, orphanRemoval = true
    )
    Set<OrderedProduct> products;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public User getCustomer() { return customer; }

    public void setCustomer(User customer) { this.customer = customer; }

    public Delivery getDelivery() { return delivery; }

    public void setDelivery(Delivery delivery) { this.delivery = delivery; }

    public Set<OrderedProduct> getProducts() { return products; }

    public void setProducts(Set<OrderedProduct> products) { this.products = products; }
}
