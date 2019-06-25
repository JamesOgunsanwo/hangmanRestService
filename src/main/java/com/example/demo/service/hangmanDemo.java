/**
 * This is a demo hangman game used to create the logic for the REST service
 * This version has more features and works using the console for user input
 **/
package com.example.demo.service;

import java.util.*;

public class hangmanDemo {
    private static Set<String> wordToSet = new HashSet<>();
    private static Set<String> uniqueGuess = new HashSet<>();
    private static Set<String> uniqueWrongGuess = new HashSet<>();
    private static int lives = 0;
    private static int numberOfGuesses = 0;
    private static String[] wordArray = {"test", "test"};
    private static Random random = new Random();
    private static String wordToGuess = "";
    private static Scanner scanner = new Scanner(System.in);
    private static boolean playGame = true;
    private static String regex = "[0-9]*";

    public static void main(String[] args) {
        setUpGame();
    }

    /* selects a random word from word array */
    private static void createWord(){
        int randomNumber = random.nextInt(wordArray.length - 1);
        wordToGuess = wordArray[randomNumber];
        for(int i = 0; i < wordToGuess.length(); i++){
            wordToSet.add(String.valueOf(wordToGuess.charAt(i)));
        }
    }

    /*  sets up the game and creates the loop to allow the player to continue playing */
    private static void setUpGame(){
        while(playGame) {
            createWord();
            System.out.println("How many lives");
            String numberOfLivesString = scanner.next();
            if (!numberOfLivesString.trim().matches(regex) || numberOfLivesString.isEmpty()) {
                setUpGame();
            } else if (Integer.valueOf(numberOfLivesString) < 1){
                setUpGame();
            }
            lives = Integer.valueOf(numberOfLivesString);
            gameLoop();
            endGame();

        }
    }

    /* game loop */
    private static void gameLoop(){
        if(lives < 0){
            gameOver();
            endGame();
        } else {
            while(lives > 0 ) {
                if (!userWon()) {
                    userGuess();
                } else {
                    System.out.println(">> You win");
                    System.out.println(">> The word was: " + wordToGuess);
                    System.out.println(">> Number of guess: " + numberOfGuesses);
                    return;
                }
            }

            if(lives == 0){
                gameOver();
            }
        }
    }

    /* Checks to see if the two lists match, if they do then the user has won */
    private static boolean userWon(){
        List<String> uniqueGuessList = new ArrayList<>(uniqueGuess);
        List<String> wordToGuess = new ArrayList<>(wordToSet);
        System.out.println(uniqueGuessList);
        System.out.println(wordToGuess);
        if(wordToGuess.equals(uniqueGuessList)){
            return true;
        }
        return false;
    }

    /* game over stats */
    private static void gameOver(){
        System.out.println(">> You Lose");
        System.out.println(">> The word was: " + wordToGuess);
        System.out.println("Number of guess: " + numberOfGuesses);
        System.out.print(">> You guessed correctly: ");
        uniqueGuess.forEach(letter -> System.out.print(letter + " , "));
        System.out.print("\n>> You guessed incorrectly: ");
        uniqueWrongGuess.forEach(letter -> System.out.print(letter + " , "));
        System.out.println("\n");
    }

    /* Reset the game to default settings  */
    private static void resetGame(){
        lives = 0;
        numberOfGuesses = 0;
        wordToGuess = "";
        wordToSet = new HashSet<>();
        uniqueGuess = new HashSet<>();
        uniqueWrongGuess = new HashSet<>();
    }

    /* Reset or end game  */
    private static void endGame(){
        System.out.println(">> Do you want to play again? [Y/N]");
        String playAgain = scanner.next();
        if(playAgain.equals("y")){
            resetGame();
        } else {
            playGame = false;
        }
    }

    /* users guess is processed */
    private static void userGuess(){
        System.out.println(">> What is your guess?");
        String guess = scanner.next();
        if (guess.length() != 1 || guess.matches(regex)){
            userGuess();
        } else if (wordToSet.contains(guess)){
            uniqueGuess.add(guess);
            numberOfGuesses++;
            System.out.println(guess + " is correct");
        } else {
            uniqueWrongGuess.add(guess);
            numberOfGuesses++;
            lives--;
            System.out.println(guess + " is wrong");
        }
    }
}
