package ru.nanaslav.guitarshop.model;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean isDeliveryIncluded;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cart")
    private User user;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public boolean isDeliveryIncluded() { return isDeliveryIncluded; }

    public void setDeliveryIncluded(boolean deliveryIncluded) { isDeliveryIncluded = deliveryIncluded; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
