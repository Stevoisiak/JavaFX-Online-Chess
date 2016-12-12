public class Knight extends Piece
{
    public Knight(boolean color)
    {
        //this calls the constructor of Piece
        super(color);
    }

    @Override
    protected MoveList[] getPieceMoves()
    {
        MoveList[] m =
            {
                MoveList.KNIGHT_LEFT_UP,
                MoveList.KNIGHT_UP_LEFT,
                MoveList.KNIGHT_UP_RIGHT,
                MoveList.KNIGHT_RIGHT_UP,
                MoveList.KNIGHT_RIGHT_DOWN,
                MoveList.KNIGHT_DOWN_RIGHT,
                MoveList.KNIGHT_DOWN_LEFT,
                MoveList.KNIGHT_LEFT_DOWN
            };
        return m;
    }

    @Override
    protected boolean usesSingleMove(){return true;}

    @Override
    protected String getName(){return "knight";}
}
