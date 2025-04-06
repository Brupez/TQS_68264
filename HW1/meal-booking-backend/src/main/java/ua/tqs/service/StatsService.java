package ua.tqs.service;

import org.springframework.stereotype.Service;

@Service
public class StatsService {
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
    }

    public void incrementCacheMisses() {
        cacheMisses++;
    }
}