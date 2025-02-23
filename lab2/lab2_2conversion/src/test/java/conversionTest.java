import org.example.ISimpleHttpClient;
import org.example.Product;
import org.example.ProductFinderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class conversionTest {

    @Mock
    private ISimpleHttpClient httpClient;

    @InjectMocks
    private ProductFinderService productFinderService;

    @BeforeEach
    void setUp(){
        httpClient = mock(ISimpleHttpClient.class);
        productFinderService = new ProductFinderService(httpClient);
    }

    @Test
    void testHTTPResponse_ValidProduct() throws IOException {
        int productId = 3;
        String jsonResponse = """
            {
                "id": 3,
                "title": "Mens Cotton Jacket",
                "price": 55.99,
                "description": "great outerwear jackets for Spring/Autumn/Winter...",
                "category": "men's clothing",
                "image": "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
                "rating": {
                    "rate": 4.7,
                    "count": 500
                }
            }
        """;

        when(httpClient.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(jsonResponse);

        Optional<Product> product = productFinderService.findProductDetails(productId);

        assertTrue(product.isPresent());
        assertEquals(3, product.get().getId());
        assertEquals("Mens Cotton Jacket", product.get().getTitle());

        // verify mock interactions
        verify(httpClient, times(1)).doHttpGet("https://fakestoreapi.com/products/3");
    }

    @DisplayName("findProductDetails(300) returns no Product")
    @Test
    void testFindProductDetails_NoProduct() throws IOException {

        when(httpClient.doHttpGet("https://fakestoreapi.com/products/300")).thenReturn("");

        Optional<Product> product = productFinderService.findProductDetails(300);

        assertTrue(product.isEmpty());

        verify(httpClient, times(1)).doHttpGet("https://fakestoreapi.com/products/300");
    }
}