class bgParticle {
 
  float speed = 1.0;
  float speedMax = 1.25;
  float speedMin = 0.5; 
  
  float xPos = 0;
  int   yPos = 0; 
  float length = 1;
  
  color color1 = color(165, 82, 0,255);
  color color2 = color(0, 0, 0,0);
  //bg_color1 = color(158, 0, 93);
  
  bgParticle(int y) {
    xPos = 0;
    yPos = y;
    speed = random(speedMin,speedMax);
  }
  
  
  void update() {
    xPos += speed; //update position
    length += speed; 
    if(xPos > STRIPLENGTH)
    {
      xPos = 0; 
      length = 1;
      speed = random(speedMin,speedMax);
    }
    
    
  }
  
  void draw() {
    blendMode(BLEND);
    setGradient(int(xPos+length), yPos, length, 0, color2, color1,  X_AXIS, ADD);
   
   }
}
