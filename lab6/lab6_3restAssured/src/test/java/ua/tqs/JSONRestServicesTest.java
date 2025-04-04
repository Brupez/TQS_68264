package ua.tqs;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;


public class JSONRestServicesTest {

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        get("https://jsonplaceholder.typicode.com/todos").then().statusCode(200).assertThat();
    }

    @Test
    public void givenUrl_whenJsonResponseHasArrayWithGivenValuesUnderKey_thenCorrect() {
        get("https://jsonplaceholder.typicode.com/todos/4").then().assertThat()
                .body("title", equalTo("et porro tempora"));
    }

    @Test
    public void givenUrl_getIds() {
        get("https://jsonplaceholder.typicode.com/todos").then().statusCode(200).assertThat()
            .body("id", hasItems(198, 199));
    }

    @Test
    public void givenUrl_quickResults() {
        get("https://jsonplaceholder.typicode.com/todos").then().statusCode(200).assertThat()
                .time(
                        lessThan(2000L)
                );
    }
}

//{
//        "id": "390",
//        "data": {
    //        "leagueId": 35,
    //        "homeTeam": "Norway",
    //        "visitingTeam": "England",
//        },
//        "odds": [{
    //        "price": "1.30",
    //        "name": "1"
//        },
//        {
    //        "price": "5.25",
    //        "name": "X"
//        }]
//        }
