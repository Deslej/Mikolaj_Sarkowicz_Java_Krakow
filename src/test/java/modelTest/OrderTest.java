package modelTest;

import org.example.model.Order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderTest {
    @Test
    void buildingObjectTest(){
        String id = "Order1";
        double value = 5;
        List<String> promotions = List.of("Bank");

        Order order = new Order(id,value,promotions);

        Assertions.assertEquals(id,order.getId());
        Assertions.assertEquals(value,order.getValue());
        Assertions.assertEquals(promotions,order.getPromotions());
    }
}
