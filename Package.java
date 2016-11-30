import java.io.Serializable;

public class Package implements Serializable
{
   int oldX;
   int newX;
   int oldY;
   int newY;
    
   public Package()
   {
       oldX = 0;
       oldY = 0;
       newX = 1;
       newY = 1;
   }
   
   public Package(int oldX, int oldY, int newX, int newY)
   {
       this.oldX = oldX;
       this.oldY = oldY;
       this.newX = newX;
       this.newY = newY;
   }
   
   public String toString()
   {
       return ("Move: " + getCharLabel(oldX+1) + (oldY+1) + " to " + getCharLabel(newX+1) + (newY+1)); 
   }
   
   public int getOldX(){return this.oldX;}
   public int getOldY(){return this.oldY;}
   public int getNewX(){return this.newX;}
   public int getNewY(){return this.newY;}
   
   public void setOldX(int oldX){this.oldX = oldX;}
   public void setOldY(int oldY){this.oldY = oldY;}
   public void setNewX(int newX){this.newX = newX;}
   public void setNewY(int newX){this.newY = newX;}
   
   // Converts x number poisition to character label
   private String getCharLabel(int i)
   {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
   }
}
