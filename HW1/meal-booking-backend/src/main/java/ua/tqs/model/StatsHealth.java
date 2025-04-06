package ua.tqs.model; // Ensure this matches your package structure

public class StatsHealth {
    private String status;

    // Constructor
    public StatsHealth(String status) {
        this.status = status;
    }

    // Getter
    public String getStatus() {
        return status;
    }

    // Optionally, you can add a setter if you need to modify the status later
    public void setStatus(String status) {
        this.status = status;
    }
}