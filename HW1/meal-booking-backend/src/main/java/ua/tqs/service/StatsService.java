package ua.tqs.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class StatsService {
    private int totalRequests = 0;
    private int cacheMisses = 0;

    public void incrementTotalRequests() {
        totalRequests++;
    }

    public void incrementCacheMisses() {
        cacheMisses++;
    }
}
