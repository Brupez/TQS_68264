import org.example.ISimpleHttpClient;
import org.example.Product;
import org.example.ProductFinderService;
import org.example.TqsBasicHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductFinderServiceIT {

    private ProductFinderService productFinderService;

    @BeforeEach
    void setUp(){
        // uses the real Http get client
        ISimpleHttpClient httpClient = new TqsBasicHttpClient();
        productFinderService = new ProductFinderService(httpClient);
    }

    @DisplayName("Test Http Response with a valid product")
    @Test
    void testHTTPResponse_ValidProduct() throws IOException {
        int productId = 3;

        Optional<Product> product = productFinderService.findProductDetails(productId);

        assertTrue(product.isPresent());
        assertEquals(3, product.get().getId());
        assertEquals("Mens Cotton Jacket", product.get().getTitle());

    }

    @DisplayName("findProductDetails(300) returns no Product")
    @Test
    void testFindProductDetails_NoProduct() throws IOException {
        
        Optional<Product> product = productFinderService.findProductDetails(300);

        assertTrue(product.isEmpty());

    }
}
