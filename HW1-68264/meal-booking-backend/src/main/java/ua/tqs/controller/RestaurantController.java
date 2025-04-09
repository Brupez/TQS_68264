package ua.tqs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.dto.MealDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import ua.tqs.service.APIService;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurant", description = "Restaurant API")
public class RestaurantController {
    private final APIService apiService;

    @Autowired
    public RestaurantController(APIService apiService) {
        this.apiService = apiService;
    }

    private final Map<String, Map<Integer, String>> restaurantMenus = Map.of(
            "Castro", Map.of(
                    0, "Soup: Creme de Legumes, Meat: Frango no Churrasco, Fish: Bacalhau à Brás",
                    1, "Soup: Caldo Verde, Meat: Bitoque com Ovo, Fish: Dourada Grelhada",
                    2, "Soup: Sopa de Feijão, Meat: Cozido à Portuguesa, Fish: Polvo à Lagareiro",
                    3, "Soup: Sopa de Peixe, Meat: Febras Grelhadas, Fish: Salmão Grelhado",
                    4, "Soup: Sopa de Cenoura, Meat: Picanha na Brasa, Fish: Robalo ao Sal"
            ),
            "Santiago", Map.of(
                    0, "Soup: Sopa da Pedra, Meat: Francesinha, Fish: Arroz de Marisco",
                    1, "Soup: Sopa de Tomate, Meat: Lombinho de Porco, Fish: Filetes de Pescada",
                    2, "Soup: Sopa de Espinafres, Meat: Arroz de Pato, Fish: Bacalhau com Natas",
                    3, "Soup: Canja de Galinha, Meat: Costoletas de Borrego, Fish: Lulas Grelhadas",
                    4, "Soup: Sopa de Abóbora, Meat: Feijoada à Transmontana, Fish: Carapau Grelhado"
            )
    );

    @Operation(summary = "Get meals for a specific restaurant and week number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meals retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Meals not found"),
    })
    @GetMapping("/{name}/{weekNumber}/meals")
    public ResponseEntity<Map<String, List<MealDTO>>> getMeals(
            @PathVariable String name,
            @PathVariable String weekNumber) {

        if (!restaurantMenus.containsKey(name)) {
            return ResponseEntity.notFound().build();
        }

        List<MealDTO> dailyMeals = new ArrayList<>();
        String city = "Aveiro";
        Map<String, Object> forecastData = apiService.getWeather(city);

        if (forecastData != null) {
            List<Map<String, Object>> forecasts = (List<Map<String, Object>>) forecastData.get("list");

            for (int day = 0; day < 5; day++) {
                MealDTO meal = new MealDTO();
                meal.setId(Integer.toUnsignedLong(day));
                meal.setName("Daily Menu");
                meal.setDescription(restaurantMenus.get(name).get(day));
                LocalDate date = LocalDate.now().plusDays(day);
                meal.setRestaurantName(name);

                // Get forecast for this specific day (at noon)
                String targetDate = date.toString();
                Map<String, Object> dayForecast = forecasts.stream()
                        .filter(f -> ((String) f.get("dt_txt")).startsWith(targetDate))
                        .findFirst()
                        .orElse(null);

                if (dayForecast != null) {
                    Map<String, Object> main = (Map<String, Object>) dayForecast.get("main");
                    List<Map<String, Object>> weather = (List<Map<String, Object>>) dayForecast.get("weather");

                    if (main != null && weather != null && !weather.isEmpty()) {
                        meal.setTemperature(((Double) main.get("temp")).intValue());
                        meal.setHumidity((Integer) main.get("humidity"));
                        meal.setWeatherCondition((String) weather.get(0).get("main"));
                    }
                }

                dailyMeals.add(meal);
            }
        }

        Map<String, List<MealDTO>> response = new HashMap<>();
        response.put(weekNumber, dailyMeals);

        return ResponseEntity.ok(response);
    }
}