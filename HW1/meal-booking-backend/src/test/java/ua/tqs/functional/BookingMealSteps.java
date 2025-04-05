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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BookingMealSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I navigate to {string}")
    public void iNavigateTo(String url) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(url);
    }

    @When("In restaurant Castro I select the week {int} on day {int}")
    public void iSelectWeekAndDay(int week, int day) {
        WebElement weekButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("week" + week)));
        weekButton.click();

        WebElement dayButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("day" + day)));
        dayButton.click();
    }

    @And("I fill in with email {string}")
    public void iFillInWithEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        emailField.sendKeys(email);
    }

    @And("I click on Book meal button")
    public void iClickOnBookMealButton() {
        WebElement bookButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("book")));
        bookButton.click();
    }

    @Then("I should see the message {string}")
    public void iShouldSeeTheMessage(String message) {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        assertThat(messageElement.getText()).isEqualTo(message);
    }

}
