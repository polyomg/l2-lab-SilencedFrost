package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    String name;
    String price;

    public Double getPrice() {
        try {
            return Double.parseDouble(price);
        } catch (NumberFormatException ex) {
            log.info("Price of {} was not a number", price);
            return (double) 0;
        }
    }
}
