package za.co.wethinkcode.weshare.ratings;

import za.co.wethinkcode.weshare.util.Strings;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class ratingPerson {

    public String when = "00:00";
    public String who = "";
    public Double amount  = 0.0;
    public String email ;


    public ratingPerson(String email){

        this.email = checkNotNull( email );

    }

    public ratingPerson(double amount, String who, String timestamp_of_lastUpdate){;
        this.who = who;
        this.amount = amount;
        this.when = timestamp_of_lastUpdate;
        this.email = who;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setWho(String who) {
        this.who = who;
    }

    public void setWhen(String timestamp_of_lastUpdate) {
        this.when = timestamp_of_lastUpdate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getWhen(){
        return when;
    }

    public String getWho() {
        return who;
    }

    public Double getAmount() {
        return amount;
    }

    public String getName(){
        String pseudonym = this.email.split( "@" )[ 0 ];
        return Strings.capitaliseFirstLetter( pseudonym );
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
