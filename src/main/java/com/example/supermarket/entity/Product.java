package com.example.supermarket.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity")  // <-- Changed from "stock" to "quantity"
    private Integer quantity;  // <-- Changed field name

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Constructors
    public Product() {}

    public Product(String name, Double price, Integer quantity, Category category) {  // <-- Updated param
        this.name = name;
        this.price = price;
         // <-- Updated
        this.category = category;
    }

    // Getters/Setters (update stock to quantity)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }  // <-- Changed from getStock
    public void setQuantity(Integer quantity) { this.quantity = quantity; }  // <-- Changed

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}