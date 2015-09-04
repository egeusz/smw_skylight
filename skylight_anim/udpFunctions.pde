
void receive( byte[] data ) {
  String message = new String( data );

  // print the result 4 debugging.
  //println( "received: "+message );

  //Parse result
  try {
    message = message.trim(); //Strip brackets
    String response_list[] = split(message, ","); //pull values

    boolean isup = true;
    boolean correct = true;
    
    int rl0 = parseInt(response_list[0]);
    int rl2 = parseInt(response_list[2]);
    
    //Massage values into types
    if(rl0 == 0 ||  rl0 == -1 )
    {
      isup = false; 
    }
    if(rl2 != 1)
    {
      correct = false; 
    }    
    int strand = (parseInt(response_list[3]) - 1) % 8; //Quick modulo to prevent index overflow.
    //int strand = 0;
    print("udp: "+message+" > "+response_list[0]+" "+response_list[1]+" "+response_list[2]+" "+response_list[3]+" "+parseInt(response_list[3])+"\n");
    //Dump final values for debugging
    //println(isup + "\t" + correct + "\t" + strand);


    //If not malformed (couldn't get here if exception occurs), add to new response stack 
     Response r = new Response();  
    r.setParams(isup, correct, strand);
    responseUdpBuffer.add(r);


    //Responsez.add( new Response(isup, correct, strand) );
    
  } finally {
     //We don't need to catch any exceptions.
     //This is the lazy way to write this code. Maybe I could whip up a regex instead.
     //But we just need it to work for now, so... 
  }
}

