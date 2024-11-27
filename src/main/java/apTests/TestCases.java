
package apTests;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
//Selenium Imports
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
///

public class TestCases {
    WebDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void navigateToHomePage() {
        driver.get("https://www.amazon.in");
    }

    public void showLogs(String log) {
        System.out.println(log);
    }

    public void showResult(String log, Boolean status) {
        if(status) {
            System.out.println(log + ": --> PASS");
        }
        else {
            System.out.println(log + ": --> FAIL");
        }
    }

    public void searchWithKeyword(String productName) {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(productName);
    }

    public void testCase01() {
        this.showLogs("Start Test case: testCase01");
        this.navigateToHomePage();
        this.showResult("The URL is valid", driver.getCurrentUrl().contains("amazon"));
        this.showLogs("End Test case: testCase01");
    }

    public void testCase02() throws InterruptedException {
        this.showLogs("Start Test case: testCase02");
        this.navigateToHomePage();
        String productToSearch = "laptop";
        this.searchWithKeyword(productToSearch);
        Thread.sleep(3000);
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(5000);
        List<WebElement> prodList = driver.findElements(By.xpath("(//span[@class='a-size-medium a-color-base a-text-normal'])"));
        Boolean productFlag = false;
        for(WebElement we : prodList) {
            if(we.getText().toLowerCase().trim().contains(productToSearch)) {
                productFlag = true;
                break;
            }
        }
        this.showResult("The searched product appears in search result", productFlag);
        this.showLogs("End Test case: testCase02");
    }

    public void testCase03() throws InterruptedException {
        this.showLogs("Start Test case: testCase03");
        this.navigateToHomePage();
        driver.findElement(By.xpath("//a[normalize-space()='Electronics']")).click();
        Thread.sleep(5000);
        String url = driver.getCurrentUrl();
        Boolean status = url.trim().toLowerCase().contains("electronics");
        this.showResult("The product category URL is valid", status);
        this.showLogs("End Test case: testCase03");
    }

}
