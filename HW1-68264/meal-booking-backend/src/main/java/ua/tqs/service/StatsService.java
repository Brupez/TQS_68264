package ua.tqs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private static final Logger log = LoggerFactory.getLogger(StatsService.class);
    private int totalRequests = 0;
    private int cacheMisses = 0;

    public int getTotalRequests() {
        return totalRequests;
    }

    public int getCacheMisses() {
        return cacheMisses;
    }

    public void incrementTotalRequests() {
        totalRequests++;
        log.info("Total requests: {}", totalRequests);
    }

    public void incrementCacheMisses() {
        cacheMisses++;
        log.info("Cache misses: {}", cacheMisses);
    }

    public void resetStats() {
        totalRequests = 0;
        cacheMisses = 0;
        log.info("Stats reset");
    }
}