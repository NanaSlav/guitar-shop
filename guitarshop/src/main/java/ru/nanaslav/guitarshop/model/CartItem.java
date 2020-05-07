package ru.nanaslav.guitarshop.model;

import javax.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private byte quantity;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Product getProduct() { return product; }

    public void setProduct(Product product) { this.product = product; }

    public byte getQuantity() { return quantity; }

    public void setQuantity(byte quantity) { this.quantity = quantity; }

    public Cart getCart() { return cart; }

    public void setCart(Cart cart) { this.cart = cart; }
}
