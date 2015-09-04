class Response {
    
  float speed = 0.0;
  float accel = 0.00005;
  float accelMax = 0.00005;
  float accelMin = 0.00001; 
//  float accelMax = 0.0015;
//  float accelMin = 0.0011; 
  
  float xPos = 0;
  int   yPos = 0; 
  float length = 1;
  
  color u_c_color1   = color(255, 240, 0  , 255);
  color u_c_color2   = color(255, 192, 0  , 0);
  color u_ic_color1  = color(255, 108, 0  , 255);
  color u_ic_color2  = color(255, 36,  0  , 0);
  
  color d_c_color1   = color(0,   185, 255, 255);
  color d_c_color2   = color(0,   119, 211, 0);
  color d_ic_color1  = color(240, 0  , 255, 255);
  color d_ic_color2  = color(156, 0  , 255, 0);
  
  color color1 =  u_c_color1;
  color color2 =  u_c_color2;
  
  void Response(){
    
  }
  
  void setParams(boolean isUp, boolean isCorrect, int strand) {
    print("respons created!\n");
    xPos = STRIPLENGTH/2;
    yPos = clamp(strand, 0, STRIPNUM);
    if(isUp)
    {
      accel = random(accelMin,accelMax);
      if(isCorrect)
      {
        color1 =  u_c_color1;
        color2 =  u_c_color2;
      } else {
        color1 =  u_ic_color1;
        color2 =  u_ic_color2;
      }
       
    }
    else
    {
      accel = -1*random(accelMin,accelMax);
      if(isCorrect)
      {
        color1 =  d_c_color1;
        color2 =  d_c_color2;
      } else {
        color1 =  d_ic_color1;
        color2 =  d_ic_color2;
      }
    }
    
   
   
  }
  
  void updatePosition(){
    speed+=accel;
    xPos +=speed; 
    length += abs(speed); 
    
    //xpos = clamp(xpos, )
 
  }
  
  
  void draw()
  {
    blendMode(BLEND);
    stroke(color(255));
    //fill(color(255));
    point( int(xPos), int(yPos) );
    point( int(xPos+1), int(yPos) );
    point( int(xPos-1), int(yPos) );
   
    if(accel > 0)
    {

      setGradient(int(xPos-length), yPos, length, 0,  color2, color1,   X_AXIS, BLEND);
    }
    else
    {

      setGradient(int(xPos), yPos, length, 0,  color1, color2, X_AXIS, BLEND);
    }
    
    
  }
  
  void drawPoint(int x)
  {
    blendMode(BLEND);
    stroke(color1);
    point(x,yPos);
  }
  
  float getXpos(){
     return xPos; 
  }
  
  int getStrip(){
     return yPos; 
  }
  
  void setPointColor(color c)
  {
    color1 = c;  
  }
  
  color getPointColor()
  {
    return color1;  
  }
  
  
}
