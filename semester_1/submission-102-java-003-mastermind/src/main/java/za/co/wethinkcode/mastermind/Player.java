package za.co.wethinkcode.mastermind;

import java.io.InputStream;
import java.util.Scanner;

public class Player {
    private final Scanner inputScanner;
    private int chance = 12;
    private int correct = 0;
    private int wrong = 0;
    public Player(){
        this.inputScanner = new Scanner(System.in);
    }

    public Player(InputStream inputStream){
        this.inputScanner = new Scanner(inputStream);
    }

    /**
     * Gets a guess from user via text console.
     * This must prompt the user to re-enter a guess until a valid 4-digit string is entered, or until the user enters `exit` or `quit`.
     *
     * @return the value entered by the user
     */
    public String getGuess(){
        correct = 0;
        wrong =0;
        
        //getting a guess
        System.out.println("Input 4 digit code:");
        String guess = this.inputScanner.nextLine();
        // System.out.println("lll"+guess);
        //checks if exit or quit
        if(guess.toLowerCase().equals("exit") || guess.toLowerCase().equals("quit")){
            System.exit(0);
        }
        if (guess.length() != 4){
            System.out.println("Please enter exactly 4 digits (each from 1 to 8).");
            return getGuess();
        }
        else if(guess.length() == 4){
            char[] split = guess.toCharArray();
            // check if in rage of 1-8
            for(int i = 0; i < split.length; i++){
                if(split[i] <= '0' || split[i] >= '9'){
                    System.out.println("Please enter exactly 4 digits (each from 1 to 8).");
                    while(inputScanner.hasNextLine()) {
                    return getGuess();
                    }
                }
            }
        }
        return guess;
    }

    public int getChance(){
        return chance;
    }

    public void lostChance() {
        if (!this.hasNoChance()) {                                                                     
            this.chance--;                                                                             
        }                                                                                 
    }

    public boolean hasNoChance() {
        return this.getChance() == 0;                                                                  
    }

    public void incr_corr() {
        correct++;                                                                               
    }

    public int correct(){
        return correct;
    }

    public int wrong(){
        return wrong;
    }

    public void incr_wrng() {
        wrong++;                                                                               
    }

}
//test that the user inputed exactly 4 digits
