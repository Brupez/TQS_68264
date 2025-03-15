package org.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.logging.Logger.getLogger;

public class SearchBooksSteps {
    private WebDriver driver;
    static final Logger log = getLogger(String.valueOf(lookup().lookupClass()));


    @When("I enter {string} in the search bar")
    public void iShouldSearch(String bookSearched) {
        log.info("Searching " + bookSearched);
        driver.findElement(
                By.cssSelector("[data-testid='book-search-input']"))
                .sendKeys(bookSearched);
    }


    @When("I click the {string} button")
    public void iClickTheButton(String button) {
        log.info("Clicking " + button);
        driver.findElement(
                By.xpath("/html/body/div/div[2]/div/div[2]/div/span[1]"))
                .click();
    }


    @Then("I should see {string} in the search results")
    public void iShouldSeeInTheSearchResults(String result) {
        log.info("Checking if " + result + " is in the search results");
        // assertThat(result).isEqualTo("Harry Potter and the Sorcerer's Stone");
    }
}
