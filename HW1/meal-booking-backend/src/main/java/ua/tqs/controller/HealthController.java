package ua.tqs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.model.StatsHealth;

@RestController
@RequestMapping("/health")
@Tag(name = "health")
public class HealthController {

    public HealthController() {
    }

    @GetMapping()
    public ResponseEntity<StatsHealth> getHealth() {
        StatsHealth StatsHealth = new StatsHealth("OK"); // Create an instance of Stats
        return new ResponseEntity<>(StatsHealth, HttpStatus.OK);
    }
}
