package org.example.model;

import lombok.*;

@Data@AllArgsConstructor
public class PaymentMethod {
    private String id;
    private int discount;
    private double limit;

}
