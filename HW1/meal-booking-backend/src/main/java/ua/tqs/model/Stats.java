package ua.tqs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Stats {
    private int totalRequests;
    private int cacheMisses;
}
