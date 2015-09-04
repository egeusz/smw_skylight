class ResponseStack{
  
  ArrayList<Response> ResponseList;
  int MaxSize = 30; 
  int ShrinkSize = 10;
  int MergeSize  = 5; //5 responses get merged to one. 30-10 = 20 4 new responses.  
  int ResponseWidth = 2; 
  
 void ResponseStack(){
   print("RS YAY");
    ResponseList = new ArrayList<Response>();
 }
 
 void init(){
   //print("Init got called!");
   ResponseList = new ArrayList<Response>();
 }
 
 void pushResponse(Response r)
 {
   ResponseList.add(r);
 }
 
 void update()
 {
   if(ResponseList.size() > MaxSize)
   {
     shrink();
   }
 }
 
 void shrink()
 {
   
   print("Stack Shrinking!");
   ArrayList<Response> NewResponseList = new ArrayList<Response>();
  
   int rsum = 0;
   int gsum = 0;
   int bsum = 0; 
  
   for (int i = 0; i < ResponseList.size(); i++) 
   { 
    Response res = ResponseList.get(i);
     
    if(i > MaxSize-ShrinkSize)
    {
       NewResponseList.add(res);
       
    }
    else
    {
      color c =  res.getPointColor();
      rsum += (c >> 16 & 0xFF);
      gsum += (c >> 8  & 0xFF);
      bsum += (c >> c  & 0xFF);
      
      if(i%MergeSize == 0 && i!= 0)
      {
        int r = (int)(((float)rsum)/((float)MergeSize));
        int g = (int)(((float)gsum)/((float)MergeSize));
        int b = (int)(((float)bsum)/((float)MergeSize));
        rsum = 0;
        gsum = 0;
        bsum = 0;
        res.setPointColor(color(r,g,b));
        NewResponseList.add(res);
      }
      
    }
  }
  ResponseList.clear();
  ResponseList = NewResponseList;
 }
 
 
 
 int getLength()
 {
   return ResponseList.size();
 }
 
 void drawBottom(){
   int x = 0+responseStackEdgeBuffer;
   for(int i = 0; i < ResponseList.size(); i++)
   {
     x++;
     ResponseList.get(i).drawPoint(x);
   }
   
 }
 
 void drawTop(){
   int x = STRIPLENGTH-responseStackEdgeBuffer;
   for(int i = 0; i < ResponseList.size(); i++)
   {
     x--;
     ResponseList.get(i).drawPoint(x);
   }
   
 }
 
 
  
  
  
  
  
  
  
  
  
  
}
  
  
  
  
  
  
  
  
  
  
  
 
