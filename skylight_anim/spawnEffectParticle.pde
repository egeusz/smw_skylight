class spawnEffectParticle{
 
  float speed = 1.0;

  
  float xPos = 0;
  int   yPos = 0; 
  float length = 1;
  
  color color1 = color(255, 255, 255, 255);
  color color2 = color(255, 255, 255, 0);
  //bg_color1 = color(158, 0, 93);
  
  spawnEffectParticle(int y, int dir) {
    xPos = STRIPLENGTH/2;
    yPos = y;
    speed = speed*dir; 
  }
  
  
  void update() {
    xPos += speed; //update position
    length += abs(speed); 
   
    
    
  }
  
  void draw() {
    blendMode(BLEND);
    if(speed > 0)
    {
      setGradient(int(xPos-length), yPos, length, 0,  color2, color1,   X_AXIS, BLEND);
    }
    else
    {
      setGradient(int(xPos), yPos, length, 0,  color1, color2, X_AXIS, BLEND);
    }
   
  }
  
  float getXpos()
  {
    return xPos;
  }
  
}
