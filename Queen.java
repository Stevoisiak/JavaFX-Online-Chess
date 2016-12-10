
public class Queen extends Piece
{
    public Queen(boolean color)
    {
        //this calls the constructor of Piece
        super(color);
    }
    
    protected MoveList[] getValidMoves()
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
    protected boolean usesSingleMove(){return false;}
    protected String getName(){return "queen";}
}
