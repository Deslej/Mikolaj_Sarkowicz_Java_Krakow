package modelTest;

import org.example.model.PaymentMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentMethodTest {
    @Test
    void buildingObjectTest(){
        String id = "Bank";
        int discount = 5;
        double limit = 100;

        PaymentMethod paymentMethod = new PaymentMethod(id,discount,limit);

        Assertions.assertEquals(id,paymentMethod.getId());
        Assertions.assertEquals(discount,paymentMethod.getDiscount());
        Assertions.assertEquals(limit,paymentMethod.getLimit());
    }
}
