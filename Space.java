import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.image.*;

public class Space extends Button
{
    int x;
    int y;
    Piece piece; // piece currently on space
    
    public Space(int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
        this.piece = null;
        this.setMinHeight(400/8); //stage size/8
        this.setMinWidth(400/8); //stage size/8
        this.setOnAction(e -> onClick());
    }
    
    // returns true if space is occupied
    public boolean isOccupied()
    {
        if (this.piece != null)
            return true;
        else
            return false;
    }
    
    // removes piece from space
    public Piece releasePiece()
    {
        Piece tmpPiece = this.piece;
        this.piece = null;
        return tmpPiece;
    }
    
    public Piece getPiece()
    {
        return this.piece;
    }
    
    // Sets the piece (TODO: Capture piece), draws image of piece on space
    public void setPiece(Piece piece)
    {
        this.piece = piece;
        this.setGraphic( new ImageView ( piece.getSprite() ) );
    }
    
    public void setX(int xIn) {this.x = xIn;}
    public int getX() {return this.x;}
    
    public void setY(int yIn) {this.y = yIn;}
    public int getY() {return this.y;}
    
    // Used whenever a space is clicked on
    public void onClick()
    {
        // Temporary debug code
        System.out.println("Space: " + getX() + ", " + getY());
    }
}
