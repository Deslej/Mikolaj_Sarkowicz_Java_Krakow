package org.example.mapper;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.example.Exception.FileNotOrderJsonException;
import org.example.model.Order;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static List<Order> jsonToOrder(String path)   {
        List<Order> orders = new ArrayList<>();
        JsonFactory factory = new JsonFactory();

        if (!path.endsWith("orders.json")) {
            throw new FileNotOrderJsonException("Invalid file: expected 'orders.json' but received: " + path);
        }


        try(FileInputStream fis = new FileInputStream(path);
            JsonParser parser = factory.createParser(fis)) {

            parser.nextToken();

            while (parser.nextToken() == JsonToken.START_OBJECT) {
                Order order = parseOrder(parser);
                orders.add(order);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading or parsing the file: " + path, e);
        } catch (FileNotOrderJsonException e) {
            throw e;
        }

        return orders;
    }

    private static Order parseOrder(JsonParser parser)throws IOException  {
        String id = null;
        double value = 0.0;
        List<String> promotions = new ArrayList<>();
        promotions.add("PUNKTY");

            while (parser.nextToken() != JsonToken.END_OBJECT) {

                if (parser.getCurrentToken() == JsonToken.FIELD_NAME) {

                    String fieldName = parser.getText();
                    parser.nextToken();

                    switch (fieldName) {
                        case "id" -> id = parser.getText();
                        case "value" -> value = Double.parseDouble(parser.getText());
                        case "promotions" -> parsePromotions(parser, promotions);
                    }
                }
            }
        return new Order(id, value, promotions);
    }

    private static void parsePromotions(JsonParser parser,List<String> promotions) throws IOException {
        if (parser.getCurrentToken() == JsonToken.START_ARRAY) {
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                promotions.add(parser.getText());
            }
        }
    }
}
