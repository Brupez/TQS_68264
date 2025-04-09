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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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


    String baseUrl;

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
        baseUrl = "http://localhost:" + randomServerPort + "/api/bookings";
    }

    @AfterEach
    void tearDown() {
        bookingRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testCreateBooking() {
        Booking booking = new Booking();
        booking.setDayIndex(3);
        booking.setEmail("test@example.com");
        booking.setRestaurant("Castro");
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setWeek("13");

        RestAssured.given().contentType(ContentType.JSON).body(booking)
                .when().post(baseUrl)
                .then().statusCode(HttpStatus.CREATED.value())
                .body("dayIndex", equalTo(booking.getDayIndex()))
                .body("email", equalTo(booking.getEmail()))
                .body("restaurant", equalTo(booking.getRestaurant()))
                .body("status", equalTo(booking.getStatus().toString()))
                .body("week", equalTo(booking.getWeek()));
    }

    @Test
    @Order(2)
    void givenBookings_whenGetBookings_thenStatus200() {
        Booking booking1 = new Booking();
        booking1.setDayIndex(3);
        booking1.setEmail("test@example.com");
        booking1.setRestaurant("Castro");
        booking1.setStatus(BookingStatus.CONFIRMED);
        booking1.setWeek("13");
        booking1.setCreatedAt(LocalDateTime.now());

        Booking booking2 = new Booking();
        booking2.setDayIndex(2);
        booking2.setEmail("test@example.com");
        booking2.setRestaurant("Santiago");
        booking2.setStatus(BookingStatus.CONFIRMED);
        booking2.setWeek("13");
        booking2.setCreatedAt(LocalDateTime.now());

        bookingRepository.saveAll(List.of(booking1, booking2));

        RestAssured.when().get(baseUrl + "?email=" + booking1.getEmail())
                .then().statusCode(HttpStatus.OK.value())
                .body("", hasSize(2))
                .body("dayIndex", hasItems(booking1.getDayIndex(), booking2.getDayIndex()))
                .body("email", hasItems(booking1.getEmail(), booking2.getEmail()))
                .body("restaurant", hasItems(booking1.getRestaurant(), booking2.getRestaurant()))
                .body("status", hasItems(booking1.getStatus().toString(), booking2.getStatus().toString()))
                .body("week", hasItems(booking1.getWeek(), booking2.getWeek()))
        ;
    }

    @Test
    @Order(3)
    void whenGetBookingByInvalidEmail_thenStatus404() {
        RestAssured.when().get(baseUrl + "?email=naoexiste@gmail.com")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Order(4)
    void whenCancelBooking_thenStatus200() {
        Booking booking = new Booking();
        booking.setDayIndex(3);
        booking.setEmail("test@example.com");
        booking.setRestaurant("Castro");
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setWeek("13");
        booking.setCreatedAt(LocalDateTime.now());

        booking = bookingRepository.save(booking);

        RestAssured.when().put(baseUrl + "/" + booking.getId() + "/cancel?email=" + booking.getEmail())
                .then().statusCode(HttpStatus.OK.value());

        Booking found = bookingRepository.findByIdAndEmail(booking.getId(), booking.getEmail())
                .stream().findFirst().orElse(null);
        assertThat(found).isNull();
    }
}
