package ua.tqs.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
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