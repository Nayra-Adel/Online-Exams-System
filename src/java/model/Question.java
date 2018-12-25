/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Aya Essam
 */
public class Question {
    public int question_id;
    public String sentence;
    public String topic;
    public ArrayList<String> answers = new ArrayList();
    public String correctAnswer;
    
    public boolean in = false;

    public Question() {
        question_id=0;
        sentence="";
        topic="";
        correctAnswer="";
    }

    public Question(int question_id, String sentence, String correctAnswer, String topic) {
        this.question_id = question_id;
        this.sentence = sentence;
        this.correctAnswer = correctAnswer;
        this.topic = topic;
    }

    public Question(int question_id, String sentence, String topic) {
        this.question_id = question_id;
        this.sentence = sentence;
        this.topic = topic;
    }
    
    public void setAnswers(ArrayList<String> answers, String correctAnswer)
    {
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
