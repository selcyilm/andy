package delft;

import net.jqwik.web.api.Web;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

class MauricioAirwaysTests {

    private WebDriver driver;

    @BeforeEach
    public void initDriver() {
        // Do not change this line. If you change it, your tests will never work, 
        // and your final grade for this exercise will be zero.
        driver = CSE1110.createDriver();
    }

    //test if there is flights to select
    @Test
    public void testIfThereIsFlight() {
        List<WebElement> flightElementsList = driver.findElements(By.className("selectFlight"));
        //test if there is a flight
        Assertions.assertTrue(flightElementsList.size() > 0);

        //test if the flight is enabled
        Assertions.assertTrue(flightElementsList.get(0).isEnabled());
    }

    //test the fight use select appear in selected flight
    @ParameterizedTest(name = "flightIndex = {0}")
    @CsvSource({"0", "1", "2", "3"})
    public void testIfFlightAppear(int flightIndex) {
        List<WebElement> flightElementsList = driver.findElements(By.className("selectFlight"));
        WebElement firstFlightElement = flightElementsList.get(flightIndex);
        firstFlightElement.click();
        String flightFullText = firstFlightElement.getText();

        WebElement flightToken = driver.findElement(By.id("selectedFlightName"));

        String expectedToken = ((flightFullText.split("\n"))[1].split(" "))[1];
        String actualToken = flightToken.getText();

        Assertions.assertEquals(expectedToken, actualToken);
    }

    //test the selected fight has more than one seat available
    @ParameterizedTest(name = "flightIndex = {0}")
    @CsvSource({"0", "1", "2", "3"})
    public void testIfMoreThanOneSeat(int flightIndex) {
        List<WebElement> flightElementsList = driver.findElements(By.className("selectFlight"));
        WebElement firstFlightElement = flightElementsList.get(flightIndex);
        firstFlightElement.click();

        int actualSeatAvailable = Integer.parseInt(driver.findElement(By.id("availableSeats"))
                .getText());

        Assertions.assertTrue(actualSeatAvailable > 0);
    }

    //book a valid seat and test your ticket is seen in reserved
    //after you reserved, test if there is valid amount of seat remained
    @ParameterizedTest(name = "flightIndex = {0}")
    @CsvSource({"0", "1", "2", "3"})
    public void testValidSeatReserved(int flightIndex) {
        List<WebElement> flightElementsList = driver.findElements(By.className("selectFlight"));
        WebElement firstFlightElement = flightElementsList.get(flightIndex);
        firstFlightElement.click();

        String availableSeatNumber = driver.findElement(By.id("availableSeats")).getText();

        WebElement bookSeatBox = driver.findElement(By.id("selectedNumberOfSeats"));
        bookSeatBox.sendKeys(availableSeatNumber);
        driver.findElement(By.id("bookButton")).click();

        String afterAvailableSeat = driver.findElement(By.id("availableSeats")).getText();

        Assertions.assertEquals("0", afterAvailableSeat);

        WebElement bookedTicketsBox = driver.findElement(By.id("ticketList"));
        String bookedTableArr[] = bookedTicketsBox.getText().split(" ");

        String expectedToken = ((firstFlightElement.getText().split("\n"))[1].split(" "))[1];
        String actualToken = bookedTableArr[0];

        Assertions.assertEquals(expectedToken, actualToken);

        String actualSeatsTaken = bookedTableArr[3];

        Assertions.assertEquals(availableSeatNumber, actualSeatsTaken);
    }

    //book  invalid seat (negative) and test if error message is shown
    @ParameterizedTest(name = "flightIndex = {0}")
    @CsvSource({"0", "1", "2", "3"})
    public void testNegInvalidValidSeatReserved(int flightIndex) {
        List<WebElement> flightElementsList = driver.findElements(By.className("selectFlight"));
        WebElement firstFlightElement = flightElementsList.get(flightIndex);
        firstFlightElement.click();


        WebElement bookSeatBox = driver.findElement(By.id("selectedNumberOfSeats"));
        bookSeatBox.sendKeys("" + new Random().nextInt(10) * -1);
        driver.findElement(By.id("bookButton")).click();

        String expectedErrorMessage = "Selected number of seats not possible.";
        String actualErrorMessage = driver.findElement(By.id("alert-msg")).getText();
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);

        driver.findElement(By.id("closeErrorButton")).click();
    }

    //book  invalid seat (above available) and test if error message is shown
    @ParameterizedTest(name = "flightIndex = {0}")
    @CsvSource({"0", "1", "2", "3"})
    public void testAboveInvalidValidSeatReserved(int flightIndex) {
        List<WebElement> flightElementsList = driver.findElements(By.className("selectFlight"));
        WebElement firstFlightElement = flightElementsList.get(flightIndex);
        firstFlightElement.click();


        int actualSeatAvailable = Integer.parseInt(driver.findElement(By.id("availableSeats"))
                .getText());
        WebElement bookSeatBox = driver.findElement(By.id("selectedNumberOfSeats"));
        bookSeatBox.sendKeys("" + (actualSeatAvailable + 1));
        driver.findElement(By.id("bookButton")).click();

        String expectedErrorMessage = "Selected number of seats not possible.";
        String actualErrorMessage = driver.findElement(By.id("alert-msg")).getText();
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);

        driver.findElement(By.id("closeErrorButton")).click();
    }

    @AfterEach
    public void close() {
        driver.close();
    }

}
