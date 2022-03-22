package za.co.wethinkcode.mastermind;

import java.util.ArrayList;

public class Mastermind {
    private final String code;
    private final Player player;
    boolean off = false;

    public Mastermind(CodeGenerator generator, Player player){
        this.code = generator.generateCode();
        this.player = player;
    }
    public Mastermind(){
        this(new CodeGenerator(), new Player());
    }

    public String runGame(){
        
        //creating an arraylist of the code
        ArrayList<Character> codeList = new ArrayList<Character>();
        for(int i = 0; i < code.length();i++){
            codeList.add(code.charAt(i));
        }

        while (!player.hasNoChance()){
            String guess = player.getGuess();
            // if (guess.equals("exit") || guess.equals("quit")) {
            //     // System.out.println("im here now");
            //     return "";
            // }
            
            //creating an arraylist of the guess
            ArrayList<Character> guessList = new ArrayList<Character>();
            for(int i = 0; i < guess.length();i++){
                guessList.add(guess.charAt(i));
            }
            
            //checking if its in the correct place
            for (int i = 0; i < codeList.size(); i++) {
                if (codeList.get(i).equals(guessList.get(i))) {
                    player.incr_corr();
                }
            }

            if (player.correct() == 4){
                System.out.println("Number of correct digits in correct place: "+player.correct());
                System.out.println("Number of correct digits not in correct place: "+player.wrong());
                System.out.println("Congratulations! You are a codebreaker!");
                System.out.println("The code was: "+code);
                return "";
            }

            // checking if correct digit wrong place
            for (int i = 0; i < 4; i++) {
                if (guessList.get(i) != (codeList.get(i)) && code.indexOf(guessList.get(i)) !=-1) {
                    player.incr_wrng();
                }
            }

            System.out.println("Number of correct digits in correct place: "+player.correct());
            System.out.println("Number of correct digits not in correct place: "+player.wrong());
            player.lostChance();
            if(player.getChance() != 0){
                System.out.println("Turns left: "+player.getChance());
            }else if(player.getChance() == 0){
                System.out.println("No more turns left.");
                System.out.println("The code was: "+code);
            }
        }
        return "";
    }

    public static void main(String[] args){
        Mastermind game = new Mastermind();
            game.runGame();
    }
}
