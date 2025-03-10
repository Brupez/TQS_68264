import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class HarryPotterSearchTest {

    private final ChromiumDriver driver;
    private final WebDriverWait wait;

    public HarryPotterSearchTest(ChromiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void HarryPotterSearch() {
        driver.get("https://cover-bookstore.onrender.com/");

        // search for "Harry Potter"
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-testid='book-search-input']")));
                searchInput.sendKeys("Harry Potter");
        searchInput.sendKeys(Keys.RETURN);

        WebElement bookTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div[2]/div/div[2]/div/span[1]")));

        assertThat(bookTitle.getText()).isEqualTo("Harry Potter and the Sorcerer's Stone");
    }
}

//
