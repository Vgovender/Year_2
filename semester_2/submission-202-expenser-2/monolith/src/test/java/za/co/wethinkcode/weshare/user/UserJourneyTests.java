package za.co.wethinkcode.weshare.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.weshare.pages.NettExpensesPage;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("WeShare user journeys")
public class UserJourneyTests extends UserTestRunner {

    @Override
    protected void setupTestData() {

    }

    @Nested
    @DisplayName("Logging in and out")
    class LoginAndLogoutTests {
        UserSession session = new UserSession(UserJourneyTests.this);

        @Test
        @DisplayName("I tried to access the home page without logging in")
        void redirectWhenNotLoggedIn() {
            session.open(new NettExpensesPage(UserJourneyTests.this))
                    .shouldBeOnLoginPage();
        }

        @Test
        @DisplayName("I can login with my email address")
        void successfulLogin() {
            session.openLoginPage()
                    .login(randomEmail())
                    .shouldBeOnNettExpensesPage()
                    .shouldHaveEmailAddressDisplayed()
                    .shouldHaveNoExpenses()
                    .shouldHaveNoClaims()
                    .shouldHaveNoClaimsToSettle();
        }

        @Test
        @DisplayName("I can logout")
        void logout() {
            session.openLoginPage()
                    .login(randomEmail())
                    .shouldBeOnNettExpensesPage()
                    .logout()
                    .shouldBeOnLoginPage();
        }
    }

    @Nested
    @DisplayName("Capturing expenses")
    class CaptureExpenseTests {
        UserSession session = new UserSession(UserJourneyTests.this);

        @Test
        @DisplayName("I have no expenses")
        void hasNoExpenses() {
            session.openLoginPage()
                    .login(randomEmail())
                    .shouldHaveNoExpenses();
        }

        @Test
        @DisplayName("I can record my first expense")
        void firstExpense() {
            session.openLoginPage()
                    .login(randomEmail())
                    .clickOnCaptureExpense()
                    .shouldBeOnCaptureExpensePage()
                    .captureExpense("Friday Lunch", 300.00, "2021-10-17")
                    .shouldBeOnNettExpensesPage()
                    .shouldHaveExpense("Friday Lunch")
                    .sumOfExpensesShouldBe(300.00)
                    .nettExpensesTotalShouldBe(300.00);
        }
    }

    @Nested
    @DisplayName("Making claims")
    class MakingClaimsTests {

        @Test
        @DisplayName("I can claim from one person")
        void claimOnce() {
            String email1 = randomEmail();
            String email2 = randomEmail();

            UserSession session1 = new UserSession(UserJourneyTests.this);
            session1.openLoginPage()
                    .login(email1)
                        .clickOnCaptureExpense()
                        .captureExpense("Friday Lunch", 300.00, "2021-10-17")
                        .shouldBeOnNettExpensesPage()
                    .clickOnExpense("Friday Lunch")
                        .shouldBeOnClaimPageForExpense("Friday Lunch")
                        .captureClaim(email2, 50.00, "2021-10-30")
                    .logout()
                    .login(email1)
                        .sumOfExpensesShouldBe(300.00)
                        .nettExpensesTotalShouldBe(250.00)
                        .sumOfClaimsShouldBe(50.00)
                    .clickOnExpense("Friday Lunch")
                        .claimPageShouldHaveClaimFor(email2);

            UserSession session2 = new UserSession(UserJourneyTests.this);
            session2.openLoginPage()
                    .login(email2)
                    .shouldHaveClaimsToSettle()
                    .sumOfClaimsToSettleShouldBe(50.00);

        }

        @Test
        @DisplayName("I can claim from two people for the same expense")
        void claimTwice() {
            String email1 = randomEmail();
            String email2 = randomEmail();
            String email3 = randomEmail();

            UserSession session1 = new UserSession(UserJourneyTests.this);
            session1.openLoginPage()
                    .login(email1)
                        .clickOnCaptureExpense()
                        .captureExpense("Friday Lunch", 300.00, "2021-10-17")
                        .shouldBeOnNettExpensesPage()
                    .clickOnExpense("Friday Lunch")
                        .shouldBeOnClaimPageForExpense("Friday Lunch")
                        .captureClaim(email2, 100.00, "2021-10-29")
                        .captureClaim(email3, 100.00, "2021-10-30")
                    .logout()
                    .login(email1)
                        .sumOfExpensesShouldBe(300.00)
                        .sumOfClaimsShouldBe(200.00)
                        .nettExpensesTotalShouldBe(100.00)
                    .clickOnExpense("Friday Lunch")
                        .claimPageShouldHaveClaimFor(email2)
                        .claimPageShouldHaveClaimFor(email3);

            UserSession session2 = new UserSession(UserJourneyTests.this);
            session2.openLoginPage()
                    .login(email2)
                    .shouldHaveClaimsToSettle()
                    .sumOfClaimsToSettleShouldBe(100.00);

            UserSession session3 = new UserSession(UserJourneyTests.this);
            session3.openLoginPage()
                    .login(email3)
                    .shouldHaveClaimsToSettle()
                    .sumOfClaimsToSettleShouldBe(100.00);
        }
    }

    @Nested
    @DisplayName("Settling claims")
    class SettleClaimTests {

        @Test
        @DisplayName("I can settle a claim against me")
        void settleClaim() {
            String email1 = randomEmail();
            String email2 = randomEmail();

            UserSession session1 = new UserSession(UserJourneyTests.this);
            session1.openLoginPage()
                    .login(email1)
                        .clickOnCaptureExpense()
                        .captureExpense("Friday Lunch", 300.00, "2021-10-17")
                        .shouldBeOnNettExpensesPage()
                    .clickOnExpense("Friday Lunch")
                        .shouldBeOnClaimPageForExpense("Friday Lunch")
                        .captureClaim(email2, 150.00, "2021-10-30")
                    .logout();

            UserSession session2 = new UserSession(UserJourneyTests.this);
            session2.openLoginPage()
                    .login(email2)
                    .shouldHaveClaimsToSettle()
                    .sumOfClaimsToSettleShouldBe(150.00)
                    .clickOnFirstClaimToSettle()
                        .shouldBeOnSettlementPageWith(email1, "Friday Lunch", 150.00, "2021-10-30")
                        .settleClaim()
                    .shouldBeOnNettExpensesPage()
                        .shouldHaveNoClaimsToSettle()
                        .sumOfExpensesShouldBe(150.00)
                        .nettExpensesTotalShouldBe(150.00);

            session1.openLoginPage()
                    .login(email1)
                    .firstExpenseShouldBe("Friday Lunch", 150.00, "2021-10-17")
                    .sumOfExpensesShouldBe(150.00)
                    .nettExpensesTotalShouldBe(150.00);
        }


    }
}
