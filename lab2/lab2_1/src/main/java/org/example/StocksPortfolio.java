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

    /**
     * @param topN the number of most valuable stocks to return
     * @return a list with the topN most valuable stocks in the portfolio
     */
    public List<Stock> mostValuableStocks(int topN) {
        List<Stock> mostValuableStocks = new ArrayList<>();
        List<Stock> stocksCopy = new ArrayList<>(stocks);

        for (int i = 0; i < topN; i++) {
            Stock mostValuableStock = stocksCopy.get(0);
            for (Stock stock : stocksCopy) {
                if (stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity() >
                        stockmarket.lookUpPrice(mostValuableStock.getLabel()) * mostValuableStock.getQuantity()) {
                    mostValuableStock = stock;
                }
            }
            mostValuableStocks.add(mostValuableStock);
            stocksCopy.remove(mostValuableStock);
        }
        return mostValuableStocks;
    }
}
