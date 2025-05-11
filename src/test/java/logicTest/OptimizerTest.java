package logicTest;

import org.example.logic.Optimizer;
import org.example.model.Order;
import org.example.model.PaymentMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OptimizerTest {
    @Test
    void optimizeTest(){

        Order order = new Order("1",100.0, new ArrayList<>(List.of("BosBankrut","PUNKTY")));
        Order order2 = new Order("2",120.0, new ArrayList<>(List.of("mZysk","PUNKTY")));
        PaymentMethod paymentMethod = new PaymentMethod("BosBankrut",10,60);
        PaymentMethod paymentMethod2 = new PaymentMethod("PUNKTY",15,40);
        PaymentMethod paymentMethod3 = new PaymentMethod("mZysk",10,120);
        Optimizer optimizer = new Optimizer(new ArrayList<>(List.of(order,order2)),
                List.of(paymentMethod2,paymentMethod,paymentMethod3));
        Map<String, Double> assertResult = Map.of("BosBankrut",50.0,"PUNKTY",40.0,
                "mZysk",108.0);

        Map<String, Double> result = optimizer.optimize();

        Assertions.assertEquals(0,order.getValue());
        Assertions.assertEquals(0,order2.getValue());
        Assertions.assertEquals(0,paymentMethod2.getLimit());
        Assertions.assertEquals(10,paymentMethod.getLimit());
        Assertions.assertEquals(12,paymentMethod3.getLimit());
        Assertions.assertEquals(assertResult, result);
    }
}
