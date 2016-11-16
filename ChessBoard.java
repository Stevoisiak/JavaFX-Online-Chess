import javafx.event.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;

public class ChessBoard extends GridPane
{
    public Space[][] spaces = new Space[8][8];
    // const

    //
    public Space activeSpace = null;

    public ChessBoard(boolean playerIsWhite)
    {
        //cause always call super
        super();

        // initialize 8x8 array of spaces
        for (int i = 0; i < spaces[0].length; i++)
        { 
            Integer iVal = new Integer(i); //gets value into EventHandler
            for (int j = 0; j < spaces[1].length; j++)
            {
                Integer jVal = new Integer(j); //gets value into EventHandler
                spaces[i][j] = new Space(i, j);

                //if white, add Spaces so ensured bottom left is 0,0
                //if Black, add Spaces so ensured bottom left is 7,7
                if (playerIsWhite) { this.add(spaces[i][j], i, 8 - j); }
                else { this.add(spaces[i][j], 8 - i, j); }

                EventHandler<ActionEvent> spaceClick = new EventHandler<ActionEvent>()
                    {
                        public void handle(ActionEvent event)
                        {

							//if there is active square and it has a piece
                            if (activeSpace != null && activeSpace.getPiece() != null)
                            {
								//move piece from active space to clicked space
                                spaces[iVal.intValue()][jVal.intValue()].setPiece(
                                	activeSpace.releasePiece()  );

								//decouples space from space on board
                                activeSpace = null;
                            }
                            else 
                            {
								//if there's a piece on the selected square when no active square
                                if(spaces[iVal.intValue()][jVal.intValue()].getPiece() != null)
                                {
									//make active square clicked square
                                    activeSpace = spaces[iVal.intValue()][jVal.intValue()];
                                }
                            }
                        }
                    };

                spaces[i][j].setOnAction(spaceClick);

                //puts pieces in start positions
                defineStartPositions(spaces[i][j]);
            }
        }
    }

    //Use this to get a space, using GridPane methods will (I think) cause color problems
    public Space getSpace(int x, int y)
    {
        return spaces[x][y];
    }

    //Use this to get a space, using GridPane methods will (I think) cause color problems
    public void setActiveSpace(Space s)
    {
        activeSpace = s;
    }

    public Space getActiveSpace()
    {
        return activeSpace;
    }

    // prints location of all pieces on the board
    // TODO: Unfinished
    public String toString()
    {
        String pieceList = "";
        for (int i = 0; i < spaces[0].length; i++)
        {
            for (int j = 0; j < spaces[1].length; j++)
            {
                if (spaces[i][j].isOccupied())
                {
                    //pieceList += spaces[i][j].toString();
                }
            }
        }
        return pieceList;
    }

    //define the starting piece positions
    public void defineStartPositions(Space s)
    {
        //white pieces
        if(s.getY() == 0 || s.getY() == 1)
        {
            if (s.getY() == 1){s.setPiece( new Piece("pawn", true) );}
            else{
                switch (s.getX())
                {
                    case 0: s.setPiece( new Piece("rook", true) );
                    break;
                    case 1: s.setPiece( new Piece("knight", true) );
                    break;
                    case 2: s.setPiece( new Piece("bishop", true) );
                    break;
                    case 3: s.setPiece( new Piece("queen", true) );
                    break;
                    case 4: s.setPiece( new Piece("king", true) );
                    break;
                    case 5: s.setPiece( new Piece("bishop", true) );
                    break;
                    case 6: s.setPiece( new Piece("knight", true) );
                    break;
                    case 7: s.setPiece( new Piece("rook", true) );
                    break;
                }
            }
        }
        //black pieces
        else if(s.getY() == 6  || s.getY() == 7)
        {
            if (s.getY() == 6){s.setPiece( new Piece("pawn", false) );}
            else{
                switch (s.getX())
                {
                    case 0: s.setPiece( new Piece("rook", false) );
                    break;
                    case 1: s.setPiece( new Piece("knight", false) );
                    break;
                    case 2: s.setPiece( new Piece("bishop", false) );
                    break;
                    case 3: s.setPiece( new Piece("queen", false) );
                    break;
                    case 4: s.setPiece( new Piece("king", false) );
                    break;
                    case 5: s.setPiece( new Piece("bishop", false) );
                    break;
                    case 6: s.setPiece( new Piece("knight", false) );
                    break;
                    case 7: s.setPiece( new Piece("rook", false) );
                    break;
                }
            }
        }
    }
}
