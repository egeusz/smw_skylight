class bgRainParticle {
 
  float speed = -0.05;
  float speedMax = -0.15;
  float speedMin = -0.05; 
  
  float xPos = 0;
  int   yPos = 0; 
  float length = 5;
  
  //color color1 = color(0,13,32,255);
   color color1 = color(0,45,200,255);
  color color2 = color(0,0,0,0);
  //bg_color1 = color(158, 0, 93);
  
  bgRainParticle(int y) {
    xPos = random(0,STRIPLENGTH);
    yPos = y;
    speed = random(speedMax,speedMin);
    length = (-75.0)*speed;
  }
  
  
  void update() {
    xPos += speed; //update position
   // length += speed; 
    if(xPos + length+length < 0 )
    {
      xPos = STRIPLENGTH; 
      
      speed = random(speedMin,speedMax);
      length = (-75.0)*speed;
    }
    
    
  }
  
  void draw() {
    blendMode(ADD);
    setGradient(int(xPos+length), yPos, length, 0, color1,color2,  X_AXIS, ADD);
   
   }
}
