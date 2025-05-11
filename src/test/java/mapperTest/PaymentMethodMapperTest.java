package mapperTest;

import org.example.mapper.PaymentMethodMapper;
import org.example.model.PaymentMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

public class PaymentMethodMapperTest {
    @Test
    void jsonToPaymentMethodTest() throws Exception {
        String json = """
                [
                      {
                        "id": "BosBankrut",
                        "discount": "5",
                        "limit": "250.00"
                      },
                      {
                        "id": "KartaLojalnosciowa",
                        "discount": "8",
                        "limit": "250.00"
                      }
                ]
                """;

        File tempFile = new File("paymentmethods.json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(json);
        }

        List<PaymentMethod> paymentMethods = PaymentMethodMapper.jsonToPaymentMethod(tempFile.getAbsolutePath());

        Assertions.assertEquals(2, paymentMethods.size());

        Assertions.assertEquals("BosBankrut", paymentMethods.get(0).getId());
        Assertions.assertEquals(5, paymentMethods.get(0).getDiscount());
        Assertions.assertEquals(250.00, paymentMethods.get(0).getLimit());

        Assertions.assertEquals("KartaLojalnosciowa", paymentMethods.get(1).getId());
        Assertions.assertEquals(8, paymentMethods.get(1).getDiscount());
        Assertions.assertEquals(250.00, paymentMethods.get(1).getLimit());

        tempFile.delete();
    }
}
