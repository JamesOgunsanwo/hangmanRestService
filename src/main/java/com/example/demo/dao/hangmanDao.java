package com.example.demo.dao;

import org.springframework.stereotype.Repository;

@Repository
public class hangmanDao {

    private String[] arrayOfWord = {"test", "word", "computer", "read", "screen", "train", "life", "code", "english"};

    public String[] fetchData(){
        return arrayOfWord;
    }
}
