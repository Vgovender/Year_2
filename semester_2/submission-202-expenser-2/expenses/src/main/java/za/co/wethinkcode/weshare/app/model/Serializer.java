package za.co.wethinkcode.weshare.app.model;

import com.google.common.collect.ImmutableList;

public class Serializer {
    ImmutableList<Expense> expenses;

    public Serializer(ImmutableList<Expense> expenses){
        this.expenses = expenses;
    }
}
