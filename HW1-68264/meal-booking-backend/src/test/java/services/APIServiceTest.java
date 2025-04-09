package services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import ua.tqs.service.APIService;
import ua.tqs.service.StatsService;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class APIServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private StatsService statsService;

    private APIService apiService;

    @BeforeEach
    void setUp() {
        apiService = new APIService(restTemplate, statsService);

        apiService.setApiKey("5960910cf077b066db15640022ad2b32");
        apiService.setApiUrl("https://api.openweathermap.org/data/2.5");

    }

    @AfterEach
    void tearDown() {
        reset(restTemplate, statsService);
    }

    @Test
    void whenGetWeather_thenReturnWeatherData() {
        String city = "Aveiro";
        Map<String, Object> mockWeather = new HashMap<>();
        mockWeather.put("main", Map.of(
                "temp", 20.5,
                "humidity", 65
        ));

        when(restTemplate.getForObject(contains("/forecast?q=" + city), eq(Map.class)))
                .thenReturn(mockWeather);

        Map<String, Object> result = apiService.getWeather(city);

        assertThat(result).isNotNull().isEqualTo(mockWeather);
        verify(statsService).incrementTotalRequests();
        verify(statsService, never()).incrementCacheMisses();
    }

    @Test
    void whenGetWeatherFails_thenReturnEmptyMap() {
        String city = "Oreiva";
        when(restTemplate.getForObject(contains("/forecast?q=" + city), eq(Map.class)))
                .thenReturn(null);

        Map<String, Object> result = apiService.getWeather(city);

        assertThat(result).isInstanceOf(Map.class);
        verify(statsService).incrementTotalRequests();
        verify(statsService).incrementCacheMisses();
    }
}
