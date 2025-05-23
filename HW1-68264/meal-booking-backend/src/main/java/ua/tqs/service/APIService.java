package ua.tqs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@EnableCaching
public class APIService {
    private static final Logger logger = LoggerFactory.getLogger(APIService.class);

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final StatsService statsService;

    public APIService(RestTemplate restTemplate, StatsService statsService) {
        this.restTemplate = restTemplate;
        this.statsService = statsService;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Cacheable("weathers")
    public Map<String, Object> getWeather(String city) {
        try {
            logger.info("Get weather for city: {}", city);
            statsService.incrementTotalRequests();
            String url = String.format("%s/forecast?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null) {
                return response;
            }
            statsService.incrementCacheMisses();
            return new HashMap<>();

        } catch (Exception e) {
            logger.error("Error fetching weather data: {}", e.getMessage());
            statsService.incrementCacheMisses();
            return new HashMap<>();
        }
    }
}