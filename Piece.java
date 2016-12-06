import javafx.scene.image.*;

public class Piece
{
    //to be made abstract later, with getValidMoves() an abstract method
    
    final boolean WHITE = true;
    final boolean BLACK = false;
    
    protected String name;
    protected Image sprite;
    protected boolean color;
    protected boolean singleMove;
    
    // validMoves
    
    public Piece(String name, boolean color)
    {
        this.name = name;
        this.color = color;
        
        //since image can be inferred from name, probably won't be needed once have Piece subclasses
        String location = "assets/pieces/";
        String filename = this.getColor() + "_" + this.getName() + ".png";
        this.sprite = new Image(location + filename);
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Image getSprite()
    {
        return this.sprite;
    }
    
    // Get piece color as string
    public String getColor()
    {
        if (this.color == true)
            return "white";
        else
            return "black";
    }
    
    // returns true if color is white
    public boolean isWhite()
    {
        return this.color;
    }
}
