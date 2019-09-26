import helper.ScreenshotHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ScreenCompare {


    @Test
    public void screenCompare() {
        Allure.label("testType", "screenshotDiff");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        driver.get("https://www.seleniumhq.org/");
        WebElement el = driver.findElement(By.id("choice"));
        AShot aShot = new AShot();
        BufferedImage actual = aShot.takeScreenshot(driver, el).getImage();
        BufferedImage expected = this.getBufferedImageFromFile("src/main/resources/screen.png");
        ImageDiff diffImage = new ImageDiffer().makeDiff(actual, expected);
        int difSize = diffImage.getDiffSize();
        BufferedImage diff = diffImage.getMarkedImage(); // comparison result with marked differences
        atttAchScreenshatToAllureReport(actual, "actual");
        atttAchScreenshatToAllureReport(expected, "expected");
        atttAchScreenshatToAllureReport(diff, "diff");
        Assert.assertTrue(difSize < 1);
        driver.quit();
    }


    @Attachment(value = "{filename}", type = "image/png")
    public byte[] atttAchScreenshatToAllureReport(BufferedImage screenshot, String filename) {
        byte[] imageInByte = new byte[0];

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imageInByte;
    }

    public BufferedImage getBufferedImageFromFile(String fullPath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
