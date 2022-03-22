package za.co.wethinkcode.weshare.claim;

import java.time.DateTimeException;
import java.time.LocalDate;

import za.co.wethinkcode.weshare.app.model.Person;

public class ClaimViewModel {
    private Double claimAmount;
    private String claimedBy;
    private String claimedFrom;
    private String dueDate;
    private String expenseId;
    private String claimId;
    private int id;
    private String description;

    public ClaimViewModel(Double claimAmount, String claimedBy, String claimedFrom, String dueDate, String claimId, String expenseId, String description) {
        this.claimAmount = claimAmount;
        this.claimedBy = claimedBy;
        this.claimedFrom = claimedFrom;
        this.dueDate = dueDate;
        this.expenseId = expenseId;
        this.claimId = claimId;
        this.description = description;
    }

    public ClaimViewModel(Double claimAmount, String claimedByEmail, String dueDate, String claimId, String description) {
        this.claimAmount = claimAmount;
        this.claimedBy = claimedByEmail;
        this.dueDate = dueDate;
        this.claimId = claimId;
        this.description = description;
    }

    public ClaimViewModel(){ }

    public LocalDate dueDateAsLocalDate() {
        try {
            return LocalDate.parse(this.dueDate);
        } catch (DateTimeException e) {
            throw new RuntimeException("Could not parse dueDate for Claim: [" + this.dueDate + "]");
        }
    }

    public Double getAmount() {
        return claimAmount;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getClaimedBy() {
        return claimedBy;
    }

    public String getClaimedFrom() {
        return claimedFrom;
    }

    public void setClaimedBy(String claimedBy) {
        this.claimedBy = claimedBy;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getClaimId() {
        return claimId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
}
