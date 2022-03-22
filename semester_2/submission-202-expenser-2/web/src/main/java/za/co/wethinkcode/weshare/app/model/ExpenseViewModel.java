package za.co.wethinkcode.weshare.app.model;

import java.time.LocalDate;
import java.util.UUID;

public class ExpenseViewModel {
    private double amount;
    private Double nettAmount;
    private String id;
    private String description;
    private String date;


    public ExpenseViewModel(Double amount, Double nettAmount, String id, String paidBy, String date, String description){
        this.amount = amount;
        this.nettAmount = nettAmount;
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public Double getNettAmount(){
        return this.nettAmount;
    }

    public String getDate() { return date; }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }
}
