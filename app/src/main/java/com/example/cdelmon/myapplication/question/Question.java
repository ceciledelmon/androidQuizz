package com.example.cdelmon.myapplication.question;

import java.util.ArrayList;

/**
 * Created by cdelmon on 07/03/2017.
 */

public class Question {
    public String text;
    public String answer;
    public String difficulty;
    public String category;
    public ArrayList incorrect;

    public Question(String text, String difficulty, String answer, String category, ArrayList incorrect) {
        this.text = text;
        this.answer = answer;
        this.difficulty = difficulty;
        this.category = category;
        this.incorrect = incorrect;
    }
}
