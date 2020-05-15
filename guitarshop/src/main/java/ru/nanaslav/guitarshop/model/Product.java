package ru.nanaslav.guitarshop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length=64)
    private String name;
    private int price;
    @Column(length=1024)
    private String description;
    @Column(length=1024)
    private String characteristics;
    @Column(length=32)
    private String producer;
    private boolean available;
    private String image;


    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    private Set<Category> categories;

    @OneToMany(
            mappedBy = "product",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, orphanRemoval = true
    )
    Set<CartItem> items;

    @OneToMany(
            mappedBy = "product",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, orphanRemoval = true
    )
    Set<OrderedProduct> orderedProducts;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) { this.available = available; }

    public Set<Category> getCategories() { return categories; }

    public void setCategories(Set<Category> categories) { this.categories = categories; }

    public String getCharacteristics() { return characteristics; }

    public void setCharacteristics(String characteristics) { this.characteristics = characteristics; }

    public String getProducer() { return producer; }

    public void setProducer(String producer) { this.producer = producer; }

    public Set<CartItem> getItems() { return items; }

    public void setItems(Set<CartItem> items) { this.items = items; }

    public Set<OrderedProduct> getOrderedProducts() { return orderedProducts; }

    public void setOrderedProducts(Set<OrderedProduct> orderedProducts) { this.orderedProducts = orderedProducts; }
}
