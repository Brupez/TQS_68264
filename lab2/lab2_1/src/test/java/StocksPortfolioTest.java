package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    @Mock
    IStockmarketService mockService;

    @InjectMocks
    StocksPortfolio portfolio;

    @Test
    void testTotalValue() {
        IStockmarketService mockService = mock(IStockmarketService.class);

        StocksPortfolio portfolio = new StocksPortfolio(mockService);

        // Add test stocks.
        Stock stock1 = new Stock("AAPL", 2);
        Stock stock2 = new Stock("GOOG", 3);
        portfolio.addStock(stock1);
        portfolio.addStock(stock2);

        // Load the mock with proper expectations.
        when(mockService.lookUpPrice("AAPL")).thenReturn(150.0);
        when(mockService.lookUpPrice("GOOG")).thenReturn(200.0);

        // Execute the test.
        double expectedTotal = (2 * 150.0) + (3 * 200.0);
        double actualTotal = portfolio.totalValue();

        // Verify the result and the use of the mock.
        assertEquals(expectedTotal, actualTotal, 0.001);
        verify(mockService, times(1)).lookUpPrice("AAPL");
        verify(mockService, times(1)).lookUpPrice("GOOG");
    }

    @Test
    void testMostValuableStocks() {
        IStockmarketService mockService = mock(IStockmarketService.class);
        StocksPortfolio portfolio = new StocksPortfolio(mockService);

        // Add test stocks.
        Stock stock1 = new Stock("X", 2);
        Stock stock2 = new Stock("Y", 3);
        Stock stock3 = new Stock("Z", 6);


        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        portfolio.addStock(stock3);

        when(mockService.lookUpPrice("X")).thenReturn(150.0);
        when(mockService.lookUpPrice("Y")).thenReturn(200.0);
        when(mockService.lookUpPrice("Z")).thenReturn(6.0);


        // Execute the test.
        Stock[] expectedStocks = {stock3, stock2, stock1};
        Stock[] actualStocks = portfolio.mostValuableStocks(3).toArray(new Stock[0]);

        // Verify the result and the use of the mock.
        assertArrayEquals(expectedStocks, actualStocks);
        verify(mockService, times(3)).lookUpPrice(anyString());
    }

    // AI didnt write the correct tests that has 0% coverage

}