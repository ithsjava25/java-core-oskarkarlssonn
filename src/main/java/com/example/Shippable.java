
package com.example;

import java.math.BigDecimal;

// Shippable: For things that can be shipped (like packages)
public interface Shippable {

    // Returns the weight of the item (in kilograms)
    double weight();

    // Calculates how much it costs to ship this item
    BigDecimal calculateShippingCost();
}