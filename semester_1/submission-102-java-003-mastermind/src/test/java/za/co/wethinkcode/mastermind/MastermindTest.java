package za.co.wethinkcode.mastermind;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.Test;

public class MastermindTest {
    //check if the code is 4 long
    @Test public void code_length(){
        boolean result = true;
        CodeGenerator generatecode = new CodeGenerator();
        String code = generatecode.generateCode();
        if (code.length() != 4){
            result = false;
        }
        assertTrue(result);
    }
    //checks if the code is all numbers
    @Test public void code_is_digit(){
        boolean result = true;
        CodeGenerator generatecode = new CodeGenerator();
        String code = generatecode.generateCode();
        char[] split = code.toCharArray();
        for(int i = 0 ;i < split.length ;i++){
            if (!Character.isDigit(split[i])){
                result = false;
            }
        }
        assertTrue(result);
    }

    //checks if the code is between 1-8
    @Test public void code_is_valid(){
        boolean result = true;
        CodeGenerator generatecode = new CodeGenerator();
        String code = generatecode.generateCode();
        char[] split = code.toCharArray();
        for(int i = 0;i < split.length ;i++){
            if(split[i] == '0' || split[i] == '9'){
                result = false;
            }
        }
        assertTrue(result);
    }
    // check if the guess is 4 digits
    @Test public void guess_length(){
        boolean result = true;
        Player player = new Player(new ByteArrayInputStream("1234\n".getBytes()));
        String guess = player.getGuess();
        if (guess.length() != 4){
            result = false;
        }
        assertTrue(result);
    }
    //
    @Test public void guess_isdigit(){
        boolean result = true;
        Player player = new Player(new ByteArrayInputStream("12df\n".getBytes()));
        String guess = player.getGuess();
        char[] split = guess.toCharArray();
        for(int i = 0 ;i < split.length ;i++){
            if (!Character.isDigit(split[i])){
                result = false;
            }
        }
        assertFalse(result);
    }

    @Test public void guess_is_valid(){
        boolean result = true;
        Player player = new Player(new ByteArrayInputStream("1234\n".getBytes()));
        String guess = player.getGuess();
        char[] split = guess.toCharArray();
        for(int i = 0;i < split.length ;i++){
            if(split[i] == '0' || split[i] == '9'){
                result = false;
            }
        }
        assertTrue(result);
    }
}