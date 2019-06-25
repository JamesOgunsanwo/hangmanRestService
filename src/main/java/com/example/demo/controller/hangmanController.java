package com.example.demo.controller;
import com.example.demo.service.hangmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hangman")
public class hangmanController {

    @Autowired
    private com.example.demo.service.hangmanService hangmanService;

    @RequestMapping("/")
    private String greeting(){
        return ">> This is a simple hangman game \n>> To setup the game type: http://localhost:8080/hangman/setup \n>> To create a word type: http://localhost:8080/hangman/create " +
                "\n>> To make a guess type: http://localhost:8080/hangman/guess/{guess} \n>> To see the game result either look at the console or type: http://localhost:8080/hangman/result " +
                "\n>> You have 5 lives so chose wisely\n>> Good luck";
    }

    @RequestMapping(
            path = "result",
            method = RequestMethod.GET
    )
    private String[] returnResult(){
        return hangmanService.returnStats();
    }

    @RequestMapping(
            path = "guess/{guess}",
            method = RequestMethod.GET
    )
    private String guessMade(@PathVariable String guess) {
        return hangmanService.userGuess(guess);
    }

    @RequestMapping(
            path = "create",
            method = RequestMethod.GET
    )
    private String createWord() {
        return hangmanService.createWord();
    }

    @RequestMapping(
            path = "setup",
            method = RequestMethod.GET
    )
    private String setUpGame() {
        return hangmanService.resetGame();
    }


}
