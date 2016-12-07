import javafx.scene.image.*;

public class Piece
{
    //to be made abstract later, with getValidMoves() an abstract method
    
    protected String name;
    protected Image image;
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
        this.image = new Image(location + filename);
    }
    
    public String getName()
    {
        return this.name;
    }
    
    // Returns image of chess piece
    public Image getImage()
    {
        return this.image;
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
    
    public String toString()
    {
        return (this.getName() + " " + this.getColor());
    }
}
