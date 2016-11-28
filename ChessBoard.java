import javafx.event.*;
import java.util.function.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import java.util.Optional;

public class ChessBoard extends GridPane
{
    public Space[][] spaces = new Space[8][8];
    // const

    //last clicked space
    public Space activeSpace = null;

    public ChessBoard(boolean playerIsWhite)
    {
        //cause always call super
        super();

        // initialize 8x8 array of spaces
        for (int x = 0; x < spaces[0].length; x++)
        { 
            Integer xVal = new Integer(x); //gets value into EventHandler
            for (int y = 0; y < spaces[1].length; y++)
            {
                Integer yVal = new Integer(y); //gets value into EventHandler
                spaces[x][y] = new Space(x, y);

                //if white, add Spaces so ensured bottom left is 0,0
                //if Black, add Spaces so ensured bottom left is 7,7
                if (playerIsWhite) { this.add(spaces[x][y], x, 7 - y); }
                else { this.add(spaces[x][y], 7 - x, y); }

                spaces[x][y].setOnAction((e) -> 
                    {
                        //runs things that happen onClick, gets networkable Move
                        Optional<Package> info = onSpaceClick(xVal.intValue(), yVal.intValue());

                        //if the move gets the all-clear, run networking methods
                        if (info.isPresent())
                        {
                            //lock board
                            this.setDisable(true); 
                            
                            NetworkProtocol.sendMove(info.get());
                            Package inputMove = NetworkProtocol.recieveMove();
                            
                            if (inputMove != null)
                                this.processOpponentMove(inputMove);
                            
                            //unlock board
                            this.setDisable(false);
                        }
                    } 
                );

                //puts pieces in start positions
                defineStartPositions(spaces[x][y]);
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
            else {
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
            else {
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

    public Optional<Package> onSpaceClick(int x, int y)
    {

        //if there is active square and it has a piece
        if (activeSpace != null && activeSpace.getPiece() != null)
        {
            Optional<Package> p = 
                Optional.of(new Package(activeSpace.getX(), activeSpace.getY(), x, y));

            //if (moveAllowedByPiece() && moveAllowedByBoard())
            //move piece from active space to clicked space
            spaces[x][y].setPiece(
                activeSpace.releasePiece()  );

            //decouples space from space on board
            activeSpace = null;

            return p;
        }
        else 
        {
            //if there's a piece on the selected square when no active square
            if(spaces[x][y].getPiece() != null)
            {
                //make active square clicked square
                activeSpace = spaces[x][y];
            }
        }
        return Optional.empty();
    }

    // Proccesses a move after it has been recieved from an online opposing player
    public void processOpponentMove(Package p)
    {
        //this takes the move from other player and gets info
        //activeSpace = spaces[p.getXStart()][p.getYStart()];
        System.out.println(p.getXEnd() +" "+ p.getYEnd());
    }
}
