public class King extends Piece
{
    public King(boolean color)
    {
        //this calls the constructor of Piece
        super(color);
    }

    protected MoveList[] getPieceMoves()
    {
        MoveList[] m =
            {
                MoveList.UP,
                MoveList.UP_RIGHT,
                MoveList.RIGHT,
                MoveList.DOWN_RIGHT,
                MoveList.DOWN,
                MoveList.DOWN_LEFT,
                MoveList.LEFT,
                MoveList.UP_LEFT
            };
        return m;
    }

    protected boolean usesSingleMove(){return true;}
    protected String getName(){return "king";}
}
