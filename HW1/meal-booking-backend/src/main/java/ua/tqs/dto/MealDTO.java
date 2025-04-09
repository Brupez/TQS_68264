package ua.tqs.dto;


public class MealDTO {
    private Long id;
    private String name;
    private String description;
    private String restaurantName;
    private int temperature;
    private String weatherCondition;
    private int humidity;

    public MealDTO() {}

    public MealDTO(Long id, String name, String description, 
                  String restaurantName, int temperature, String weatherCondition, int humidity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.restaurantName = restaurantName;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.humidity = humidity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}