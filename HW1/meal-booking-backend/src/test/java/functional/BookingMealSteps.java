package functional;

import io.cucumber.java.en.*;
import org.openqa.selenium.Alert;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.get(url);
    }

    @When("In restaurant Castro I select the week {int} on day {int}")
    public void iSelectWeekAndDay(int week, int day) {
        WebElement weekButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("week")));
        weekButton.click();

        WebElement dayButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("day")));
        dayButton.click();
    }

    @And("I fill in with email {string}")
    public void iFillInWithEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[type='email']")));
        emailField.sendKeys(email);
    }

    @And("I click on Book meal button")
    public void iClickOnBookMealButton() {
        WebElement bookButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'bg-blue-600') and text()='Book Meal']")));
        bookButton.click();
    }

    @Then("I should see the message {string}")
    public void iShouldSeeTheMessage(String message) {
        Alert bookingAlert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = bookingAlert.getText();

        assertThat(alertText).isEqualTo(message);
        bookingAlert.accept();
    }

    @When("I fill email {string}")
    public void iFillEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[type='email']")));
        emailField.sendKeys(email);

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Search']")));
        searchButton.click();
    }

    @And("I click on my last booking")
    public void iClickOnMyLastBooking() {
        WebElement lastBooking = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".space-y-4 > div:first-child")));
        lastBooking.click();
    }

    @And("I click the cancel button")
    public void iClickTheCancelButton() {
        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Cancel Booking']")));
        cancelButton.click();
    }

    @Then("I should see the message {string}")
    public void iShouldSeeTheCancelMessage(String message) {
        WebElement messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".text-red-700")));
        String actualMessage = messageElement.getText();
        assertThat(actualMessage).isEqualTo(message);
    }
}
