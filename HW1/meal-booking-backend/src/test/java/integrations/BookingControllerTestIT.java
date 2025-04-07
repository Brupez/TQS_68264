package integrations;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
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

import java.util.Arrays;

import static org.hamcrest.Matchers.*;


@SpringBootTest(classes = MealReservationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @AfterEach
    void tearDown() {
        bookingRepository.deleteAll();
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
                .body("createdAt", equalTo(booking.getCreatedAt()));
    }

    @Test
    void whenGetBookings_thenStatus200() {
        Booking booking1 = new Booking();
        booking1.setDayIndex(3);
        booking1.setEmail("test@example.com");
        booking1.setRestaurant("Castro");
        booking1.setStatus(BookingStatus.CONFIRMED);
        booking1.setWeek("13");

        Booking booking2 = new Booking();
        booking2.setDayIndex(4);
        booking2.setEmail("test@example.com");
        booking2.setRestaurant("Santiago");
        booking2.setStatus(BookingStatus.CONFIRMED);
        booking2.setWeek("13");

        bookingRepository.saveAll(Arrays.asList(booking1, booking2));


        RestAssured.when().get(BASE_URL)
                .then().statusCode(HttpStatus.OK.value())
                .body("", hasSize(2))
                .body("dayIndex", hasItems(booking1.getDayIndex(), booking2.getDayIndex()))
                .body("email", hasItems(booking1.getEmail(), booking2.getEmail()))
                .body("restaurant", hasItems(booking1.getRestaurant(), booking2.getRestaurant()))
                .body("status", hasItems(booking1.getStatus().toString(), booking2.getStatus().toString()))
                .body("week", hasItems(booking1.getWeek(), booking2.getWeek()))
                .body("createdAt", hasItems(booking1.getCreatedAt(), booking2.getCreatedAt()));
    }

}
