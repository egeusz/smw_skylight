int Y_AXIS = 1;
int X_AXIS = 2;

void setGradient(int x, int y, float w, float h, color c1, color c2, int axis, int BLENDMODE ) {

  //noFill();
 
  
  if (axis == Y_AXIS) {  // Top to bottom gradient
    for (int i = y; i <= y+h; i++) {
      float inter = map(i, y, y+h, 0, 1);
      color c = lerpColor(c1, c2, inter);
     
      stroke(c);
      line(x, i, x+w, i);
    }
  }  
  else if (axis == X_AXIS) {  // Left to right gradient
    for (int i = x; i <= x+w; i++) {
      float inter = map(i, x, x+w, 0, 1);
      color c = lerpColor(c1, c2, inter);
      stroke(c);
      line(i, y, i, y+h);
    }
  }
}

float clamp( float _num, float _min, float _max)
{
    if(_num > _max)
    {
      return _max;
    }
    else if(_num < _min)
    {
        return _min;
    }
    else
    {
      return _num;  
    }
}

int clamp( int _num, int _min, int _max)
{
    if(_num > _max)
    {
      return _max;
    }
    else if(_num < _min)
    {
        return _min;
    }
    else
    {
      return _num;  
    }
}

//color clampColor( color _c)
//{
//  int red    = c >> 16 & 0xFF;
//  int green  = c >> 8 & 0xFF;
//  int blue   = c & 0xFF;
//  int alpha  = c & 0xFF;
//  
//  red   = clamp(red, 0,255); 
//  green = clamp(green, 0,255); 
//  blue   = clamp(blue, 0,255); 
//  
//  return new color(red, green, blue, alpha);
//}
