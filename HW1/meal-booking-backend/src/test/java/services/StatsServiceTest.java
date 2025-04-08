package services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tqs.service.StatsService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {
    @InjectMocks
    private StatsService statsService;

    @Test
    void whenIncreaseTotalRequests_thenTotalRequestsIsIncreased() {
        statsService.incrementTotalRequests();
        assertThat(statsService.getTotalRequests()).isEqualTo(1);
    }

    @Test
    void whenIncreaseCacheMisses_thenCacheMissesIsIncreased() {
        statsService.incrementCacheMisses();
        assertThat(statsService.getCacheMisses()).isEqualTo(1);
    }
}
