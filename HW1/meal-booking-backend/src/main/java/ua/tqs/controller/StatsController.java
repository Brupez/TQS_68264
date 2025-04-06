package ua.tqs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.model.Stats;
import ua.tqs.service.StatsService;

@RestController
@RequestMapping("/api/stats")
@Tag(name = "Stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping()
    @Operation(summary = "Get stats")
    public ResponseEntity<Stats> getStats() {
        int totalRequests = statsService.getTotalRequests();
        int cacheMisses = statsService.getCacheMisses();
        Stats stats = new Stats(totalRequests, cacheMisses);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

}
