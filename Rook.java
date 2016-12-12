public class Rook extends Piece
{
    public Rook(boolean color)
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
                MoveList.RIGHT,
                MoveList.DOWN,
                MoveList.LEFT
            };
        return m;
    }

    @Override
    protected boolean usesSingleMove(){return false;}

    @Override
    protected String getName(){return "rook";}
}
