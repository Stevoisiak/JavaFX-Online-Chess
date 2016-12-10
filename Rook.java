
public class Rook extends Piece
{
    public Rook(boolean color)
    {
        //this calls the constructor of Piece
        super(color);
    }
    
    protected MoveList[] getValidMoves()
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
}
