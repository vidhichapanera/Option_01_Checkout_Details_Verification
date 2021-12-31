package Config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver driver;

    public BaseTest() {
    }

    public BaseTest(WebDriver driver) {
        this.driver = driver;
    }


    /**
     *
     * @param browserName
     * @param baseURL
     * @throws Exception
     */
    @Parameters({"browserName","baseURL"})
    @BeforeClass(alwaysRun = true)
    public synchronized void launchBrowser(@Optional("chrome") String browserName, @Optional("https://accounts.google.com/signin")String baseURL) throws Exception {
        this.getDriver(browserName);
        this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        this.driver.get(baseURL);

    }


    /**
     *
     * @throws Exception
     */
    @AfterClass(alwaysRun = true)
    public void afterclass() throws Exception {
        this.driver.quit();
    }

    /**
     *
     * @param browserName
     * @return
     */
    public synchronized WebDriver getDriver(String browserName) {

        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\driver\\Chrome\\chromedriver.exe");//Version 96.0.4664.45
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            this.driver = new ChromeDriver(options);
        }else{
            System.out.println("Kindly Please Provide Valid Browser Name.........../");
        }
        return this.driver;
    }


}
