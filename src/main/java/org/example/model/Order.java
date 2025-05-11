package org.example.model;

import lombok.*;

import java.util.List;

@Data@AllArgsConstructor
public class Order implements Comparable<Order> {
    private String id;
    private double value;
    private List<String> promotions;

    @Override
    public int compareTo(Order o) {
        return Double.compare(o.value,this.value);
    }
}
