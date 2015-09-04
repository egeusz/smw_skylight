
/*

skylight_anim -- a thoroughly re-written fork of Paul Stoffregen's OctoWS2811 movie2serial.pde
Also relies on hypermedia.net's excellent UDP library. 

  OctoWS2811 movie2serial.pde - Transmit video data to 1 or more
   Teensy 3.0 boards running OctoWS2811 VideoDisplay.ino
   http://www.pjrc.com/teensy/td_libs_OctoWS2811.html
   Copyright (c) 2013 Paul Stoffregen, PJRC.COM, LLC
 
   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:
 
   The above copyright notice and this permission notice shall be included in
   all copies or substantial portions of the Software.
 
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
   THE SOFTWARE.
 
  
 Changes made by UNM Social Media Workgroup (unmsocialmedia@gmail.com):
 
   All movie code has been deleted. A PImage object and its array of raw RGB values will be used instead of a movie.
   We write our graphics to the canvas, call loadPixels() and point secondCanvas.pixels[] to pixels[].
   writeToLEDs(secondCanvas) does the rest of the heavy lifting. 
    
   UDP communications have been implemented for moment-to-moment animation changes over network. We hook this up to 
   our smartphone app (https://github.com/UNMSocialMediaWorkgroup/Android-Rain) to integrate a sms-portal. 
 
   Flow: 
 
     setup() 
       - finds and establishes communications with connected Teensies
       - starts listening to port 5005 for UDP packets
       - sets up our drawing stage
       - establishes response stack for UDP 
       - etc. 
       
     draw() starts running at established frameRate.
       - listen for UDP packets and spawn images on retrieval
       - animate all spawned images
       - pass pixels around
       - writeToLEDs() writes to attached teensies. 
 
   TO-DO:
   
     Test on Teensies ;)
    
 */
