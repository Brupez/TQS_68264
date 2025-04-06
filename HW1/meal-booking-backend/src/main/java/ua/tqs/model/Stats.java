package ua.tqs.model;

public class Stats {
    private int totalRequests;
    private int cacheMisses;

    public Stats() {
        this.totalRequests = 0;
        this.cacheMisses = 0;
    }

    public Stats(int totalRequests, int cacheMisses) {
        this.totalRequests = totalRequests;
        this.cacheMisses = cacheMisses;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }

    public int getCacheMisses() {
        return cacheMisses;
    }

    public void setCacheMisses(int cacheMisses) {
        this.cacheMisses = cacheMisses;
    }
}