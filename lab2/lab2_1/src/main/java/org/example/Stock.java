package org.example;

// Stock class
class Stock {
    private String label;
    private int quantity;

    // Constructor
    public Stock(String label, int quantity) {
        this.label = label;
        this.quantity = quantity;
    }

    // Getter and Setter for label
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


