import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.RemoteWebDriver;

import static io.github.bonigarcia.seljup.BrowserType.CHROME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class JupiterandDockerTest {

    @Test
    public void testChrome(@DockerBrowser(type = CHROME) RemoteWebDriver driver) {
        driver.get("https://www.ua.pt");
        String title = driver.getTitle();
        System.out.println("Title: " + title);
        assertThat(driver.getTitle().contains("Universidade de Aveiro"));
    }
}
