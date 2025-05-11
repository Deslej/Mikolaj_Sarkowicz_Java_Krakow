package org.example.mapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.example.Exception.FileNotOrderJsonException;
import org.example.model.PaymentMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class PaymentMethodMapper {

    public static List<PaymentMethod> jsonToPaymentMethod(String path) {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        JsonFactory factory = new JsonFactory();

        if (!path.endsWith("paymentmethods.json")) {
            throw new FileNotOrderJsonException("Invalid file: expected ending with 'paymentmethods.json' but received: " + path);
        }


        try (FileInputStream fis = new FileInputStream(path);
             JsonParser parser = factory.createParser(fis)) {

            parser.nextToken();

            while (parser.nextToken() == JsonToken.START_OBJECT) {
                PaymentMethod paymentMethod = parsePaymentMethod(parser);
                paymentMethods.add(paymentMethod);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading or parsing the file: " + path, e);
        } catch (FileNotOrderJsonException e) {
            throw e;
        }

        return paymentMethods;
    }

    private static PaymentMethod parsePaymentMethod(JsonParser parser)throws IOException {
        String id = null;
        int discount = 0;
        double limit = 0;

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                JsonToken currentToken = parser.getCurrentToken();

                if (currentToken == JsonToken.FIELD_NAME) {
                    String fieldName = parser.getText();
                    parser.nextToken();
                    switch (fieldName) {
                        case "id" -> id = parser.getText();
                        case "discount" -> discount = Integer.parseInt(parser.getText());
                        case "limit" -> limit = Double.parseDouble(parser.getText());
                    }
                }
            }

        return new PaymentMethod(id, discount, limit);
    }
}