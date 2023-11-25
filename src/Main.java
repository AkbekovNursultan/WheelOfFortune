import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random ran = new Random();
        String[] words = {"PARROT", "WATER", "COSMOS", "SPIDER", "FLY", "MONACO", "LIGHT", "PAPAOUTAI", "DONATELLO"};
        String chosenWord = words[ran.nextInt(0, words.length)];
        System.out.println(chosenWord);
        char[] correctLetters = chosenWord.toCharArray();
        char[] hiddenLetters = new char[correctLetters.length];
        Arrays.fill(hiddenLetters, '*');
        while(true){
            System.out.println("Guess the letter: ");
            String guessWord = sc.next();
            if(guessWord.length() < 2) {
                char guess = guessWord.charAt(0);
                showLetter(correctLetters, hiddenLetters, guess);
            }else {
                if(guessWord.toUpperCase().equals(chosenWord)){
                    System.out.println("Hooray");
                    System.exit(0);
                }
            }
        }

    }
    static void showLetter(char[] correctLetters,char[] hiddenLetters, char guess){
        int lettersFound = 0;
        for(int i = 0; i < correctLetters.length; i++){
            if(Character.toUpperCase(guess) == correctLetters[i]){
                hiddenLetters[i] = correctLetters[i];
                lettersFound++;
            }
        }
        for (char hiddenLetter : hiddenLetters) {
            System.out.print("  " + hiddenLetter + "     ");
        }
        System.out.println();
        for(int i = 0; i < correctLetters.length; i++){
            System.out.print("=====   ");
        }
        System.out.println();
        System.out.println();
        if(lettersFound == 0){
            System.out.println("There is no '"+ Character.toUpperCase(guess)+ "' in this word.");
        } else if(lettersFound == 1) {
            System.out.println("There is 1 '" + Character.toUpperCase(guess) + "' letter!");
        } else{
            System.out.println("There are " + lettersFound + " '" + Character.toUpperCase(guess) + "' letters in this word!");
        }

    }
    static void emptyBox(){
        for(int i = 0; i < 10; i++){
            System.out.print(" ");
        }

    }
    static void drawLines(char[] correctLetters){
        for(int i = 0; i < correctLetters.length; i++){
            System.out.print("=====   ");
        }
        System.out.println();
    }


    static void cleanScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n\n\n\n\n\n\n");

    }
}