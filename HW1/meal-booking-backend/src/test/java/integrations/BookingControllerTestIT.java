package integrations;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.tqs.MealReservationApplication;
import ua.tqs.model.Booking;
import ua.tqs.model.BookingStatus;
import ua.tqs.repository.BookingRepository;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(classes = MealReservationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class BookingControllerTestIT {

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16")
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("mealreservationTest");

    String BASE_URL;

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private BookingRepository bookingRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @BeforeEach
    void setUp() {
        BASE_URL = "http://localhost:" + randomServerPort + "/api/bookings";
    }

    @Test
    void testCreateBooking() {
        Booking booking = new Booking();
        booking.setDayIndex(3);
        booking.setEmail("test@example.com");
        booking.setRestaurant("Castro");
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setWeek("13");

        RestAssured.given().contentType(ContentType.JSON).body(booking)
                .when().post(BASE_URL)
                .then().statusCode(HttpStatus.CREATED.value())
                .body("dayIndex", equalTo(booking.getDayIndex()))
                .body("email", equalTo(booking.getEmail()))
                .body("restaurant", equalTo(booking.getRestaurant()))
                .body("status", equalTo(booking.getStatus().toString()))
                .body("week", equalTo(booking.getWeek()))
        ;
    }

}
