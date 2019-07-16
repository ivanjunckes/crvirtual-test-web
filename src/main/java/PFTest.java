import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.logging.Level;

public class PFTest {

    public static WebDriver getDriver(String name) {
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        if (name.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.silentOutput", "true");
            System.setProperty("webdriver.chrome.driver", "/home/ivan/tmp/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            return new ChromeDriver(options);
        }
        if (name.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "/home/ivan/tmp/geckodriver");
            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");

            FirefoxBinary firefoxBinary = new FirefoxBinary();
            firefoxBinary.addCommandLineOptions("--headless");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            return new FirefoxDriver(firefoxOptions);
        }
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        // TomEE
        testOpeningBrowserOnce("https://services.cremesc.org.br:8181/crvirtual-pessoafisica-web/login.html#/login", "firefox", 100);
        testOpeningBrowserMultipleTimes("https://services.cremesc.org.br:8181/crvirtual-pessoafisica-web/login.html#/login", "firefox", 20);
        testOpeningBrowserOnce("https://services.cremesc.org.br:8181/crvirtual-pessoafisica-web/login.html#/login", "chrome", 100);
        testOpeningBrowserMultipleTimes("https://services.cremesc.org.br:8181/crvirtual-pessoafisica-web/login.html#/login", "chrome", 20);

        // TAG 1.8
        testOpeningBrowserOnce("https://api.cremesc.org.br/crvirtual-pessoafisica-web/login.html#/login", "firefox", 100);
        testOpeningBrowserMultipleTimes("https://api.cremesc.org.br/crvirtual-pessoafisica-web/login.html#/login", "firefox", 20);
        testOpeningBrowserOnce("https://api.cremesc.org.br/crvirtual-pessoafisica-web/login.html#/login", "chrome", 100);
        testOpeningBrowserMultipleTimes("https://api.cremesc.org.br/crvirtual-pessoafisica-web/login.html#/login", "chrome", 20);

        // TAG 1.4
        testOpeningBrowserOnce("https://desenvolvimento.cremesc.org.br/crvirtual-pessoafisica-web/login.html#/login", "firefox", 100);
        testOpeningBrowserMultipleTimes("https://desenvolvimento.cremesc.org.br/crvirtual-pessoafisica-web/login.html#/login", "firefox", 20);
        testOpeningBrowserOnce("https://desenvolvimento.cremesc.org.br/crvirtual-pessoafisica-web/login.html#/login", "chrome", 100);
        testOpeningBrowserMultipleTimes("https://desenvolvimento.cremesc.org.br/crvirtual-pessoafisica-web/login.html#/login", "chrome", 20);
    }

    private static String testOpeningBrowserMultipleTimes(String baseUrl, String driverName, int numberOfRequests) throws InterruptedException {
        WebDriver driver = null;
        int failed = 0;
        for (int i = 0; i < numberOfRequests; i++) {
            driver = getDriver(driverName);
            driver.get(baseUrl);
            WebElement loginUser = null;
            boolean found = false;
            for (int j = 0; j < 5; j++) {
                try {
                    loginUser = driver.findElement(By.id("login_user"));

                    if (loginUser != null && loginUser.isDisplayed()) {
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    if (j == 4) {
                        break;
                    }
                    Thread.sleep(1000);
                }
            }

            if (found == false) {
                failed++;
            }
            driver.close();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("URL: " + baseUrl + "\n");
        sb.append("Tests executed: " + numberOfRequests + "\n");
        sb.append("Tests failed: " + failed + "\n");
        sb.append("Browser " + driverName + "\n");
        sb.append("Test: testOpeningBrowserMultipleTimes");
        System.out.println(sb.toString());
        System.out.println("---------------------------------------------");
        return sb.toString();
    }

    private static String testOpeningBrowserOnce(String baseUrl, String driverName, int total) throws InterruptedException {
        WebDriver driver = null;
        int failed = 0;
        driver = getDriver(driverName);
        for (int i = 0; i < total; i++) {

            driver.get(baseUrl);

            WebElement loginUser = null;
            boolean found = false;
            for (int j = 0; j < 5; j++) {
                try {
                    loginUser = driver.findElement(By.id("login_user"));

                    if (loginUser != null && loginUser.isDisplayed()) {
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    if (j == 4) {
                        break;
                    }
                    Thread.sleep(2000);
                }
            }

            if (found == false) {
                failed++;
            }
        }
        driver.close();
        StringBuilder sb = new StringBuilder();
        sb.append("URL: " + baseUrl + "\n");
        sb.append("Tests executed: " + total + "\n");
        sb.append("Tests failed: " + failed + "\n");
        sb.append("Browser " + driverName + "\n");
        sb.append("Test: testOpeningBrowserOnce");
        System.out.println(sb.toString());
        System.out.println("---------------------------------------------");

        return sb.toString();
    }
}
