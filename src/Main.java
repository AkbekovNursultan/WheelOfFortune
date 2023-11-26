import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random ran = new Random();
        String[] words = {"PARROT", "COSMOS", "SPIDER", "FLY", "MONACO", "LIGHT", "PAPAOUTAI", "MADAGASCAR", "THERMOMETER", "CHOCOLATE"};
        String[] wordsHints = {"For a long time people thought that this animal could talk", "Freezing, far and lonely place",
                            "Falsely, considered insect by many", "Annoying", "Country inside France",
                            "This word is can be used as noun, adjective and verb without changing it",
                            "The most popular song of artist Stromae", "Lion, zebra, hippo and giraffe",
                            "Celsius", "Willy Wonka"};
        ArrayList<String>  playersList = new ArrayList<>();
        playersList = addPlayers(playersList, sc);
        int[] playersScore = new int[playersList.size()];
        Arrays.fill(playersScore, 0);
        int randomChoice = ran.nextInt(0, words.length);
        String chosenWord = words[randomChoice];
        String chosenWordHint = wordsHints[randomChoice];
        cleanScreen();
        //
        char[] correctLetters = chosenWord.toCharArray();
        char[] hiddenLetters = new char[correctLetters.length];
        Arrays.fill(hiddenLetters, '*');
        int playerTurn = 0;
        while(true) {
            System.out.println("          Your turn " + playersList.get(playerTurn) + "!\n");
            showScoreboard(playersList, playersScore);
            drawGameBoard(correctLetters, hiddenLetters, chosenWordHint);
            System.out.println("          Make your guess: ");
            String guessWord = sc.next();
            cleanScreen();
            if (guessWord.length() < 2) {
                char guess = guessWord.charAt(0);
                playerTurn = checkGuessedLetter(playersList, playersScore, correctLetters, hiddenLetters, guess, playerTurn);
                if (playerTurn == playersList.size()) {
                    playerTurn = 0;
                }
                if(isOneLetterLeft(hiddenLetters, correctLetters)){
                    showScoreboard(playersList, playersScore);
                    drawGameBoard(correctLetters, hiddenLetters, chosenWordHint);
                    System.out.println("\n          ENDGAME!!!!!\n   " + playersList.get(playerTurn) + ", can you guess what word it is?");
                    guessWord = sc.next();
                    finalStage(guessWord, chosenWord, chosenWordHint, playersList, playersScore, playerTurn, correctLetters, hiddenLetters);
                }
                checkPoints(sc, playersList, playersScore, playerTurn, chosenWord, chosenWordHint, correctLetters, hiddenLetters);
            } else if(guessWord.toUpperCase().equals(chosenWord)) {
                cleanScreen();
                finalStage(guessWord, chosenWord, chosenWordHint, playersList, playersScore, playerTurn, correctLetters, hiddenLetters);
                System.exit(0);
            } else{
                System.out.println("   Nice try, but that is not our hidden word.\n");
                playerTurn++;
                if (playerTurn == playersList.size()) {
                    playerTurn = 0;
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
            System.out.println("\n------------------------------------");
        }
        System.out.println();
    }

    static void drawGameBoard(char[] correctLetters, char[] hiddenLetters, String chosenWordHint){
        for (char hiddenLetter : hiddenLetters) {
            System.out.print("  " + hiddenLetter + "     ");
        }
        System.out.println();
        for(int i = 0; i < correctLetters.length; i++){
            System.out.print("|___|   ");
        }
        System.out.println();
        System.out.println("Hint: " + chosenWordHint);
        System.out.println();
    }

    static int checkGuessedLetter(ArrayList<String> playersList, int[] playersScore, char[] correctLetters,char[] hiddenLetters, char guess, int playerTurn) {
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
        return playerTurn;
    }

    static void checkPoints(Scanner sc, ArrayList<String> playersList, int[] playersScore, int playerTurn, String chosenWord, String chosenWordHint, char[] correctLetters, char[] hiddenLetters){
        if(playersScore[playerTurn] > chosenWord.length() * 50){
            showScoreboard(playersList, playersScore);
            drawGameBoard(correctLetters, hiddenLetters, chosenWordHint);
            System.out.println("\n          ENDGAME!!!!!\n   " + playersList.get(playerTurn) + ", can you guess what word it is?");
            String guessWord = sc.next();
            finalStage(guessWord, chosenWord, chosenWordHint, playersList, playersScore, playerTurn, correctLetters, hiddenLetters);
        } else if (Arrays.equals(hiddenLetters, correctLetters)) {
            cleanScreen();
            showTheAnswer(correctLetters, playersList, playersScore);
            if(isItDraw(playersScore) == false) {
                System.out.println();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("\n     The WINNER is " + playersList.get(getIndexOfLargestScore(playersScore)).toUpperCase() + "!!!!!\n       With the highest score!!!!!");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.exit(0);
            } else if(isItDraw(playersScore) == true) {
                System.out.println();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("\n           ~~DRAW~~");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.exit(0);
            }
            }
        }

    static boolean isOneLetterLeft(char[] hiddenLetters, char[] correctLetters){
        ArrayList<Integer> indexOfLettersNotFound = new ArrayList<>();
        for(int i = 0; i < correctLetters.length; i++){
            if(hiddenLetters[i] == '*'){
                indexOfLettersNotFound.add(i);
            }
        }
        char missingLetter = correctLetters[indexOfLettersNotFound.get(0)];
        for(int i = 1 ; i < indexOfLettersNotFound.size(); i++){
            if(correctLetters[indexOfLettersNotFound.get(i)] != missingLetter){
                return false;
            }
        }

        return true;
    }

    static void finalStage(String guessWord, String chosenWord,String chosenWordHint, ArrayList<String> playersList, int[] playersScore, int playerTurn, char[] correctLetters, char[] hiddenLetters){
        cleanScreen();
        Scanner sc = new Scanner(System.in);
        if (guessWord.toUpperCase().equals(chosenWord)) {
            System.out.println("Congratulations, " + playersList.get(playerTurn) + "! You have guessed the word!\n\n");
            playersScore[playerTurn] = 9999;
            showTheAnswer(correctLetters, playersList, playersScore);
            System.out.println();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("     The WINNER is " + playersList.get(playerTurn).toUpperCase() + "!!!!!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.exit(0);
        } else {
            System.out.println("   Nice try, but that is not our hidden word.\n");
            System.out.println("   Since, " + playersList.get(playerTurn) + "'s points are enough to win the game" +
                    "\n   if other players won't be able to guess the word he will become winner!\n\n");
            showScoreboard(playersList, playersScore);
            drawGameBoard(correctLetters, hiddenLetters, chosenWordHint);
            for(int i = 0; i < playersList.size()-1; i++){
                playerTurn++;
                if(playerTurn == playersList.size()){
                    playerTurn = 0;
                }
                System.out.println("       " + playersList.get(playerTurn) + ", can you guess this word?");
                guessWord = sc.next();
                if(guessWord.equals(chosenWord)){
                    System.out.println("Congratulations, " + playersList.get(playerTurn) + "! You have guessed the word!");
                    playersScore[playerTurn] = 9999;
                    showTheAnswer(correctLetters, playersList, playersScore);
                    System.out.println();
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("     The WINNER is " + playersList.get(playerTurn).toUpperCase() + "!!!!!");
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.exit(0);
                }
                else{
                    System.out.println("\n        Unfortunately that is wrong answer");
                }

            }
            cleanScreen();
            showTheAnswer(correctLetters, playersList, playersScore);
            ////////////
            if(isItDraw(playersScore) == false) {
                System.out.println();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("\n     The WINNER is " + playersList.get(getIndexOfLargestScore(playersScore)).toUpperCase() + "!!!!!\n       With the highest score!!!!!");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.exit(0);
            } else if(isItDraw(playersScore) == true) {
                System.out.println();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("\n           ~~DRAW~~");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.exit(0);
            }
        }
    }

    static void showTheAnswer(char[] correctLetters, ArrayList<String> playersList, int[] playersScore){
        showScoreboard(playersList,playersScore);
        System.out.println();
        System.out.println("    The word was:");
        System.out.println();
        for (char correctLetter : correctLetters) {
            System.out.print("  " + correctLetter + "     ");
        }
        System.out.println();
        for (int i = 0; i < correctLetters.length; i++) {
            System.out.print("|___|   ");
        }
    }
    static boolean isItDraw(int[] playersScore){
        int maxScore = playersScore[0];
        for(int i = 1; i < playersScore.length; i++){
            if(maxScore < playersScore[i]){
                maxScore = playersScore[i];
            } else if (maxScore == playersScore[i]) {
                return true;
            }
        }
        return false;
    }

    static int getIndexOfLargestScore( int[] array ) {
        if ( array == null || array.length == 0 ) return -1; // null or empty

        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) largest = i;
        }
        return largest; // position of the first largest found
    }

    static void cleanScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n\n\n\n\n\n\n");

    }
}