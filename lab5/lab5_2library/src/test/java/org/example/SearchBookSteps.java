package org.example;

import io.cucumber.core.logging.Logger;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Condition;
import org.junit.platform.commons.logging.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SearchBookSteps {

    private Library library = new Library();
    private List<Book> searchResults;
    /**
     * create a registered type named iso8601Date to map a string pattern from the feature
     * into a custom datatype. Extracted parameters should be strings.
	 */
    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day){
        return Utils.localDateFromDateParts(year, month, day);
    }

    @ParameterType("[0-9]{4}")
    public Date year(String year){
        LocalDate localDate = LocalDate.of(Integer.parseInt(year), 1, 1);
        return Utils.isoTextToLocalDate(localDate);
    }

    /**
     * load a data table from the feature (tabular format) and call this method
     * for each row in the table. Injected parameter is a map with column name --> value
     */
    @DataTableType
    public Book bookEntry(Map<String, String> tableEntry) {
        return new Book(
                tableEntry.get("Title"),
                tableEntry.get("Author"),
                Utils.isoTextToLocalDate(Utils.parsePublish(tableEntry.get("Date"))));
    }

    // only title of the book
    @DataTableType
    public String title(String title) {
        return title;
    }


    @Given("books with:")
    public void theFollowingBooks(List<Book> books) {
        for (Book book : books) {
            library.addBook(book);
        }
    }

    @When("the customer searches for books published between {year} and {year}")
    public void theCustomerSearchesForBooksPublishedBetweenAnd(Date startYear, Date endYear) {
        searchResults = library.findBooks(startYear, endYear);
    }

    @Then("{int} books should have been found")
    public void booksShouldHaveBeenFound(int count) {
        assertThat(searchResults.size()).isEqualTo(count);
    }

    @And("Book {int} should have the title {string}")
    public void bookShouldHaveTheTitleSomeOtherBook(int bookIdx, String title) {
        assertThat(searchResults.get(bookIdx - 1).getTitle().replaceAll("^'|'$", "")).isEqualTo(title);
    }
}
