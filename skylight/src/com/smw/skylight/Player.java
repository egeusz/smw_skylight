package com.smw.skylight ;

public class Player {
	 
    //private variables
    String phone_number = "";
    int currentquestion = 0;
    String lastanswer = ""; 
    int tries = 0; 
    // Empty constructor
    public Player(){
 
    }
    // constructor
    public Player(String _phone_number, int _currentquestion){
        this.phone_number = _phone_number;
        this.currentquestion = _currentquestion;
    }
 
    
    // getting phone number
    public String getPhoneNumber(){
        return this.phone_number;
    }
    // setting phone number
    public void setPhoneNumber(String _phone_number){
        this.phone_number = _phone_number;
    }
    
    // getting phone number
    public int getCurrentQuestion(){
        return currentquestion;
    }
    // setting phone number
    public void setCurrentQuestion(int _currentquestion){
       currentquestion = _currentquestion;
    }
    
    // getting phone number
    public String getLastAnswer(){
        return lastanswer;
    }
    // setting phone number
    public void setLastAnswer(String _lastanswer){
        lastanswer = _lastanswer;
    }
    
    // getting phone number
    public int getTries(){
        return tries;
    }
    // setting phone number
    public void setTries(int _tries){
        tries = _tries;
    }

    
    public String toShortString()
    {
    	return getPhoneNumber() + " [" +getCurrentQuestion() +"][" + getLastAnswer() +"][" + getTries() +"]";
    
    }
    
}
