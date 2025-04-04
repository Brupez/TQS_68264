package ua.tqs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {
    // Getters and setters - these are crucial for JSON serialization
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private String restaurantName;

    // Weather information
    private int temperature;
    private String weatherCondition;
    private int humidity;

}