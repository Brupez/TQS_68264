package org.example;

import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BookSearchSteps {

    private final WebDriver driver = new ChromeDriver();

    @Given("I am on the library's home page")
    public void iAmOnTheLibrarySHomePage() {
        driver.get("https://cover-bookstore.onrender.com/");
    }

    @When("I search for {string}")
    public void search_for(String query) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".Navbar_searchBarContainer__3UbnF > div:nth-child(1) > input:nth-child(1)")));
        searchBar.sendKeys(query);

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".Navbar_searchBarContainer__3UbnF > div:nth-child(1) > button:nth-child(2) > img:nth-child(1)")));
        button.click();
    }

    @Then("I should see search results containing {string}")
    public void iShouldSeeSearchResultsContaining(String title) {
        List<WebElement> elements = driver.findElements(By.className("SearchList_bookTitle"));
        for (WebElement element : elements) {
            assertThat(element.getText()).contains(title);
        }
    }

    @Then("I should see search results containing books by {string}")
    public void iShouldSeeSearchResultsContainingBooksBy(String author) {
        List<WebElement> elements = driver.findElements(By.className("SearchList_bookAuthor"));
        for (WebElement element : elements) {
            assertThat(element.getText()).contains(author);
        }
    }

    @Then("I should see a message indicating no results found")
    public void iShouldSeeAMessageIndicatingNoResultsFound() {
        WebElement noResults = driver.findElement(By.cssSelector(".SearchList_bookListContainer__3MG7n"));
        assertThat(noResults.isDisplayed()).isTrue();
    }


    @After()
    public void closeBrowser() {
        driver.quit();
    }
}