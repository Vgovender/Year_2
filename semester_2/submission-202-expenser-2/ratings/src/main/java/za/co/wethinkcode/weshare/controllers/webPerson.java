package za.co.wethinkcode.weshare.controllers;

import za.co.wethinkcode.weshare.util.Strings;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class webPerson {

    private String email;
    private int id;
    private String name;

    public webPerson(String email,int id,String name){
        this.email = email;
        this.id = id;
        this.name = name;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getId(){
        return this.id;
    }
    public String getEmail(){
        return this.email;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        String pseudonym = this.email.split( "@" )[ 0 ];
        return Strings.capitaliseFirstLetter( pseudonym );
    }

    @Override
    public int hashCode(){
        return Objects.hash( email );
    }

    @Override
    public boolean equals( Object o ){
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        webPerson person = (webPerson) o;
        return email.equalsIgnoreCase( person.email );
    }

    @Override
    public String toString(){
        return "Person{" +
                "id='" + email + '\'' +
                '}';
    }
}
