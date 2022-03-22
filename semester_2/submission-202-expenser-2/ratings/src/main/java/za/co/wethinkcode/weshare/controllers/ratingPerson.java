package za.co.wethinkcode.weshare.controllers;

import za.co.wethinkcode.weshare.util.Strings;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class ratingPerson implements Comparable<ratingPerson>{

    private String when = "00:00";
    private String who = "";
    private Double amount  = 0.0;
    private String email ;


    public ratingPerson(String email){

        this.email = checkNotNull( email );

    }

    public ratingPerson(String email, double amount, String timestamp_of_lastUpdate){
        this.email = email;
        this.who = email;
        this.amount = amount;
        this.when = timestamp_of_lastUpdate;
    }
    public int compareTo(ratingPerson o){
        return (amount).compareTo(o.getAmount());
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setWho(String timestamp_of_lastUpdate) {
        this.when = timestamp_of_lastUpdate;
    }

    public void setWhen(String timestamp_of_lastUpdate) {
        this.when = timestamp_of_lastUpdate;
    }

    public Double getAmount() {
        return amount;
    }

    public String getWhen() {
        return when;
    }

    public String getWho() {
        return who;
    }

//    public String getEmail() {
//        return email;
//    }

//    public String getName(){
//        String pseudonym = this.email.split( "@" )[ 0 ];
//        return Strings.capitaliseFirstLetter( pseudonym );
//    }

    @Override
    public int hashCode(){
        return Objects.hash( email );
    }

    @Override
    public boolean equals( Object o ){
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        ratingPerson person = (ratingPerson) o;
        return email.equalsIgnoreCase( person.email );
    }

    @Override
    public String toString(){
        return "Person{" +
                "id='" + email + '\'' +
                '}';
    }
}
