package za.co.wethinkcode.mastermind;

import java.util.Random;


public class CodeGenerator {
    private final Random random;

    public CodeGenerator(){
        this.random = new Random();
    }

    public CodeGenerator(Random random){
        this.random = random;
    }

    /**
     * Generates a random 4 digit code, using this.random, where each digit is in the range 1 to 8 only.
     * Duplicated digits are allowed.
     * @return the generated 4-digit code
     */
    public String generateCode(){
        String sequence = "";
        for (int i=0;i<4;i++){

            //generate random integer
            int x = random.nextInt(8)+1;
            sequence = sequence + Integer.toString(x);
        }
        System.out.println("4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.");
        return sequence;
    }
}
