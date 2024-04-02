import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class MyJunitAutomation {
    WebDriver driver ;
    @BeforeAll
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @Test
    public void writeSomething(){
        driver.get("https://demoqa.com/text-box");
        driver.findElement(By.id("userName")).sendKeys("Amit Roy");
        driver.findElement(By.id("userEmail")).sendKeys("amitroy017852@gmail.com");
        driver.findElements(By.tagName("textarea")).get(0).sendKeys("Banani");
        driver.findElements(By.tagName("textarea")).get(1).sendKeys("Dhaka");
        Utils.scroll(driver,0,500);
        driver.findElement(By.id("submit")).click();
        String nameActual = driver.findElement(By.id("name")).getText();
        String nameExpected = "Amit Roy";
        Assertions.assertTrue(nameActual.contains(nameExpected));
    }
    @Test
    public void clickButton(){
        driver.get("https://demoqa.com/buttons");
        List<WebElement> button = driver.findElements(By.className("btn-primary"));
        Actions actions =new Actions(driver);
        actions.doubleClick(button.get(0)).perform();
        actions.contextClick(button.get(1)).perform();
        actions.click(button.get(2)).perform();

        List<WebElement>textActual = driver.findElements(By.tagName("p"));
        String t1 = textActual.get(0).getText();
        String t2 = textActual.get(1).getText();
        String t3 = textActual.get(2).getText();

        Assertions.assertTrue(t1.contains("double click"));
        Assertions.assertTrue(t2.contains("right click"));
        Assertions.assertTrue(t3.contains("dynamic click"));
    }
    @Test
    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
        driver.findElement(By.id("timerAlertButton")).click();
        Thread.sleep(6000);
        driver.switchTo().alert().accept();
        driver.findElement(By.id("confirmButton")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().dismiss();
        driver.findElement(By.id("promtButton")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().sendKeys("Amit");

//        String actualText = driver.findElement(By.id("promptResult")).getText();
//        String expectedText = "Amit Roy";
//        Assertions.assertTrue(actualText.contains(expectedText));
    }
    @Test

    public void datePicker(){
        driver.get("https://demoqa.com/date-picker");
        WebElement txtCalender = driver.findElement(By.id("datePickerMonthYearInput"));
        txtCalender.click();
        txtCalender.sendKeys(Keys.CONTROL+"a",Keys.BACK_SPACE);
        txtCalender.sendKeys("4/2/2024");
        txtCalender.sendKeys(Keys.ENTER);
    }
    @Test

    public void selectDropdown(){
        driver.get("https://demoqa.com/select-menu");
        WebElement dropdown = driver.findElement(By.id("oldSelectMenu"));
        Select select =new Select(dropdown);
        select.selectByVisibleText("Black");
    }
    @Test

    public void selectMultipleDropdown(){
        driver.get("https://demoqa.com/select-menu");
        Utils.scroll(driver);
        WebElement dropdown = driver.findElement(By.id("cars"));
        Select select =new Select(dropdown);
        if (select.isMultiple()){
            select.selectByVisibleText("Volvo");
            select.selectByVisibleText("Audi");
        }

    }
    @Test

    public void selectWithOptDropdown()throws InterruptedException{
        driver.get("https://demoqa.com/select-menu");
        WebElement dropDown =  driver.findElement(By.id("withOptGroup"));
        dropDown.click();
        Thread.sleep(2000);



//        dropDown.sendKeys(Keys.ARROW_DOWN);
//        Thread.sleep(2000);
//        dropDown.sendKeys(Keys.ARROW_DOWN);
//        Thread.sleep(2000);
//        dropDown.sendKeys(Keys.ENTER);


        Actions actions = new Actions(driver);
        for (int i =0; i<2; i++){
            actions.keyDown(Keys.ARROW_DOWN).perform();
            Thread.sleep(500);
        }
        actions.sendKeys(Keys.ENTER);
    }
    @Test

    public void mouseHover(){
        driver.get("https://daffodilvarsity.edu.bd/");
        WebElement menu =driver.findElement(By.xpath("//a[contains(text(),\"Admissions\")]"));
        Actions actions =new Actions(driver);
        actions.moveToElement(menu).perform();
    }
    @Test

    public void uploadImage(){
        driver.get("https://demoqa.com/upload-download");
        driver.findElement(By.id("uploadFile")).sendKeys(System.getProperty("user.dir")+ "./src/test/resources/image.jpg");
    }
    @Test

    public void  handleTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(3000);
        ArrayList<String> w = new ArrayList(driver.getWindowHandles());
        //switch to open tab
        driver.switchTo().window(w.get(1));
        System.out.println("New tab title: " + driver.getTitle());
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertEquals(text,"This is a sample page");
        driver.close();
        driver.switchTo().window(w.get(0));

    }
    @Test

    public void handleChildWindow(){
        driver.get("https://demoqa.com/browser-windows");
//        Thread.sleep(5000);
//        WebDriverWait wait = new WebDriverWait(driver, 30);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        driver.findElement(By.id(("windowButton"))).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        while (iterator.hasNext()) {
                String ChildWindow = iterator.next();
                if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                    driver.switchTo().window(ChildWindow);
                    String text= driver.findElement(By.id("sampleHeading")).getText();
                    Assertions.assertTrue(text.contains("This is a sample page"));
                }
        }
        driver.close();
        driver.switchTo().window(mainWindowHandle);

    }

    


    @AfterAll
    public void closeDriver(){
//        driver.quit();
    }
}
