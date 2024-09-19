package com.zosh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item", indexes = @Index(name = "idx_order_item_id", columnList = "id"))
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @ManyToOne
    private Food food;

    private int quantity;

    private Long totalPrice;

    private List<String> ingredients;
}
