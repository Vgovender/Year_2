package za.co.wethinkcode.weshare;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

public class RatingsPageDisplayTest extends AbstractUserTest {
    @Test
    void claimLabelTest() {
        // Login
        doLogin("student@wethinkcode.co.za");

        //Navigate to claims dropdown
        navigateToListItem("/ratings?value=claims");
        testTitle("Ratings by claims");
        // capture an expense
        navigateTo("/home");

    }

    @Test
    void settledLabelTest() {
        // Login
        doLogin("student@wethinkcode.co.za");

        //Navigate to claims dropdown
        navigateToListItem("/ratings?value=settled");
        testTitle("Ratings by settled");
        // capture an expense
        navigateTo("/home");

    }

    @Test
    void unsettledLabelTest() {
        // Login
        doLogin("student@wethinkcode.co.za");

        //Navigate to claims dropdown
        navigateToListItem("/ratings?value=unsettled");
        testTitle("Ratings by unsettled");
        // capture an expense
        navigateTo("/home");

    }

    @Test
    void expenseLabelTest() {
        // Login
        doLogin("student@wethinkcode.co.za");

        //Navigate to claims dropdown
        navigateToListItem("/ratings?value=expenses");
        testTitle("Ratings by expenses");
        // capture an expense
        navigateTo("/home");

    }
}

