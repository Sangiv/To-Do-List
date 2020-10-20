package com.qa.todo.selenium;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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

        assertThat(driver.getTitle()).isEqualTo("To-Do-List");
    }
    
    
    @Test
    public void testCreatePage() throws InterruptedException {
        driver.get("http://localhost:8901/index.html");
        
//        navigate to create TaskList page
        driver.findElement(By.xpath("/html/body/a")).click();

        assertThat(driver.getTitle()).isEqualTo("Create A Task List");
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
        Thread.sleep(1500);
        assertThat(driver.findElement(By.xpath("/html/body/table")).isDisplayed());
    }
    
    
    @Test
    public void testViewPage() throws InterruptedException {
        driver.get("http://localhost:8901/index.html");
        
//      navigate to create TaskList page
        driver.findElement(By.xpath("/html/body/a")).click();
        
//      create a TaskList
        WebElement createBar = driver.findElement(By.id("inputName"));
        String createTerm = "End-Of-Season Tagets";
        createBar.sendKeys(createTerm);
        createBar.submit();
        
//      Navigate back to homepage
        driver.findElement(By.xpath("/html/body/div/a")).click();
        
//        navigate to view TaskList page
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/a")).click();

        assertThat("Task List Details").isEqualTo(driver.getTitle());
    }
    
    
    @Test
    public void testUpdateTaskList() throws InterruptedException {
        driver.get("http://localhost:8901/index.html");
        
//      navigate to create TaskList page
        driver.findElement(By.xpath("/html/body/a")).click();
        
//      create a TaskList
        WebElement createBar = driver.findElement(By.id("inputName"));
        String createTerm = "End-Of-Season Tagets";
        createBar.sendKeys(createTerm);
        createBar.submit();
        
//      Navigate back to homepage
        driver.findElement(By.xpath("/html/body/div/a")).click();
        
//        navigate to view TaskList page
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/a")).click();
        
//        find update textbox
        WebElement updateBar = driver.findElement(By.id("inputName"));
        String updateTerm = "End-Of-Season Goals";
        updateBar.clear();
        updateBar.sendKeys(updateTerm);
        updateBar.submit();
        
//        navigate back to home
        driver.findElement(By.xpath("/html/body/div/a[1]")).click();
        
        
        List<WebElement> results;
        results = driver.findElements(By.xpath("/html/body/table/tbody/tr/*"));
        WebElement result = results.get(1);
        
        String testtext = result.getText();
        
//        is the name updated?
        Thread.sleep(1500);
        assertThat(updateTerm).isEqualTo(testtext);
           
    }
    
    
    @Test
    public void testCreateTaskPage() throws InterruptedException {
    	driver.get("http://localhost:8901/record.html?id=1");
        
//        click the create task button
        driver.findElement(By.xpath("/html/body/div/a[2]")).click();

        assertThat("Create A Task").isEqualTo(driver.getTitle());        
    }
    
    
    @Test
    public void testCreateTask() throws InterruptedException {
        driver.get("http://localhost:8901/index.html");
        
//      navigate to create TaskList page
        driver.findElement(By.xpath("/html/body/a")).click();
        
//      create a TaskList
        WebElement createBar = driver.findElement(By.id("inputName"));
        String createTerm = "End-Of-Season Tagets";
        createBar.sendKeys(createTerm);
        createBar.submit();
        
//      Navigate back to homepage
        driver.findElement(By.xpath("/html/body/div/a")).click();
        
//      navigate to view TaskList page
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/a")).click();
        
//      navigate to create task page
        driver.findElement(By.xpath("/html/body/div/a[2]")).click();
        
//      create a Task
        WebElement createBar2 = driver.findElement(By.id("inputName"));
        WebElement idBar = driver.findElement(By.id("inputId"));
        String createTerm2 = "End-Of-Season Tagets";
        createBar2.sendKeys(createTerm2);
        idBar.sendKeys("1");
        createBar2.submit();
      
//      Navigate back to homepage
        driver.findElement(By.xpath("/html/body/div/a")).click();
//      Navigate to TaskList
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[3]/a")).click();
      
//      is table now displayed?
        Thread.sleep(1500);
        assertThat(driver.findElement(By.xpath("/html/body/div/table")).isDisplayed());       
    }

    
    @Test
    public void testDeleteTask() throws InterruptedException {
        driver.get("http://localhost:8901/index.html");
        
//      navigate to create TaskList page
        driver.findElement(By.xpath("/html/body/a")).click();
        
//      create a TaskList
        WebElement createBar = driver.findElement(By.id("inputName"));
        String createTerm = "End-Of-Season Tagets";
        createBar.sendKeys(createTerm);
        createBar.submit();
        
//      Navigate back to homepage
        driver.findElement(By.xpath("/html/body/div/a")).click();
        
//      navigate to view TaskList page
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/a")).click();
        
//      navigate to create task page
        driver.findElement(By.xpath("/html/body/div/a[2]")).click();
        
//      create a Task
        WebElement createBar2 = driver.findElement(By.id("inputName"));
        WebElement idBar = driver.findElement(By.id("inputId"));
        String createTerm2 = "End-Of-Season Tagets";
        createBar2.sendKeys(createTerm2);
        idBar.sendKeys("1");
        createBar2.submit();
      
//      Navigate back to homepage
        driver.findElement(By.xpath("/html/body/div/a")).click();
//      Navigate to TaskList
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[3]/a")).click();
        
//        click the delete task button
        driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[4]/a")).click();
        
//        is the task still there?
        Thread.sleep(3000);
        assertFalse(driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]")).isDisplayed());       
    }
    
    
    @Test
    public void testDeleteTaskList() throws InterruptedException {
        driver.get("http://localhost:8901/createlist.html");
        
//      create a TaskList
        WebElement createBar = driver.findElement(By.id("inputName"));
        String createTerm = "End-Of-Season Tagets";
        createBar.sendKeys(createTerm);
        createBar.submit();
      
//      Navigate back to homepage
        driver.findElement(By.xpath("/html/body/div/a")).click();
        
//        click the delete task button
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        
//        is the task still there?
        Thread.sleep(3000);
        assertFalse(driver.findElement(By.xpath("/html/body/table")).isDisplayed());       
    }
    
    
    @AfterClass
    public static void tearDown() {
        driver.close();
    }
    
}