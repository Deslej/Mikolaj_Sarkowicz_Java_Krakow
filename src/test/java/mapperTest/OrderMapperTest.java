package mapperTest;

import org.example.mapper.OrderMapper;
import org.example.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

public class OrderMapperTest {

    @Test
    void jsonToOrderTest() throws Exception {
        String json = """
                [
                    {
                        "id": "Order1",
                        "value": 10.5,
                        "promotions": ["Promo1", "Promo2"]
                    },
                    {
                        "id": "Order2",
                        "value": 20,
                        "promotions": []
                    }
                ]
                """;

        File tempFile = new File("orders.json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(json);
        }

        List<Order> orders = OrderMapper.jsonToOrder(tempFile.getAbsolutePath());

        Assertions.assertEquals(2, orders.size());

        Assertions.assertEquals("Order1", orders.get(0).getId());
        Assertions.assertEquals(10.5, orders.get(0).getValue());
        Assertions.assertEquals(List.of("PUNKTY","Promo1", "Promo2"), orders.get(0).getPromotions());

        Assertions.assertEquals("Order2", orders.get(1).getId());
        Assertions.assertEquals(20.0, orders.get(1).getValue());
        Assertions.assertEquals(List.of("PUNKTY"), orders.get(1).getPromotions());

        tempFile.delete();
    }
}
