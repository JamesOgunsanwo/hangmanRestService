package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class hangmanService {

    @Autowired
    private com.example.demo.dao.hangmanDao hangmanDao;

    private Set<String> wordToSet = new HashSet<>();
    private Set<String> uniqueGuess = new HashSet<>();
    private Set<String> uniqueWrongGuess = new HashSet<>();
    private int lives = 5;
    private int numberOfGuesses = 0;
    private String[] wordArray = {};
    private Random random = new Random();
    private String wordToGuess = "";

    /* selects random word from word array */
    public String createWord(){
        // fetches data from dao
        wordArray = hangmanDao.fetchData();
        int randomNumber = random.nextInt(wordArray.length - 1);
        wordToGuess = wordArray[randomNumber];
        for(int i = 0; i < wordToGuess.length(); i++){
            wordToSet.add(String.valueOf(wordToGuess.charAt(i)));
        }
        return ">> A word has been selected";
    }

    /* userGuess is processed */
    public String userGuess(String guess){
        System.out.println(">> What is your guess?");
        if (wordToSet.contains(guess)){
            uniqueGuess.add(guess);
            numberOfGuesses++;
            System.out.println(guess + " is correct");
        } else {
            uniqueWrongGuess.add(guess);
            numberOfGuesses++;
            lives--;
            System.out.println(guess + " is wrong");
        }

        //Tells user if they have lost or if they should keep playing
        if(lives == 0){
            gameOver();
            return "Loser | The word was :" + wordToGuess ;
        } else if(userWon()){
            winner();
            return "Winner | The word was :" + wordToGuess ;
        } else {
            return ">> keep playing | Lives: " + lives + " | Number of guess: " + numberOfGuesses ;
        }
    }

    /* Returns stats  */
    public String[] returnStats(){
        String wordSetString  = "Word to guess: " + wordToSet.toString();
        String uniqueGuessString = "Unique guess: " + uniqueGuess.toString();
        String uniqueWrongGuessString = "Unique wrong: " + uniqueWrongGuess.toString();
        String livesString = "Lives: " + lives;
        return new String[]{wordSetString,uniqueGuessString ,uniqueWrongGuessString,livesString};
    }

    /* game over */
    private void gameOver(){
        System.out.println(">> You Lose");
        System.out.println(">> The word was: " + wordToGuess);
        System.out.println("Number of guess: " + numberOfGuesses);
        System.out.print(">> You guessed correctly: ");
        uniqueGuess.forEach(letter -> System.out.print(letter + " , "));
        System.out.print("\n>> You guessed incorrectly: ");
        uniqueWrongGuess.forEach(letter -> System.out.print(letter + " , "));
        System.out.println("\n");
    }

    private boolean userWon(){
        List<String> uniqueGuessList = new ArrayList<>(uniqueGuess);
        List<String> wordToGuess = new ArrayList<>(wordToSet);
        return wordToGuess.equals(uniqueGuessList);
    }

    public String resetGame(){
        lives = 5;
        numberOfGuesses = 0;
        wordToGuess = "";
        wordToSet = new HashSet<>();
        uniqueGuess = new HashSet<>();
        uniqueWrongGuess = new HashSet<>();
        return ">> Game is ready to play ";
    }

    private void winner(){
        System.out.println(">> You win");
        System.out.println(">> The word was: " + wordToGuess);
        System.out.println(">> Number of guess: " + numberOfGuesses);
    }

}
