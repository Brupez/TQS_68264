package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;

public class ProductFinderService{

    private static final String API_Products = "https://fakestoreapi.com/products/";
    private final ISimpleHttpClient httpClient;

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Product> findProductDetails(int id) throws IOException {

        String jsonResponse = httpClient.doHttpGet(API_Products + id);
        if (jsonResponse.isEmpty()) {
            return Optional.empty();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            int productId = jsonNode.get("id").asInt();
            String title = jsonNode.get("title").asText();
            double price = jsonNode.get("price").asDouble();
            String description = jsonNode.get("description").asText();
            String category = jsonNode.get("category").asText();
            String image = jsonNode.get("image").asText();
            Product product = new Product(productId, image, price, title, description, category);
            return Optional.of(product);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
