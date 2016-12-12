public class King extends Piece
{
    public King(boolean color)
    {
        //this calls the constructor of Piece
        super(color);
    }

    @Override
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

    @Override
    protected boolean usesSingleMove(){return true;}

    @Override
    protected String getName(){return "king";}
}
