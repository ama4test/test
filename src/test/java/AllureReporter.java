import io.qameta.allure.Attachment;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class AllureReporter {


    @Test
    @Link()
    public void testForSum(){
        this.attachString("я текст");

        checkSumStep(3, 2, 5);
        checkSumStep(5, 4, 8);

    }


    @Step()
    public static void checkSumStep(int num1, int num2, int expectedSum) {
        Assert.assertTrue( num1 + num2 == expectedSum, "Сумма слагаемых не соответствует ожидаемому значению");
    }


    @Attachment()
    public static byte[] attachString(String attachString){
        return attachString.getBytes();
    }

    @Attachment()
    public static byte[] attachBrowserScreenShot(WebDriver driver){
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Test
    public void mobileDriver() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        driver.get("https://www.google.com.ua");
        this.attachBrowserScreenShot(driver);
        driver.quit();
    }

}
