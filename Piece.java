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
        if (this.isWhite())
            location += "white_";
        else
            location += "black_";
        this.sprite = new Image(location + name + ".png");
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Image getSprite()
    {
        return this.sprite;
    }
    
    // returns true if color is white
    public boolean isWhite()
    {
        return color;
    }
}