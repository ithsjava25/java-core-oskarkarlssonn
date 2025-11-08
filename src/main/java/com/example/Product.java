
package com.example;

import java.math.BigDecimal;
import java.util.UUID;

// Product: Represents a general product in the warehouse (base class for all products)
public abstract class Product {

    // Unique ID for each product (never changes)
    private final UUID id;

    // The name of the product (e.g., "Milk", "Laptop")
    private final String name;

    // The category this product belongs to (e.g., Food, Electronics)
    private final Category category;

    // The price of the product (can be updated)
    private BigDecimal price;

    // Gets the product's unique ID
    public UUID uuid() { return id; }

    // Gets the product's name
    public String name() { return name; }

    // Gets the product's category
    public Category category() { return category; }

    // Gets the product's price
    public BigDecimal price() { return price; }

    // Returns details about the product (subclasses must implement)
    public abstract String productDetails();

    // Makes a copy of this product with a new price (subclasses must implement)
    public abstract Product cloneWithPrice(BigDecimal newPrice);

    // Constructor: Creates a new product
    public Product(UUID id, String name, Category category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    // Updates the product's price (used for price changes in Warehouse)
    public void setPrice(BigDecimal newPrice) {
        if (newPrice == null) throw new IllegalArgumentException("Price cannot be null.");
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.price = newPrice.setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
