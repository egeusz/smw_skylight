package com.smw.skylight ;

public class Response {
	
	int id = 0;
	String text = ""; 
	int answer = 0;
	String answertext = ""; 
	

	public Response()
	{
		
	}
	
	public Response(int _id, String _text)
	{
		id = _id; 
		text = _text; 
	}
	
	// getting id
    public int getId(){
        return this.id;
    }
    // setting id
    public void setId(int _id){
        this.id = _id;
    }
	
    // getting text
    public String getText(){
        return this.text;
    }
    // setting text
    public void setText(String _text){
        this.text = _text;
    }
    
    public int getAnswer()
    {
    	return this.answer; 
    }
    public void setAnswer(int _answer)
    {
    	this.answer = _answer;
    }
    
    public String getAnswerText()
    {
    	return this.answertext; 
    }
    public void setAnswerText(String _answertext)
    {
    	this.answertext = _answertext;
    }
 
    
    
   

    public String toShortString()
    {
    	return "["+id+"]  "+text+" , "+"["+answer+"] "+answertext; 
    }
}
