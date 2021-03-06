package ru.nanaslav.guitarshop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String address;
    private int price;
    @Column(length = 64)
    private String name;
    @Column(length = 1024)
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @OneToMany(
            mappedBy = "delivery",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, orphanRemoval = true
    )
    Set<Order> orders;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public DeliveryType getDeliveryType() { return deliveryType; }

    public void setDeliveryType(DeliveryType deliveryType) { this.deliveryType = deliveryType; }

    public Set<Order> getOrders() { return orders; }

    public void setOrders(Set<Order> orders) { this.orders = orders; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public void setDefaultPrice() { this.price = 7; }
}
