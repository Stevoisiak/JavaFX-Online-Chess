import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.*;
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
        this.getStyleClass().add("chess-space");
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
        this.setGraphic(new ImageView());
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
        this.setGraphic( new ImageView ( piece.getImage() ) );
    }

    public void setX(int xIn) {this.x = xIn;}
    public int getX() {return this.x;}

    public void setY(int yIn) {this.y = yIn;}
    public int getY() {return this.y;}
}
