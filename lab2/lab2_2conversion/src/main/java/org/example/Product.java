package org.example;

public class Product {

    private Integer id;
    private String image;
    private String description;
    private Double price;
    private String title;
    private String category;

    public Product() {
    }

    public Product(Integer id, String image, Double price, String title, String description, String category) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.title = title;
        this.description = description;
        this.category = category;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
