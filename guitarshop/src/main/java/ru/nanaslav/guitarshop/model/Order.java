package ru.nanaslav.guitarshop.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean executed;
    private Date date;

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

    public int getTotal() {
        int total = 0;
        for (OrderedProduct product : products) {
            total += product.getCost();
        }
        return total;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public User getCustomer() { return customer; }

    public void setCustomer(User customer) { this.customer = customer; }

    public Delivery getDelivery() { return delivery; }

    public void setDelivery(Delivery delivery) { this.delivery = delivery; }

    public Set<OrderedProduct> getProducts() { return products; }

    public void setProducts(Set<OrderedProduct> products) { this.products = products; }

    public boolean isExecuted() { return executed; }

    public void setExecuted(boolean executed) { this.executed = executed; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
