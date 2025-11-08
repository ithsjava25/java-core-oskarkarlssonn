
package com.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.math.BigDecimal;

// Warehouse: Manages products in a warehouse (one instance per warehouse name)
public class Warehouse {

	// Stores all warehouse instances by name (singleton pattern)
	private static final Map<String, Warehouse> warehouses = new ConcurrentHashMap<>();

	// The name of this warehouse
	private final String name;

	// List of products in this warehouse
	private final List<Product> products = new ArrayList<>();

	// Private constructor: Only called inside this class
	private Warehouse(String name) {
		this.name = Objects.requireNonNull(name, "Warehouse name cannot be null");
	}

	// Gets the warehouse instance for a given name (creates if missing)
	public static Warehouse getInstance(String name) {
		return warehouses.computeIfAbsent(name, Warehouse::new);
	}

	// Gets the name of this warehouse
	public String getName() {
		return name;
	}

	// Adds a product to the warehouse (no duplicates by name)
	public void addProduct(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null.");
		}
		for (Product p : products) {
			if (p.name().equalsIgnoreCase(product.name())) {
				throw new IllegalArgumentException("Product with name '" + product.name() + "' already exists.");
			}
		}
		products.add(product);
	}

	// Removes a product by its name
	public boolean removeProduct(String productName) {
		return products.removeIf(p -> p.name().equalsIgnoreCase(productName));
	}

	// Gets all products in the warehouse (read-only list)
	public List<Product> getProducts() {
		return Collections.unmodifiableList(products);
	}

	// EdgeCaseTest - Gets all products that can be shipped
	public List<Shippable> shippableProducts() {
		return products.stream()
				.filter(p -> p instanceof Shippable)
				.map(p -> (Shippable) p)
				.collect(Collectors.toList());
	}

	// EdgeCaseTest - Gets all products that can expire (perishable)
	public List<Perishable> perishableProducts() {
		return products.stream()
				.filter(p -> p instanceof Perishable)
				.map(p -> (Perishable) p)
				.collect(Collectors.toList());
	}

	// Removes a product by its unique ID
	public boolean remove(UUID uuid) {
		Objects.requireNonNull(uuid, "UUID cannot be null");
		return products.removeIf(p -> uuid.equals(p.uuid()));
	}

	// Gets a product by its unique ID (returns Optional)
	public Optional<Product> getProductById(UUID uuid) {
		Objects.requireNonNull(uuid, "UUID cannot be null");
		return products.stream().filter(p -> uuid.equals(p.uuid())).findFirst();
	}

	// EdgeCaseTest - Groups products by their category
	public Map<Category, List<Product>> getProductsGroupedByCategories() {
		if (products.isEmpty()) return new HashMap<>();
		return products.stream().collect(Collectors.groupingBy(Product::category));
	}

	// EdgeCaseTest - Updates the price of a product by its unique ID
	public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
		Objects.requireNonNull(uuid, "UUID cannot be null");
		Objects.requireNonNull(newPrice, "New price cannot be null");
		Product product = getProductById(uuid).orElseThrow(() ->
			new NoSuchElementException("Product not found with id: " + uuid));
		product.setPrice(newPrice);
	}

	// EdgeCaseTest - Gets a list of expired perishable products
	public List<Perishable> expiredProducts() {
		List<Perishable> expired = new ArrayList<>();
		for (Product p : products) {
			if (p instanceof Perishable per && per.expirationDate().isBefore(java.time.LocalDate.now())) {
				expired.add(per);
			}
		}
		return expired;
	}

	// Returns true if the warehouse has no products
	public boolean isEmpty() {
		return products.isEmpty();
	}

	// Removes all products from the warehouse (used for testing)
	public void clearProducts() {
		products.clear();
	}
}