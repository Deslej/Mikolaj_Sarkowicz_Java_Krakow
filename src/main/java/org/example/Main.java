package org.example;

import org.example.logic.Optimizer;
import org.example.mapper.OrderMapper;
import org.example.mapper.PaymentMethodMapper;

import java.util.Map;

public class Main {
    public static void main(String[] args) {

            Optimizer optimizer = new Optimizer(
                    OrderMapper.jsonToOrder(args[0]),
                    PaymentMethodMapper.jsonToPaymentMethod(args[1]));

        Map<String, Double> result = optimizer.optimize();
        result.forEach((key, value) -> System.out.println(key + "  " + value));
    }
}