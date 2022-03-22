package za.co.wethinkcode.weshare.user;

import org.jetbrains.annotations.NotNull;
import za.co.wethinkcode.weshare.pages.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserSession {
    private final UserTestRunner testRunner;
    private LoginPage loginPage;
    private NettExpensesPage nettExpensesPage;
    private String email;
    private ExpensePage expensePage;
    private final Map<String, Expense> expenses = new HashMap<>();
    private final Map<String, Claim> claims = new HashMap<>();
    private ClaimPage claimPage;
    private SettlementPage settlementPage;

    public UserSession(UserTestRunner testRunner) {
        this.testRunner = testRunner;
        loginPage = new LoginPage(testRunner);
    }

    public UserSession openLoginPage() {
        loginPage.open();
        return this;
    }

    public UserSession login(String email) {
        this.email = email;
        nettExpensesPage = loginPage.loginUser(email);
        return this;
    }

    public UserSession open(AbstractPage page) {
        page.open();
        return this;
    }

    public UserSession shouldBeOnLoginPage() {
        testRunner.shouldBeOnPage(loginPage);
        return this;
    }

    public UserSession shouldBeOnNettExpensesPage() {
        testRunner.shouldBeOnPage(nettExpensesPage);
        return this;
    }

    public UserSession shouldHaveEmailAddressDisplayed() {
        assertThat(nettExpensesPage.userEmail()).isEqualTo(email);
        return this;
    }

    public UserSession shouldHaveLogoutLinkDisplayed() {
        String name = getNameFromEmail(email);
        assertThat(nettExpensesPage.logoutText()).isEqualTo("Logout " + name);
        return this;
    }

    @NotNull
    private String getNameFromEmail(String email) {
        String namePart = email.split("@")[0];
        return namePart.substring(0, 1).toUpperCase() + namePart.substring(1);
    }

    public UserSession logout() {
        loginPage = nettExpensesPage.logout();
        return this;
    }

    public UserSession shouldHaveNoExpenses() {
        assertThat(nettExpensesPage.hasNoExpenses()).isTrue();
        return this;
    }

    public UserSession clickOnCaptureExpense() {
        expensePage = nettExpensesPage.captureExpense();
        return this;
    }

    public UserSession shouldBeOnCaptureExpensePage() {
        testRunner.shouldBeOnPage(expensePage);
        return this;
    }

    public UserSession captureExpense(String description, double amount, String isoDateString) {
        int row = expenses.size() + 1;
        LocalDate date = LocalDate.parse(isoDateString, DateTimeFormatter.ISO_DATE);
        Expense expense = new Expense(row, description, amount, date);
        expenses.put(description, expense);
        nettExpensesPage = expensePage.submitExpense(description, amount, date);
        return this;
    }

    public UserSession shouldHaveExpense(String description) {
        Expense expense = expenses.get(description);
        verifyExpense(expense.row, expense.description, expense.amount, expense.date);
        return this;
    }

    private void verifyExpense(int row, String description, double amount, LocalDate date) {
        assertThat(nettExpensesPage.expenseDescription(row)).isEqualTo(description);
        assertThat(nettExpensesPage.expenseAmount(row)).isEqualTo(amount);
        assertThat(nettExpensesPage.expenseDate(row)).isEqualTo(date);
    }

    public UserSession sumOfExpensesShouldBe(double amount) {
        assertThat(nettExpensesPage.totalExpenses()).isEqualTo(amount);
        return this;
    }

    public UserSession nettExpensesTotalShouldBe(double amount) {
        assertThat(nettExpensesPage.totalNettExpenses()).isEqualTo(amount);
        return this;
    }

    public UserSession clickOnExpense(String description) {
        Expense expense = expenses.get(description);
        claimPage = nettExpensesPage.clickOnExpenseAtRow(expense.row);
        return this;
    }

    public UserSession shouldBeOnClaimPageForExpense(String description) {
        testRunner.shouldBeOnPage(claimPage);
        Expense expense = expenses.get(description);

        assertThat(claimPage.expenseDescription()).isEqualTo(expense.description);
        assertThat(claimPage.expenseDate()).isEqualTo(expense.date);
        assertThat(claimPage.expenseAmount()).isEqualTo(expense.amount);
        return this;
    }

    public UserSession captureClaim(String email, double amount, String isoDate) {
        LocalDate date = LocalDate.parse(isoDate, DateTimeFormatter.ISO_DATE);
        int row = claims.size() + 1;
        Claim claim = new Claim(row, email, amount, date);
        claims.put(email, claim);
        claimPage.captureClaim(email, amount, date);
        return this;
    }

    public UserSession claimPageShouldHaveClaimFor(String email) {
        Claim claim = claims.get(email);
        assertThat(claimPage.claimFrom(claim.row)).isEqualTo(getNameFromEmail(email));
        assertThat(claimPage.claimAmount(claim.row)).isEqualTo(claim.amount);
        assertThat(claimPage.claimSettled(claim.row)).isEqualTo(claim.settled);
        assertThat(claimPage.claimDueDate(claim.row)).isEqualTo(claim.date);
        return this;
    }

    public UserSession sumOfClaimsShouldBe(double amount) {
        assertThat(nettExpensesPage.totalClaims()).isEqualTo(amount);
        return this;
    }

    public UserSession shouldHaveClaimsToSettle() {
        assertThat(nettExpensesPage.hasSettlements()).isTrue();
        return this;
    }

    public UserSession sumOfClaimsToSettleShouldBe(double amount) {
        assertThat(nettExpensesPage.totalSettlements()).isEqualTo(amount);
        return this;
    }

    public UserSession clickOnFirstClaimToSettle() {
        settlementPage = nettExpensesPage.clickOnSettlementAtRow(1);
        return this;
    }

    public UserSession shouldBeOnSettlementPageWith(String email, String description, double amount, String isoDate) {
        testRunner.shouldBeOnPage(settlementPage);
        LocalDate date = LocalDate.parse(isoDate, DateTimeFormatter.ISO_DATE);
        assertThat(settlementPage.email()).isEqualTo(email);
        assertThat(settlementPage.dueDate()).isEqualTo(date);
        assertThat(settlementPage.description()).isEqualTo(description);
        assertThat(settlementPage.amount()).isEqualTo(amount);
        return this;
    }

    public UserSession settleClaim() {
        nettExpensesPage = settlementPage.settleClaim();
        return this;
    }

    public UserSession shouldHaveNoClaimsToSettle() {
        assertThat(nettExpensesPage.hasNoSettlements()).isTrue();
        return this;
    }

    public UserSession firstExpenseShouldBe(String description, double amount, String isoDate) {
        LocalDate date = LocalDate.parse(isoDate, DateTimeFormatter.ISO_DATE);
        verifyExpense(1, description, amount, date);
        return this;
    }

    public UserSession shouldHaveNoClaims() {
        assertThat(nettExpensesPage.hasNoClaims()).isTrue();
        return this;
    }

    private class Claim {
        public final int row;
        public final String email;
        public final double amount;
        public final LocalDate date;
        public final String settled;

        private Claim(int row, String email, double amount, LocalDate date) {
            this.row = row;
            this.email = email;
            this.amount = amount;
            this.date = date;
            this.settled = "No";
        }
    }

    private class Expense {
        public final int row;
        public final String description;
        public final double amount;
        public final LocalDate date;

        private Expense(int row, String description, double amount, LocalDate date) {
            this.row = row;
            this.description = description;
            this.amount = amount;
            this.date = date;
        }
    }
}
