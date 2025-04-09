package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.tqs.model.Stats;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Stats.class)
class StatsTest {
    private static final Logger logger = LoggerFactory.getLogger(StatsTest.class);

    private Stats stats;

    @BeforeEach
    void setUp() {
        logger.debug("Setting up StatsTest");
        stats = new Stats();
    }

    @Test
    void whenDefaultConstructor_thenStatsAreZero() {
        logger.debug("Testing default constructor initialization");
        assertThat(stats.getTotalRequests()).isZero();
        assertThat(stats.getCacheMisses()).isZero();
    }

    @Test
    void whenParameterizedConstructor_thenStatsAreSet() {
        logger.debug("Testing parameterized constructor");
        stats = new Stats(5, 3);

        assertThat(stats.getTotalRequests()).isEqualTo(5);
        assertThat(stats.getCacheMisses()).isEqualTo(3);
    }

    @Test
    void whenSetTotalRequests_thenValueIsUpdated() {
        logger.debug("Testing total requests setter");
        stats.setTotalRequests(10);

        assertThat(stats.getTotalRequests()).isEqualTo(10);
    }

    @Test
    void whenSetCacheMisses_thenValueIsUpdated() {
        logger.debug("Testing cache misses setter");
        stats.setCacheMisses(7);

        assertThat(stats.getCacheMisses()).isEqualTo(7);
    }
}
