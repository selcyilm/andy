package delft;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

class FranksBookStoreTests {

    private WebDriver driver;

    @BeforeEach
    public void initDriver() {
        driver = CSE1110.getDriver();
    }

    // Write your tests here.
    @Test
    public void addAuthorWithValidNameTest() {
        WebElement authorBox = driver.findElement(By.id("author_name"));
        authorBox.sendKeys("Victor Hugo");

        driver.findElement(By.id("author_submit_button")).click();

        WebElement alertMessage = driver.findElement(By.id("alert-msg"));
        String expectedAlertRespond = "Author successfully added";
        String actualAlertRespond = alertMessage.getText();

        Assertions.assertTrue(actualAlertRespond.contains(expectedAlertRespond));
    }

    @Test
    public void addNothingAndSubmitTest() {
        driver.findElement(By.id("author_submit_button")).click();

        WebElement alertMessage = driver.findElement(By.id("alert-msg"));
        String expectedAlertRespond = "Author successfully added";
        String actualAlertRespond = alertMessage.getText();

        Assertions.assertFalse(actualAlertRespond.contains(expectedAlertRespond));
    }

    @Test
    public void addSameAuthorTwice() {
        WebElement authorBox = driver.findElement(By.id("author_name"));
        authorBox.sendKeys("Douglas Adams");
        driver.findElement(By.id("author_submit_button")).click();
        authorBox.clear();
        authorBox.sendKeys("Douglas Adams");
        driver.findElement(By.id("author_submit_button")).click();

        String expextedAlertMessage = "Author with that name already exists!";
        String actualAlertMessage = driver.findElement(By.id("alert-msg")).getText();

        Assertions.assertEquals(expextedAlertMessage, actualAlertMessage);
    }

    @AfterEach
    public void close() {
        driver.close();
    }

}
