/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class candidateExam {
    public String question; 
    public ArrayList<String> candidateAnswer = new ArrayList();

    public candidateExam(){
        question = "";
    
        
    }
    public candidateExam(String quest, ArrayList<String> candAns){
      
        this.question = quest; 
        this.candidateAnswer = candAns;
            
    }
    
}
