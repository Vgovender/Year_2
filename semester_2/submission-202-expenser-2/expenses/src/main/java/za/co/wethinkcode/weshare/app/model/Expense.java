package za.co.wethinkcode.weshare.app.model;

import com.google.common.base.Strings;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Expense extends AbstractTransactionModelBase{
    private final LocalDate date;
    private final String description;
    private final Person paidBy;
    private final Set<Claim> claims;

    public Expense(Double amount, LocalDate date, String description, Person paidBy) {
        //a unique id,
        //the amount of the expense as captured
        super(UUID.randomUUID(), amount);
        //a description of the expense
        this.description = Strings.isNullOrEmpty(description) ? "Unspecified" : description;
        //the Person that paid the expense (the person who captured the expense)
        this.paidBy = paidBy;
        //date
        this.date = date;
        this.claims = new HashSet<>();
    }

    //<editor-fold desc="Behaviour">
    public Claim createClaim( Person claimedFrom, Double amount, LocalDate dueDate ){
        Double currentTotalClaimed = this.claims.stream().mapToDouble(Claim::getAmount).sum();
        if (currentTotalClaimed + amount > this.getAmount()) {
            throw new RuntimeException("Total claims exceeds the amount of the expense");
        }
        Claim claim = new Claim( this, claimedFrom, amount, dueDate );
        this.claims.add(claim);
        return claim;
    }

    public void removeClaim(Claim claim){
        this.claims.remove(claim);
    }

    //</editor-fold>

    //<editor-fold desc="Accessors">
    public Set<Claim> getClaims() {
        return claims.stream()
                .sorted(Comparator.comparing(Claim::getDueDate))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LocalDate getDate() { return date; }

    public String getDescription() {
        return description;
    }

    public Person getPaidBy() {
        return paidBy;
    }

    //</editor-fold>

    //<editor-fold desc="Utilities">

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (!date.equals(expense.date)) return false;
        if (!description.equals(expense.description)) return false;
        return paidBy.equals(expense.paidBy);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + paidBy.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", paidBy=" + paidBy +
                ", id=" + id +
                ", amount=" + amount +
                '}';
    }

    //the nett amount of the expense being the expense amount
    public String getFormattedAmount(){
        return String.format("R %,.2f", this.amount);
    }

    /*the sum of all for claims against the expense,
    regardless of whether the claims have been settled or not*/
    public Double getTotalClaims() {
        if (claims.isEmpty()) return 0.0;
        return claims.stream().map(AbstractTransactionModelBase::getAmount).reduce(Double::sum).get();
    }

    //the sum of unsettled claims against the expense
    public Double getUnclaimedAmount() {
        return this.amount - getTotalClaims();
    }

    //the nett amount for expense less the sum of settled claims for the expense
    // (9th bullet point)
    //or
    //the nett amount of the expense being the expense amount(8th bp)
    public Double getNettAmount() {
        return this.amount - getTotalSettledClaims();
    }

    //sum of settled claims
    private Double getTotalSettledClaims() {
        if (claims.isEmpty()) return 0.0;
        return claims.stream()
                .filter(Claim::isSettled)
                .mapToDouble(Claim::getAmount).sum();
    }
    //</editor-fold>
}
