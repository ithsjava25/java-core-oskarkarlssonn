package com.example;

import java.time.LocalDate;

// Perishable: For things that can expire (like food)
// Lets you check the expiration date and if it's expired
public interface Perishable {
    // Returns the expiration date
    LocalDate expirationDate();

    // Checks if the item is expired
    default boolean isExpired() {
        return expirationDate().isBefore(LocalDate.now());
    }
}