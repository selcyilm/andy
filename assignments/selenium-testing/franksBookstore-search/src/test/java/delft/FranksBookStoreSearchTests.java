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
import org.openqa.selenium.Keys;

import static org.assertj.core.api.Assertions.assertThat;

class FranksBookStoreSearchTests {

    private WebDriver driver;

    @BeforeEach
    public void initDriver() {
        driver = CSE1110.getDriver();
    }

    //First you gotta test if there is a search box
    @Test
    public void searchBoxAppearTest() {
        WebElement searchBox = driver.findElement(By.id("search_bar"));

        Assertions.assertTrue(searchBox.isDisplayed());
    }

    @Test
    public void emtySearchBoxTest() {
        WebElement searchBox = driver.findElement(By.id("search_bar"));
        searchBox.sendKeys(Keys.ENTER);

        WebElement authorListElement = driver.findElement(By.id("author_list"));
        String expectedAuthor1 = "Mauricio";
        String expectedAuthor2 = "Dan Brown";
        String expectedAuthor3 = "Tolkien";

        assertThat(authorListElement.getText().contains(expectedAuthor1)).isTrue();
        assertThat(authorListElement.getText().contains(expectedAuthor2)).isTrue();
        assertThat(authorListElement.getText().contains(expectedAuthor3)).isTrue();
    }

    @ParameterizedTest(name = "authorName = {0}")
    @CsvSource({"Selcuk", "Ahmet", "Test53"})
    public void addAuthorAndSearchTest(String authorName) {
        driver.findElement(By.id("author_name")).sendKeys(authorName);
        driver.findElement(By.id("author_submit_button")).click();

        WebElement searchBox = driver.findElement(By.id("search_bar"));
        searchBox.sendKeys(authorName + Keys.ENTER);

        String actualAuthorListElement = driver.findElement(By.id("author_list")).getText();
        Assertions.assertTrue(actualAuthorListElement.contains(authorName));
    }


    @ParameterizedTest(name = "authorName = {0}, authorBook = {1}, authorId = {2}")
    @CsvSource({"Selcuk, GDPR, 4", "Ahmet, Essays, 4", "Test53, BookForLove, 4"})
    public void addAuthorAndBookSearchTest(String authorName, String authorBook, int id) {
        driver.findElement(By.id("author_name")).sendKeys(authorName);
        driver.findElement(By.id("author_submit_button")).click();

        driver.findElement(By.id("book_name")).sendKeys(authorBook);
        driver.findElement(By.id("author_id")).sendKeys(Integer.toString(id));
        driver.findElement(By.id("book_submit_button")).click();

        WebElement searchBox = driver.findElement(By.id("search_bar"));
        searchBox.sendKeys(authorName + Keys.ENTER);

        String actualAuthorListElement = driver.findElement(By.id("author_list")).getText();
        Assertions.assertTrue(actualAuthorListElement.contains(authorName));

        searchBox.clear();
        searchBox.sendKeys(authorBook + Keys.ENTER);

        String bookListElement = driver.findElement(By.id("book_list")).getText();
        Assertions.assertTrue(bookListElement.contains(authorName));
        Assertions.assertTrue(bookListElement.contains(authorBook));

    }
    @AfterEach
    public void close() {
        driver.close();
    }

}
