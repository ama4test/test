package helper;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotHelper {


    public static String saveBufferedImageToDisk(String path, String filename, BufferedImage image) {
        String fullPath = (path + filename);
        File outputfile = new File(fullPath);
        outputfile.mkdirs();
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullPath;
    }


    public static BufferedImage getBufferedImageFromFile(String fullPath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


}
