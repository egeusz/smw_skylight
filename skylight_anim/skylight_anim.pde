import processing.serial.*;
import java.awt.Rectangle;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Iterator;
import hypermedia.net.*;

int frame_rate = 100; //FPS

//Teensies, networking
UDP udp;
float gamma = 1.7;
int numPorts=0;  // the number of serial ports in use
int maxPorts=1; // maximum number of serial ports
PImage secondCanvas; //Our canvas, to draw to 
Serial[] ledSerial = new Serial[maxPorts];     // each port's actual Serial port
Rectangle[] ledArea = new Rectangle[maxPorts]; // the area of the movie each port gets, in % (0-100)
boolean[] ledLayout = new boolean[maxPorts];   // layout of rows, true = even is left->right
PImage[] ledImage = new PImage[maxPorts];      // image sent to each port
int[] gammatable = new int[256];
int errorCount=0;

//Animation
int STRIPLENGTH = 237;
int STRIPNUM = 8; 


color bg_color1 = color(95, 0, 25);
color bg_color2 = color( 0, 0, 0);

ArrayList<bgParticle> bgParticlez;
ArrayList<bgRainParticle> bgRainParticlez;
ArrayList<spawnEffectParticle> spawnEffect;
ArrayList<Response> Responsez;
ArrayList<ResponseStack> upperRStacks;
ArrayList<ResponseStack> lowerRStacks;
int responseStackEdgeBuffer = 5; 

ArrayList<Response> responseUdpBuffer;

void setup() {
  size(STRIPLENGTH, STRIPNUM);
  frameRate(frame_rate);

  //Network, teensy housekeeping
  t_setup();

  bgParticlez = new ArrayList<bgParticle>();
  bgRainParticlez = new ArrayList<bgRainParticle>();
  spawnEffect = new ArrayList<spawnEffectParticle>();
  Responsez   = new ArrayList<Response>();

  upperRStacks = new ArrayList<ResponseStack>();
  lowerRStacks = new ArrayList<ResponseStack>();
  
  responseUdpBuffer = new ArrayList<Response>();
  
  for (int i = 0; i < STRIPNUM; i++)
  {
    ResponseStack rsu = new ResponseStack();
    rsu.init();//Totally stupid and hacky, because apparently constructors don't get called.
    ResponseStack rsl = new ResponseStack();
    rsl.init();//Totally stupid and hacky, because apparently constructors don't get called.
    upperRStacks.add(rsu);
    lowerRStacks.add(rsl);
  }



  for (int i = 0; i < STRIPNUM; i++)
  {
    bgParticle  p = new bgParticle(i);  
    bgParticlez.add(p);
    
    for(int j = 0; j < 5; j++)
    {
      bgRainParticle r = new bgRainParticle(i);  
      bgRainParticlez.add(r);
    }
    
  }
}

void draw() {

  //**Temp Response Spawning** 
  if (mousePressed) {

    int strand = int(random(0, 79.99));
    strand = int(strand/10);
    float updown_f   = random(0, 1);
    float correct_f  = random(0, 1);
    boolean isup = true; 
    boolean correct = true; 
    if (updown_f < 0.5)
    {
      isup = false;
    }
    if (correct_f < 0.5)
    {
      correct = false;
    }

    Response r = new Response();  
    r.setParams(isup, correct, strand);
    Responsez.add(r);

    spawnEffectParticle  p1 = new spawnEffectParticle(strand, 1);  
    spawnEffectParticle  p2 = new spawnEffectParticle(strand, -1);  
    spawnEffect.add(p1);
    spawnEffect.add(p2);
  }



  //update
  udpBufferFlush();
  updateBackground();
  updateResponses();
  updateSpawnEffect();

  //draw
  // noSmooth();
  drawBackground();
  drawResponseStacks();
  drawResponses();
  drawSpawnEffect();

  //push to teensies
  t_draw();
}

void udpBufferFlush() {

  if (responseUdpBuffer.size() > 0 ) {

    for ( int i = responseUdpBuffer.size () - 1; i >= 0; i-- ) {

      Response r = responseUdpBuffer.get(i);
      Responsez.add(r);

      spawnEffectParticle  p1 = new spawnEffectParticle(r.getStrip(), 1);  
      spawnEffectParticle  p2 = new spawnEffectParticle(r.getStrip(), -1);  
      spawnEffect.add(p1);
      spawnEffect.add(p2);
  
      responseUdpBuffer.remove(i);
    }
  }
}

void updateResponses() {
  int stripnum = 0;//temp var for current response strip  
  for (int i = Responsez.size ()-1; i >= 0; i--) {

    Response r = Responsez.get(i);
    r.updatePosition(); 
    stripnum = r.getStrip();

    if (r.getXpos() > STRIPLENGTH - responseStackEdgeBuffer  - upperRStacks.get(stripnum).getLength())
    {
      upperRStacks.get(stripnum).pushResponse(r);
      upperRStacks.get(stripnum).update();
      Responsez.remove(i);
    } else if (r.getXpos() < responseStackEdgeBuffer + lowerRStacks.get(stripnum).getLength())
    {
      lowerRStacks.get(stripnum).pushResponse(r); 
      lowerRStacks.get(stripnum).update();
      Responsez.remove(i);
      //print("revoved "+r.getXpos()+"\n");
    }
  }
}

void drawResponses() {

  for (int i = Responsez.size () - 1; i >= 0; i-- ) {
    Responsez.get(i).draw();
  }
}



void drawResponseStacks() {
  for (ResponseStack rs : upperRStacks) {
    rs.drawTop();
  }
  for (ResponseStack rs : lowerRStacks) {
    rs.drawBottom();
  }
}

void updateSpawnEffect() {

  for (int i = spawnEffect.size ()-1; i >= 0; i--) 
  {
    spawnEffectParticle p = spawnEffect.get(i);
    p.update();

    if (p.getXpos() > STRIPLENGTH+10)
    {
      spawnEffect.remove(i);
    } else if (p.getXpos() < 0-10)
    {
      spawnEffect.remove(i);
    }
  }
}

void drawSpawnEffect()
{
  for (spawnEffectParticle p : spawnEffect) 
  {
    p.draw();
  }
}


void updateBackground() {

  for (bgParticle p : bgParticlez) {
    p.update();
  }
  
  for (bgRainParticle r : bgRainParticlez) {
    r.update();
  }
}

void drawBackground() {
  setGradient(0, 0, STRIPLENGTH, STRIPNUM, bg_color1, bg_color2, X_AXIS, BLEND);
   for (bgRainParticle r : bgRainParticlez) {
    r.draw();
  }
  for (bgParticle p : bgParticlez) {
    p.draw();
  }
}

