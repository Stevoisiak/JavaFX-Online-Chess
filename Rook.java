public class Rook extends Piece
{
    public Rook(boolean color)
    {
        //this calls the constructor of Piece
        super(color);
    }

    protected MoveList[] getPieceMoves()
    {
        MoveList[] m =
            {
                MoveList.UP,
                MoveList.RIGHT,
                MoveList.DOWN,
                MoveList.LEFT
            };
        return m;
    }

    protected boolean usesSingleMove(){return false;}
    protected String getName(){return "rook";}
    //https://github.com/emuro2/Chess-Game--Java-
    protected boolean canMove(int gapX, int gapY)
    {
        // Only move in column or row
        if (gapX != 0 && gapY != 0)
            return false;
        // piece not moving
        else if (gapX == 0 && gapY == 0)
            return false;
            
        return true;
    }
}
