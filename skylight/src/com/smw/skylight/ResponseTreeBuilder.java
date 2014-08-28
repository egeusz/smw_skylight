package com.smw.skylight ;

import java.util.Locale;

public class ResponseTreeBuilder {

	DatabaseHandler db; 
	
	
	public ResponseTreeBuilder(DatabaseHandler _db)
	{
		db = _db; 
	}
	
	public boolean PopulateResponses(String _treeID)
    {
    	if(_treeID.toLowerCase(Locale.getDefault()).contains("skylight"))
    	{
    		PopulateSkylightResponses(); 
    		return true; 
    	}
    	else if(_treeID.toLowerCase(Locale.getDefault()).contains("test"))
    	{
    		PopulateTestResponses(); 
    		return true; 
    	}
    	else 
    	{
    		PopulateSkylightResponses(); 
    		return true; 
    	}
    	
    
    	
    }  
    
	public void PopulateSkylightResponses()
    {
		//-1 = lower //1 = higher 
		AddResponse( 1,"How much energy does a square meter of the ground receive from the sun per hour? More or less than 500 watts?", 1 , "More. 1000W of sunlight per square meter on a cloudless day. Thats 1kWh of sunlight per meter per hour.");
		AddResponse( 2,"Every year over 700,000 people visit the Albuquerque International Balloon Fiesta. Is that more or less visitors than the population of Albuquerque?", 1 , "More. The population of Albuquerque is about 555,400. In 2013, 856,986 visitors attended the park.");
		AddResponse( 3,"The Albuquerque Balloon Fiesta Park is about 54 football fields in size. Approximately 288,954 square meters. How many kW of sunlight energy does the park receive at noon on a clear day? more or less than 100,000 kW?", 1 , "More. There are approximately 1000 watts (1 kiloWatt) of sunlight per square meter on a clear day at noon.  1kW x  288,954 square meters equals 288,954 kW of power.");
		AddResponse( 4,"If the entire surface of the Balloon Fiesta Park were covered with solar panels, it would generate approximately 43,400 kWatts at noon on a cloudless day. How many refrigerators could that power? more or less than 60,000?", -1 , " Less, it's nearly 60,000, but the average refrigerator is about 0.750 kWatts so approximately 58,000 refrigerators could be powered at noon on a cloudless day.");
		AddResponse( 5,"Hot air balloons use propane as fuel. Does propane contain more or less energy per gallon than gasoline?", -1 , "Less, propane contains about 27 kWh and gasoline contains about 34 kWh.");
		AddResponse( 6,"How many gallons of gasoline would be equivalent to one hour of energy from a Balloon Fiesta Park-sized solar array at noon on a cloudless day? More or Less than 10,000 gallons?", -1 , "Less. There are 33.41 kWh of energy in 1 gallon of gas. That's 1297.3 equivalent gallons of gasoline produced.");
		
		AddResponse( 7,"Do you use more or less energy than the average refrigerator per day? ****", -1 , "Less. A modern refrigerator consumes 16kWh of energy per day and the average human consumes 1.8kWh of energy per day or Basal Metabolic Rate (BMR); the amount of energy you'd burn if you stayed in bed all day.");
		AddResponse( 8,"Does the average human consume more or less energy per day than that in a gallon of gasoline?", -1 , "Much less. A gallon of gasoline contains 33.41kWh of energy while the average human uses 1.8kWh a day. If humans could eat gasoline 1 gallon would feed 18 people for a day.");
		AddResponse( 9,"Does a car that gets 15mpg use more or less energy than one that gets 20mpg to travel one mile?", 1 , "More. A car that gets 15mpg uses 2.2kWh of gasoline to travel one mile while a car that 20mpg uses 1.7kWh. An average car consumes about the same amount of energy to travel 1 mile as you do in an entire day! 1.8 kWh");
		AddResponse( 10,"Does your body produce more or less pounds of CO2 per kWh of energy than an average car?", 1 , "You produce more! A car that gets produces 0.587lb of CO2 per kWh, the average human produces 2.3lb of CO2 a day, so 2.3lb a day / 1.8kWh a day = 1.2 lb of CO2 per kWh.");
    }
	
	public void PopulateTestResponses()
    {
		//-1 = lower //1 = higher 
		AddResponse( 1,"How much energy does a square meter of the ground receive from the sun per hour? More or less than 500 watts?", 1 , "More. 1000W of sunlight per square meter on a cloudless day. Thats 1kWh of sunlight per meter per hour.");
		AddResponse( 2,"Every year over 700,000 people visit the Albuquerque International Balloon Fiesta. Is that more or less visitors than the population of Albuquerque?", 1 , "More. The population of Albuquerque is about 555,400. In 2013, 856,986 visitors attended the park.");
		AddResponse( 3,"The Albuquerque Balloon Fiesta Park is about 54 football fields in size. Approximately 288,954 square meters. How many kW of sunlight energy does the park receive at noon on a clear day? more or less than 100,000 kW?", 1 , "More. There are approximately 1000 watts (1 kiloWatt) of sunlight per square meter on a clear day at noon.  1kW x  288,954 square meters equals 288,954 kW of power.");
		AddResponse( 4,"If the entire surface of the Balloon Fiesta Park were covered with solar panels, it would generate approximately 43,400 kWatts at noon on a cloudless day. How many refrigerators could that power? more or less than 60,000?", -1 , " Less, it's nearly 60,000, but the average refrigerator is about 0.750 kWatts so approximately 58,000 refrigerators could be powered at noon on a cloudless day.");
		AddResponse( 5,"Hot air balloons use propane as fuel. Does propane contain more or less energy per gallon than gasoline?", -1 , "Less, propane contains about 27 kWh and gasoline contains about 34 kWh.");
		AddResponse( 6,"How many gallons of gasoline would be equivalent to one hour of energy from a Balloon Fiesta Park-sized solar array at noon on a cloudless day? More or Less than 10,000 gallons?", -1 , "Less. There are 33.41 kWh of energy in 1 gallon of gas. That's 1297.3 equivalent gallons of gasoline produced.");
		
		AddResponse( 7,"Do you use more or less energy than the average refrigerator per day? ****", -1 , "Less. A modern refrigerator consumes 16kWh of energy per day and the average human consumes 1.8kWh of energy per day or Basal Metabolic Rate (BMR); the amount of energy you'd burn if you stayed in bed all day.");
		AddResponse( 8,"Does the average human consume more or less energy per day than that in a gallon of gasoline?", -1 , "Much less. A gallon of gasoline contains 33.41kWh of energy while the average human uses 1.8kWh a day. If humans could eat gasoline 1 gallon would feed 18 people for a day.");
		AddResponse( 9,"Does a car that gets 15mpg use more or less energy than one that gets 20mpg to travel one mile?", 1 , "More. A car that gets 15mpg uses 2.2kWh of gasoline to travel one mile while a car that 20mpg uses 1.7kWh. An average car consumes about the same amount of energy to travel 1 mile as you do in an entire day! 1.8 kWh");
		AddResponse( 10,"Does your body produce more or less pounds of CO2 per kWh of energy than an average car?", 1 , "You produce more! A car that gets produces 0.587lb of CO2 per kWh, the average human produces 2.3lb of CO2 a day, so 2.3lb a day / 1.8kWh a day = 1.2 lb of CO2 per kWh.");
    }
	
    public void AddResponse(int _id, String  _text, int  _answer, String _answertext)
    {
    	Response response = new Response(); 
    	response.setId(			            _id);
        response.setText(		          _text);
        response.setAnswer(             _answer);
        response.setAnswerText(     _answertext);
        db.addResponse(response);
    }
	
	
}
