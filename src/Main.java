import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random ran = new Random();
        String[] words = {"PARROT", "COSMOS", "SPIDER", "FLY", "MONACO", "LIGHT", "PAPAOUTAI", "MADAGASCAR"};
        String[] wordsHints = {"For a long time people that this animal could talk", "Cold, Far and Lonely place",
                            "Falsely, considered insect by many", "Annoying", "Country inside France",
                            "This word is can be used as noun, adjective and verb without changing it",
                            "The most popular song of artist Stromae", "Lion, zebra, hippo and giraffe"};
        ArrayList<String>  playersList = new ArrayList<>();
        playersList = addPlayers(playersList, sc);
        int[] playersScore = new int[playersList.size()];
        Arrays.fill(playersScore, 0);
        int randomChoice = ran.nextInt(0, words.length);
        String chosenWord = words[randomChoice];
        String chosenWordHint = wordsHints[randomChoice];
        cleanScreen();
        System.out.println(chosenWord);
        //
        char[] correctLetters = chosenWord.toCharArray();
        char[] hiddenLetters = new char[correctLetters.length];
        Arrays.fill(hiddenLetters, '*');
        int playerTurn = 0;
        while(true) {
            if (playerTurn == playersList.size()) {
                playerTurn = 0;
            }
            System.out.println("          Your turn " + playersList.get(playerTurn) + "!\n");
            showScoreboard(playersList, playersScore);
            drawGameBoard(correctLetters, hiddenLetters, chosenWordHint);
            System.out.println("          Make your guess: ");
            String guessWord = sc.next();
            cleanScreen();
            if (guessWord.length() < 2) {
                char guess = guessWord.charAt(0);
                playerTurn = checkGuess(playersList, playersScore, correctLetters, hiddenLetters, guess, playerTurn);
            } else {
                if (guessWord.toUpperCase().equals(chosenWord)) {
                    System.out.println("Congratulations, " + playersList.get(playerTurn) + "! You have guessed the word!");
                    showTheAnswer(correctLetters, playersList, playersScore);
                    System.out.println("\n\n     The WINNER is " + playersList.get(playerTurn).toUpperCase() + "!!!!!");

                    System.exit(0);
                } else {
                    System.out.println("Nice try, but that is not our hidden word.");
                }
            }
        }
    }
    static ArrayList<String> addPlayers(ArrayList<String> playersList, Scanner sc){
        System.out.println("Enter the number of players: ");
        int playersListSize = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < playersListSize; i++){
            System.out.println("Player " + (i+1) + ": ");
            playersList.add(sc.nextLine());
        }
        return makeNewOrder(playersList);
    }
    static ArrayList<String> makeNewOrder(ArrayList<String> playersList){
        ArrayList<String> newOrder = new ArrayList<>();
        Random ran = new Random();
        while(playersList.size() > 0){
            int playerOrder = ran.nextInt(playersList.size());
            newOrder.add(playersList.get(playerOrder));
            playersList.remove(playerOrder);
        }
        return newOrder;
    }
    static void showScoreboard(ArrayList<String> playersList, int[] playersScore){
        for(int i = 0; i < playersList.size(); i++){
            System.out.print("   " + playersList.get(i) + " -- " + playersScore[i] + " points");
            System.out.println();
        }
        System.out.println();
    }

    static void drawGameBoard(char[] correctLetters, char[] hiddenLetters, String chosenWordHint){
        for (char hiddenLetter : hiddenLetters) {
            System.out.print("  " + hiddenLetter + "     ");
        }
        System.out.println();
        for(int i = 0; i < correctLetters.length; i++){
            System.out.print("=====   ");
        }
        System.out.println();
        System.out.println("Hint: " + chosenWordHint);
        System.out.println();
    }
    static int checkGuess(ArrayList<String> playersList, int[] playersScore, char[] correctLetters,char[] hiddenLetters, char guess, int playerTurn) {
        int lettersFound = 0;
        for (int i = 0; i < correctLetters.length; i++) {
            if (Character.toUpperCase(guess) == correctLetters[i] && hiddenLetters[i] != Character.toUpperCase(guess)) {
                hiddenLetters[i] = correctLetters[i];
                lettersFound++;
            } else if (Character.toUpperCase(guess) == hiddenLetters[i]) {
                System.out.println("      This letter was already guessed!\n");
                lettersFound = -1;
                break;
            }
        }
        if(lettersFound == 0){
            System.out.println("      There is no '"+ Character.toUpperCase(guess)+ "' in this word.\n");
            playerTurn++;
        } else if(lettersFound == 1) {
            playersScore[playerTurn] += 100;
            System.out.println("      There is 1 '" + Character.toUpperCase(guess) + "' letter!\n          +100 points");
        } else if(lettersFound > 1){
            playersScore[playerTurn] += 100 * lettersFound;
            System.out.println("  There are " + lettersFound + " '" + Character.toUpperCase(guess) + "' letters in this word!\n          +" + (lettersFound * 100) + " points");
        }
        if(Arrays.equals(correctLetters, hiddenLetters)){
            cleanScreen();
            cleanScreen();
            showTheAnswer(correctLetters, playersList, playersScore);
            System.exit(0);
        }
        return playerTurn;
    }
    static void showTheAnswer(char[] correctLetters, ArrayList<String> playersList, int[] playersScore){
        showScoreboard(playersList,playersScore);
        System.out.println();
        for (char correctLetter : correctLetters) {
            System.out.print("  " + correctLetter + "     ");
        }
        System.out.println();
        for (int i = 0; i < correctLetters.length; i++) {
            System.out.print("=====   ");
        }
    }
    static void cleanScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n\n\n\n\n\n\n");

    }
}