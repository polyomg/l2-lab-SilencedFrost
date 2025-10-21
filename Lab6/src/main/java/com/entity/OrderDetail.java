package com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "Orderdetails")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double price;

    Integer quantity;

    @ManyToOne @JoinColumn(name = "Productid")
    Product product;

    @ManyToOne @JoinColumn(name = "Orderid")
    Order order;
}
