package delft;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    //Test Cases:
    // Invalid Ones

    //1-add emty book and id
    //      Expected: "Book name cannot be empty"
    @Test
    public void emtyBookEmtyId() {
        driver.findElement(By.id("book_submit_button")).click();

        String expectedAlert = "Book name cannot be empty";
        String actualAlert = driver.findElement(By.id("alert-msg")).getText();

        assertThat(actualAlert.equals(expectedAlert)).isTrue();
    }

    //2-add emty book and valid id
    //      Expected: "Book name cannot be empty"
    @ParameterizedTest(name = "Id = {0}")
    @CsvSource({"1", "2", "3"})
    public void emtyBookValidId(String validId) {
        WebElement authorIdBox = driver.findElement(By.id("author_id"));
        authorIdBox.sendKeys(validId);

        driver.findElement(By.id("book_submit_button")).click();

        String expectedAlert = "Book name cannot be empty";
        String actualAlert = driver.findElement(By.id("alert-msg")).getText();

        assertThat(actualAlert.equals(expectedAlert)).isTrue();
    }

    //3-add valid book and emty id
    //      Expected: "Author with that id does not exist"
    @Test
    public void validBookEmtyId() {
        WebElement bookNameBox = driver.findElement(By.id("book_name"));
        bookNameBox.sendKeys("1984");

        driver.findElement(By.id("book_submit_button")).click();

        String expectedAlert = "Author with that id does not exist";
        String actualAlert = driver.findElement(By.id("alert-msg")).getText();

        assertThat(actualAlert.equals(expectedAlert)).isTrue();
    }

    //4-add valid book and invalid id
    //      Expected: "Author with that id does not exist"
    @ParameterizedTest(name = "id={0}")
    @CsvSource({"4", "-1", "0"})
    public void validBookInvalidId(String id) {
        WebElement bookNameBox = driver.findElement(By.id("book_name"));
        bookNameBox.sendKeys("1984");

        WebElement authorIdBox = driver.findElement(By.id("author_id"));
        authorIdBox.sendKeys(id);

        driver.findElement(By.id("book_submit_button")).click();

        String expectedAlert = "Author with that id does not exist";
        String actualAlert = driver.findElement(By.id("alert-msg")).getText();

        assertThat(actualAlert.equals(expectedAlert)).isTrue();
    }

    //5- add same book twice
    //      Expected: "The author already has a book with that name!"
    @ParameterizedTest(name = "bookName = {0}, authorId = {1}")
    @CsvSource({"1984, 1", "Beyond Order, 2", "Meditations, 3"})
    public void duplicateBook(String bookName, String authorId) {
        WebElement bookNameBox = driver.findElement(By.id("book_name"));
        bookNameBox.sendKeys(bookName);

        WebElement authorIdBox = driver.findElement(By.id("author_id"));
        authorIdBox.sendKeys(authorId);

        driver.findElement(By.id("book_submit_button")).click();

        bookNameBox.clear();
        bookNameBox.sendKeys(bookName);

        authorIdBox.clear();
        authorIdBox.sendKeys(authorId);

        driver.findElement(By.id("book_submit_button")).click();

        String expectedAlert = "The author already has a book with that name!";
        String actualAlert = driver.findElement(By.id("alert-msg")).getText();

        assertThat(actualAlert.equals(expectedAlert)).isTrue();
    }

    //Valid ones
    //1- Add valid book with valid author
    //      Expected: "Book was successfully added!"
    @ParameterizedTest(name = "bookName = {0}, authorId = {1}")
    @CsvSource({"1984, 1", "Beyond Order, 2", "Meditations, 3"})
    public void validBookValidId(String bookName, String authorId) {
        WebElement bookNameBox = driver.findElement(By.id("book_name"));
        bookNameBox.sendKeys(bookName);

        WebElement authorIdBox = driver.findElement(By.id("author_id"));
        authorIdBox.sendKeys(authorId);

        driver.findElement(By.id("book_submit_button")).click();

        String expectedAlert = "Book was successfully added!";
        String actualAlert = driver.findElement(By.id("alert-msg")).getText();

        assertThat(actualAlert.equals(expectedAlert)).isTrue();
    }

    @AfterEach
    public void close() {
        driver.close();
    }

}
