import javafx.scene.image.*;

public abstract class Piece
{
    protected boolean hasMoved;
    protected Image image;
    protected boolean color;

    // validMoves

    public Piece(boolean color)
    {
        this.color = color;

        //for pawn double move and castling(?)
        hasMoved = false;

        String location = "assets/pieces/";
        String filename = this.getColor() + "_" + this.getName() + ".png";
        this.image = new Image(location + filename);

        //getValidMoves, usesSingleMove, getName defined in classes

    }

    public boolean getHasMoved()
    {
        return this.hasMoved;
    }

    public void setHasMoved(boolean shouldBeTrue)
    {
        this.hasMoved = shouldBeTrue;
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

    protected abstract MoveList[] getValidMoves();
    protected abstract boolean usesSingleMove();
    protected abstract String getName();
}
