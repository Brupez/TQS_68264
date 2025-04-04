package ua.tqs.functional;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BookingMealSteps {

    private WebDriver driver = new ChromeDriver();
    private WebDriverWait wait;

    @Given("I navigate to {string}")
    public void iNavigateTo(String url) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);
    }

    @When("I select a table at Castro for week {int} on day {int}")
    public void iBookATableAtCastroForWeekOnDay(int week, int day) {

        WebElement weekButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("week" + week)));
        weekButton.click();

        WebElement dayButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("day" + day)));
        dayButton.click();

    }

    @And("I fill in with email {string}")
    public void iFillInWithEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        emailField.sendKeys(email);

        WebElement bookButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("book")));
        bookButton.click();
    }

    @Then("My booking should be {string}")
    public void myBookingShouldBe(String arg0) {
    }


//    @Then("My booking should be {string}")
//    public void myBookingShouldBe(String state) {
//        List<WebElement> elements = driver.findElements(By.className("SearchList_bookTitle"));
//        for (WebElement element : elements) {
//            assertThat(element.getText()).contains(state);
//        }
//    }
}
