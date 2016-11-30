import java.io.Serializable;

public class Package implements Serializable
{
   int xStart;
   int xEnd;
   int yStart;
   int yEnd;
    
   public Package()
   {
       xStart = 0;
       xEnd = 1;
       yStart = 0;
       yEnd = 1;
   }
   
   public Package(int x1, int y1, int x2, int y2)
   {
       xStart = x1;
       xEnd = x2;
       yStart = y1;
       yEnd = y2;
   }
   
   public String toString()
   {
       return ("Move: " + getCharLabel(xStart+1) + (yStart+1) + " to " + getCharLabel(xEnd+1) + (yEnd+1)); 
   }
   
   public int getXStart(){return xStart;}
   public int getXEnd(){return xEnd;}
   public int getYStart(){return yStart;}
   public int getYEnd(){return yEnd;}
   
   public void setXStart(int x){xStart = x;}
   public void setXEnd(int x){xEnd = x;}
   public void setYStart(int y){yStart = y;}
   public void setYEnd(int y){yEnd = y;}
   
   // Converts x number poisition to character label
   private String getCharLabel(int i)
   {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
   }
}
