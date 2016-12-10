import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;

public class Space extends Button
{
    private int x;
    private int y;
    private Piece piece; // piece currently on space

    public Space(boolean light, int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
        this.piece = null;
        this.getStyleClass().add("chess-space");
        
        if (light)
            this.getStyleClass().add("chess-space-light");
        else
            this.getStyleClass().add("chess-space-dark");
    }

    // returns true if space is occupied
    public boolean isOccupied()
    {
        return (this.piece != null);
    }

    // removes piece from space
    public Piece releasePiece()
    {
        Piece tmpPiece = this.piece;
        setPiece(null);
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

        if (this.piece != null)
            this.setGraphic( new ImageView ( piece.getImage() ) );
        else
            this.setGraphic( new ImageView() );
    }

    public String getPieceColor()
    {
        if (getPiece() != null)
            return getPiece().getColor();
        else // space empty
            return "";
    }

    public void setX(int xIn) {this.x = xIn;}
    public int getX() {return this.x;}

    public void setY(int yIn) {this.y = yIn;}
    public int getY() {return this.y;}
}
