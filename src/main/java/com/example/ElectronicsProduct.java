package com.example;

import java.math.BigDecimal;
import java.util.UUID;

// ElectronicsProduct: Represents an electronic item you can ship
// Stores warranty info and weight, and can calculate shipping cost

public class ElectronicsProduct extends Product implements Shippable {

	// Fields - Stores warranty months and weight
	private final int warrantyMonths;
	private final BigDecimal weight;

	// Constructor - Makes a new ElectronicsProduct
	public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
		super(id, name, category, price);
		if (warrantyMonths < 0) {
			throw new IllegalArgumentException("Warranty months cannot be negative.");
		}
		this.warrantyMonths = warrantyMonths;
		this.weight = weight;
	}

	// Accessor - Returns the warranty in months
	public int warrantyMonths() {
		return warrantyMonths;
	}

	// Accessor - Returns the weight as BigDecimal
	public BigDecimal getWeight() {
		if (weight == null) {
			throw new IllegalArgumentException("Weight cannot be null.");
		}
		if (weight.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Weight cannot be negative.");
		}
		return weight;
	}

	// Shippable Implementation - Returns the weight as a double (needed for shipping)
	@Override
	public double weight() {
		return weight != null ? weight.doubleValue() : 0.0;
	}

	// Shippable Implementation - Calculates the shipping cost for this product
	@Override
	public BigDecimal calculateShippingCost() {
		BigDecimal base = new BigDecimal("79");
		if (weight() > 5.0) {
			return base.add(new BigDecimal("49"));
		} else {
			return base;
		}
	}

	// Product Details - Shows product details as text
	@Override
	public String productDetails() {
		return "Electronics: " + name() + ", Warranty: " + warrantyMonths + " months";
	}

	// Cloning - Makes a copy of this product with a new price
	@Override
	public Product cloneWithPrice(BigDecimal newPrice) {
		return new ElectronicsProduct(
			this.uuid(),
			this.name(),
			this.category(),
			newPrice,
			this.warrantyMonths,
			this.weight
		);
	}
}
