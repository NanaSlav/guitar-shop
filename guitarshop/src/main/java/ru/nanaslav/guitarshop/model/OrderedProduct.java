package ru.nanaslav.guitarshop.model;

import javax.persistence.*;

@Entity
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Product getProduct() { return product; }

    public void setProduct(Product product) { this.product = product; }

    public Order getOrder() { return order; }

    public void setOrder(Order order) { this.order = order; }
}
