package com.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

// Category: Represents a product type (uses caching for efficiency, flyweight)
public final class Category {

    // Flyweight Cache - Used for HashMap: stores all unique Category objects
    private static final Map<String, Category> CACHE = new ConcurrentHashMap<>();

    // Fields
    private final String name;

    // Constructor - Makes a new Category (only called inside this class)
    private Category(String name) {
        this.name = name;
    }

    // Makes a new Category or returns an existing one
    public static Category of(String name) {

        // Check for null
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }

        // Remove leading/trailing spaces
        String trimmed = name.trim();

        // Check for blank
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Category name can't be blank");
        }
        // Normalize: capitalize first letter, lowercase the rest
        String normalized = capitalize(trimmed);

        // Return the same instance for the same normalized name
        return CACHE.computeIfAbsent(normalized, n -> new Category(n));
    }

    // Accessor
    public String getName() {
        return name;
    }

    // Helpers
    private static String capitalize(String s) {
        if (s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    // Equality - Checks if two categories are the same
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    // Hashing
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // Debugging - Shows the category as text
    @Override
    public String toString() {
        return "Category{" + "name='" + name + '\'' + '}';
    }
}
