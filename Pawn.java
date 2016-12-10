
public class Pawn extends Piece
{
    public Pawn(boolean color)
    {
        //this calls the constructor of Piece
        super(color);
    }

    protected MoveList[] getValidMoves()
    {
        //Pawn movement is HIGHLY conditional, these are all POSSIBLE valid moves
        boolean isWhite = this.color;

        MoveList[] moves;

        //since pawns will never be white AND black, only returns moves of correct direction
        if(isWhite)
        {
            MoveList[] whiteMoves =
                {
                    //standard straight, can't capture using this
                    MoveList.UP,

                    //diagonals, can and must capture using this
                    MoveList.UP_RIGHT,
                    MoveList.UP_LEFT,
                    
                    //if hasn't moved, UP is valid board move, can't capture using this
                    MoveList.DOUBLE_UP,
                };
            moves = whiteMoves;
        }
        else
        {
            MoveList[] blackMoves =
                {   
                    //standard straight, can't capture
                    MoveList.DOWN,

                    //diagonals, can and must capture using this
                    MoveList.DOWN_RIGHT,
                    MoveList.DOWN_LEFT,

                    //if hasn't moved, DOWN is valid board move, can't capture using this
                    MoveList.DOUBLE_DOWN
                };
            moves = blackMoves;
        }

        return moves;
    }

    protected boolean usesSingleMove(){return true;}

    protected String getName(){return "pawn";}
}