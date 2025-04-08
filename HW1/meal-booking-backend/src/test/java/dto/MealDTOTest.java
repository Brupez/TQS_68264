package dto;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tqs.dto.MealDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MealDTOTest {
    private static final Logger logger = LoggerFactory.getLogger(MealDTOTest.class);

    @Test
    void whenConvertMealEntityToDTO_thenCorrect() {
        logger.debug("Testing conversion from Meal entity to DTO");

        LocalDate date = LocalDate.now();
        MealDTO mealDTO = new MealDTO(
                1L,
                "Pasta",
                "Fresh pasta",
                date,
                "Castro",
                25,
                "Sunny",
                60);

        logger.debug("Verifying DTO fields match entity");
        assertThat(mealDTO.getId()).isEqualTo(1L);
        assertThat(mealDTO.getName()).isEqualTo("Pasta");
        assertThat(mealDTO.getDescription()).isEqualTo("Fresh pasta");
        assertThat(mealDTO.getDate()).isEqualTo(date);
        assertThat(mealDTO.getRestaurantName()).isEqualTo("Castro");
        assertThat(mealDTO.getTemperature()).isEqualTo(25);
        assertThat(mealDTO.getWeatherCondition()).isEqualTo("Sunny");
        assertThat(mealDTO.getHumidity()).isEqualTo(60);
    }
    @Test
    void whenUseSettersAndGetters_thenCorrect() {
        logger.debug("Testing DTO setters and getters");

        LocalDate date = LocalDate.now();
        MealDTO mealDTO = new MealDTO();
        mealDTO.setId(1L);
        mealDTO.setName("Salad");
        mealDTO.setDescription("Fresh garden salad");
        mealDTO.setDate(date);
        mealDTO.setRestaurantName("Castro");
        mealDTO.setTemperature(22);
        mealDTO.setWeatherCondition("Cloudy");
        mealDTO.setHumidity(70);

        logger.debug("Verifying getter methods");
        assertThat(mealDTO.getId()).isEqualTo(1L);
        assertThat(mealDTO.getName()).isEqualTo("Salad");
        assertThat(mealDTO.getDescription()).isEqualTo("Fresh garden salad");
        assertThat(mealDTO.getDate()).isEqualTo(date);
        assertThat(mealDTO.getRestaurantName()).isEqualTo("Castro");
        assertThat(mealDTO.getTemperature()).isEqualTo(22);
        assertThat(mealDTO.getWeatherCondition()).isEqualTo("Cloudy");
        assertThat(mealDTO.getHumidity()).isEqualTo(70);
    }
 }