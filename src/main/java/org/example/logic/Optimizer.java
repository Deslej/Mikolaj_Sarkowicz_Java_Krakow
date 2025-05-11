package org.example.logic;

import org.example.model.Order;
import org.example.model.PaymentMethod;

import java.util.*;
import java.util.stream.Collectors;


public class Optimizer {
    private  List<Order> orders;
    private  Map<String, PaymentMethod> paymentMethods;
    private  Map<String, Double> spendMoneyInMethods = new HashMap<>();

    public Optimizer(List<Order> orders, List<PaymentMethod> paymentMethods) {
        this.orders = orders;
        this.paymentMethods = paymentMethods.stream()
                .collect(Collectors.toMap(PaymentMethod::getId, pm -> pm));
    }

    public Map<String, Double> optimize() {
        Collections.sort(orders);
        for (Order order : orders) {

            List<PaymentMethod> paymentMethodList = order.getPromotions().stream()
                    .map(paymentMethods::get)
                    .filter(Objects::nonNull)
                    .filter(pm -> order.getValue() <= pm.getLimit())
                    .toList();

            PaymentMethod bestPayment = paymentMethodList.stream()
                    .max(Comparator.comparingDouble(pm -> order.getValue() * (pm.getDiscount() / 100.0)))
                    .orElse(null);

            if (bestPayment != null) {
                payingOnePayMethods(order, bestPayment);
            } else {
                payWithDifferentMethods(order);
            }
        }
        return spendMoneyInMethods;
    }


    private void payingOnePayMethods(Order order, PaymentMethod paymentMethod) {
        double discount = (double) paymentMethod.getDiscount() / 100;
        double oValue = order.getValue();
        double payLimit = paymentMethod.getLimit();
        double spentMoney = oValue - oValue * discount;
        if (payLimit >= oValue ) {
            settingValues(order,paymentMethod,0.0,payLimit - spentMoney,spentMoney);
        }
    }

    private void payWithDifferentMethods(Order order) {
        PaymentMethod pointsMethod = paymentMethods.get("PUNKTY");

        if (pointsMethod == null) return;

        double orderValue = order.getValue();
        double points = pointsMethod.getLimit();

        if (points / orderValue >= 0.1) {
            double discount = orderValue * 0.1;
            order.setValue(orderValue - discount);
        }

        if (points >= orderValue) {
            settingValues(order, pointsMethod, 0.0, points - orderValue, orderValue);
            return;
        }

        paymentMethods.values().stream()
                .filter(pm -> !pm.getId().equalsIgnoreCase("PUNKTY"))
                .max(Comparator.comparingDouble(PaymentMethod::getLimit))
                .ifPresent(method -> payWithTwoMethods(order, pointsMethod, method));
    }

    private void payWithTwoMethods(Order order, PaymentMethod pointsMethod, PaymentMethod secondaryMethod) {
        double orderValue = order.getValue();
        double pointsLimit = pointsMethod.getLimit();
        double remainingValue = orderValue - pointsLimit;

        if (pointsLimit > 0) {
            settingValues(order,pointsMethod,remainingValue,0.0,pointsLimit);
        }

        if (remainingValue <= secondaryMethod.getLimit()) {
            settingValues(order,secondaryMethod,order.getValue() - remainingValue,
                    secondaryMethod.getLimit() - remainingValue,remainingValue);
        }
    }

    private void addToSpendMoneyInMethods(String methodId, double amount){
        spendMoneyInMethods.merge(methodId, amount, Double::sum);
    }

    private void settingValues(Order order,PaymentMethod paymentMethod, Double valueForOrder,
                               Double valueForPayMethod,Double spendMoney){
        paymentMethod.setLimit(valueForPayMethod);
        order.setValue(valueForOrder);
        addToSpendMoneyInMethods(paymentMethod.getId(),spendMoney);
    }
}
