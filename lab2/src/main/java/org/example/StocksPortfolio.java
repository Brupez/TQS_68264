package org.example;

import java.util.ArrayList;
import java.util.List;

// StocksPortfolio class
class StocksPortfolio {
    private IStockmarketService stockmarket;
    private List<Stock> stocks;

    // Constructor
    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue() {
        double value = 0.0;
        for (Stock stock : stocks) {
            value += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return value;
    }
}
