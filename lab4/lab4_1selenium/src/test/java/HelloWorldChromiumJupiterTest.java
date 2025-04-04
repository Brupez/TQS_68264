import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.chromium.ChromiumDriver;


@ExtendWith(SeleniumJupiter.class)
class HelloWorldChromiumJupiterTest {


    @Test
    void test(ChromiumDriver driver) {
        driver.get("https://blazedemo.com/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");

        driver.findElement(By.linkText("Slow calculator")).click();

        assertThat(driver.getCurrentUrl()).isEqualTo("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
    }

}