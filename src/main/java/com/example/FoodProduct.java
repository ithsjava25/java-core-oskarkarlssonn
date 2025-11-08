package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.math.RoundingMode;

// FoodProduct: Represents a food item you can ship
// Stores expiration date and weight, and can calculate shipping cost
public class FoodProduct extends Product implements Perishable, Shippable {

	// Fields - Stores expiration date and weight
	private final LocalDate expirationDate;
	private final BigDecimal weight;

	// Constructor - Makes a new FoodProduct
	public FoodProduct(UUID id, String name, Category category, BigDecimal price, LocalDate expirationDate, BigDecimal weight) {
		super(id, name, category, price);
		if (price.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Price cannot be negative.");
		}
		if (weight == null) {
			throw new IllegalArgumentException("Weight cannot be null.");
		}
		if (weight.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Weight cannot be negative.");
		}
		this.expirationDate = expirationDate;
		this.weight = weight;
	}

	// Perishable Implementation - Returns the expiration date
	@Override
	public LocalDate expirationDate() {
		return expirationDate;
	}

    // Perishable Implementation - Checks if the food is expired
	@Override
	public boolean isExpired() {
		return expirationDate.isBefore(LocalDate.now());
	}

	// Shippable Implementation - Returns the weight as a double (needed for shipping)
	@Override
	public double weight() {
		return weight.doubleValue();
	}

    // Shippable Implementation - Calculates the shipping cost for this food
	@Override
	public BigDecimal calculateShippingCost() {
		return weight.multiply(BigDecimal.valueOf(50)).setScale(2, RoundingMode.HALF_UP);
	}

	// Product Details - Shows product details as text
	@Override
	public String productDetails() {
		return "Food: " + name() + ", Expires: " + expirationDate;
	}

    // Cloning - Makes a copy of this food product with a new price
	@Override
	public Product cloneWithPrice(BigDecimal newPrice) {
		return new FoodProduct(this.uuid(), this.name(), this.category(), newPrice, this.expirationDate, this.weight);
	}
}
