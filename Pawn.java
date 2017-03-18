import java.util.ArrayList;

public class Pawn extends Piece
{
    public Pawn(boolean color)
    {
        //this calls the constructor of Piece
        super(color);
    }

    protected MoveList[] getPieceMoves()
    {
        /*
         * Pawn movement is HIGHLY conditional, so this branches.
         * The list ensures correct direction and two-space movement.
         * All the board-dependent things (like diagonal iff capturing) are ChessBoard's job.
        */
        boolean isWhite = this.color;

        //braces ensure toArray() works later, see ArrayList docs for why
        MoveList[] moves = {};

        //since pawns will never be white AND black, only returns moves of correct direction
        if(isWhite)
        {
            ArrayList<MoveList> whiteMoves = new ArrayList<MoveList>();

            //standard straight, can't capture using this
            whiteMoves.add(MoveList.UP);

            //diagonals, can and must capture using this
            whiteMoves.add(MoveList.UP_RIGHT);
            whiteMoves.add(MoveList.UP_LEFT);

            //if hasn't moved, UP is valid board move, can't capture using this
            if(!hasMoved) {whiteMoves.add(MoveList.DOUBLE_UP);}

            moves = whiteMoves.toArray(moves);
        }
        else
        {
            ArrayList<MoveList> blackMoves = new ArrayList<MoveList>();

            //standard straight, can't capture
            blackMoves.add(MoveList.DOWN);

            //diagonals, can and must capture using this
            blackMoves.add(MoveList.DOWN_RIGHT);
            blackMoves.add(MoveList.DOWN_LEFT);

            //if hasn't moved, DOWN is valid board move, can't capture using this
            if(!hasMoved) {blackMoves.add(MoveList.DOUBLE_DOWN);}

            moves = blackMoves.toArray(moves);
        }

        return moves;
    }

    protected boolean usesSingleMove(){return true;}
    protected String getName(){return "pawn";}
}
