package services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import ua.tqs.MealReservationApplication;
import ua.tqs.service.StatsService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = MealReservationApplication.class)
class StatsServiceTest {
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

    @Test
    void whenResetStats_thenStatsAreReset() {
        statsService.incrementTotalRequests();
        statsService.incrementCacheMisses();

        statsService.resetStats();

        assertThat(statsService.getTotalRequests()).isZero();
        assertThat(statsService.getCacheMisses()).isZero();
    }
}
