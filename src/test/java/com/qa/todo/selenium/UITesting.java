package com.qa.todo.selenium;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class UITesting {

    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1366, 768));

    }

    @Test
    public void testIndexPage() throws InterruptedException {
        driver.get("http://localhost:8901/index.html");

        assertThat("To-Do-List").isEqualTo(driver.getTitle());
    }
    
    @Test
    public void testCreatePage() throws InterruptedException {
        driver.get("http://localhost:8901/index.html");
        
//        navigate to create TaskList page
        driver.findElement(By.xpath("/html/body/a")).click();

        assertThat("Create A Task List").isEqualTo(driver.getTitle());
    }
    
    @Test
    public void testCreateTaskList() throws InterruptedException {
        driver.get("http://localhost:8901/createlist.html");
        
//        create a TaskList
        WebElement createBar = driver.findElement(By.id("inputName"));
        String createTerm = "End-Of-Season Tagets";
        createBar.sendKeys(createTerm);
        createBar.submit();
        
//        Navigate back to homepage
        driver.findElement(By.xpath("/html/body/div/a")).click();
        
//        is table now displayed?
        assertThat(driver.findElement(By.xpath("/html/body/table")).isDisplayed());
    }
    
    @Test
    public void testViewPage() throws InterruptedException {
        driver.get("http://localhost:8901/index.html");
        
//        navigate to view TaskList page
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/a")).click();

        assertThat("Task List Details").isEqualTo(driver.getTitle());
    }
    
    @Test
    public void testUpdateTaskList() throws InterruptedException {
        driver.get("http://localhost:8901/record.html?id=1");
        
//        find update textbox
        WebElement updateBar = driver.findElement(By.id("inputName"));
        String updateTerm = "End-Of-Season Goals";
        updateBar.sendKeys(updateTerm);
        updateBar.submit();
        
//        navigate back to home
        driver.findElement(By.xpath("/html/body/div/a[1]")).click();
        
//        is the name updated?
        assertThat(updateTerm).isEqualTo(driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]")).getText());
    }
    
    
    

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
    
}