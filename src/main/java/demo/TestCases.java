package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;


public class TestCases {
    ChromeDriver driver;
    public TestCases()
    {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.INFO);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Connect to the chrome-window running on debugging port
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    // Verify the URL of the Google Calendar home page
    public  void testCase01(){
        System.out.println("Start Test Case: testCase01");
        
        driver.get("https://calendar.google.com/");
        if(driver.getCurrentUrl().contains("calendar.")) {
            System.out.println("URL verification successful!");
        }
        else {
            System.out.println("URL verification unsuccessful!!!");
        }

        System.out.println("End Test Case: testCase01");
    }

    // Switch to the month view, and add a task for today
    public  void testCase02() throws InterruptedException{
        System.out.println("Start Test Case: testCase02");
        
        Thread.sleep(5000);
        WebElement viewDropdown=driver.findElement(By.xpath("//span[text()='View switcher menu']/..//button"));
        viewDropdown.click();
        Thread.sleep(2000);
        WebElement monthViewOption=driver.findElement(By.xpath("//ul[@role='menu']/li/span[text()='Month']/.."));
        monthViewOption.click();
        Thread.sleep(2000);

        LocalDateTime localDate=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd");
        String date=localDate.format(formatter);
        //h2[@class='w48V4c F262Ye']/..
        //div[@class='kbf0gd']/div[3]/div[1]/div[3]
        WebElement dateElement=driver.findElement(By.xpath("//h2[normalize-space()="+date+"]/parent::div"));
        Actions action=new Actions(driver);
        action.moveToElement(dateElement, 50, -50).click().perform();
        Thread.sleep(3000);

        WebElement task=driver.findElement(By.xpath("//div[text()='Task']/parent::button"));
        task.click();
        Thread.sleep(2000);
        WebElement titleBox=driver.findElement(By.xpath("//input[@aria-label='Add title and time']"));
        titleBox.sendKeys("Crio INTV Task Automation");
        WebElement descriptionBox=driver.findElement(By.xpath("//textarea[@aria-label='Add description']"));
        descriptionBox.sendKeys("Crio INTV Calendar Task Automation");
        WebElement saveButton=driver.findElement(By.xpath("//span[text()='Save']/.."));
        saveButton.click();
        Thread.sleep(3000);

        try {
            WebElement taskCreated=driver.findElement(By.xpath("//span[contains(text(),'task:')]"));
            System.out.println("Task Created Successfully--> "+taskCreated.getText());
        } catch(Exception e) {
            System.out.println("Task Creation Failed!!!");
        }

        System.out.println("End Test Case: testCase02");
    }

    public  void testCase03() throws InterruptedException{
        System.out.println("Start Test Case: testCase03");
        
        WebElement task=driver.findElement(By.xpath("//span[contains(text(), 'task:')]/.."));
        task.click();
        Thread.sleep(2000);
        WebElement editTaskButton=driver.findElement(By.xpath("//div[text()='Edit task']/../button"));
        editTaskButton.click();
        Thread.sleep(2000);
        WebElement descriptionBox=driver.findElement(By.xpath("//textarea[@placeholder='Add description']"));
        descriptionBox.clear();
        descriptionBox.sendKeys("Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application");
        WebElement saveButton=driver.findElement(By.xpath("//span[text()='Save']/.."));
        saveButton.click();
        Thread.sleep(3000);

        task.click();
        Thread.sleep(2000);
        String description=driver.findElement(By.xpath("//span[text()='Description:']/..")).getText().split(":")[1].trim();
        System.out.println(description);
        if(description.equals("Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application")) {
            System.out.println("Task description is successfully updated!");
        }
        else {
            System.out.println("Task description could not be updated!!!");
        }
        WebElement closeTask=driver.findElement(By.xpath("//div[text()='Edit task']/../../../../div[1]//button"));
        closeTask.click();
        Thread.sleep(2000);

        System.out.println("End Test Case: testCase03");
    }

    public  void testCase04() throws InterruptedException{
        System.out.println("Start Test Case: testCase04");
        
        WebElement task=driver.findElement(By.xpath("//span[contains(text(), 'task:')]/.."));
        task.click();
        Thread.sleep(2000);
        WebElement deleteTask=driver.findElement(By.xpath("//div[text()='Edit task']/../../../div[2]//button"));
        deleteTask.click();
        Thread.sleep(1000);
        // div#J9Hpafc14206
        String popupMessage=driver.findElement(By.xpath("(//div[@aria-label='Close'])[2]/../../div[1]")).getText();
        if(popupMessage.equals("Task deleted")) {
            System.out.println("Task deleted successfully!");
        }
        else {
            System.out.println("Task deletion failed!");
        }

        System.out.println("End Test Case: testCase04");
    }

}
