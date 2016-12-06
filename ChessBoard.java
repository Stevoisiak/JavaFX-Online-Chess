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

                //runs things that happen when a space is clicked
                spaces[x][y].setOnAction( e -> onSpaceClick(xVal.intValue(), yVal.intValue()) );

                //puts pieces in start positions
                defineStartPositions(spaces[x][y]);
            }
        }
    }

    // Use this to get a space, using GridPane methods will (I think) cause color problems
    public Space getSpace(int x, int y)
    {
        return spaces[x][y];
    }

    public void setActiveSpace(Space s)
    {
        // Remove style from old active space
		if (this.activeSpace != null) 
            this.activeSpace.getStyleClass().removeAll("chess-space-active");
            
        this.activeSpace = s;
        
		// Add style to new active space
        if (this.activeSpace != null) 
            this.activeSpace.getStyleClass().add("chess-space-active");
    }

    // Use this to get a space, using GridPane methods will (I think) cause color problems
    public Space getActiveSpace()
    {
        return this.activeSpace;
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

    public void onSpaceClick(int x, int y)
    {
        // if piece is selected (active square has a piece)
        if (activeSpace != null && activeSpace.getPiece() != null)
        {            
            MoveInfo p;
            boolean boardUpdated = false;
            boolean moveSent = false;
            
            p = new MoveInfo(activeSpace.getX(), activeSpace.getY(), x, y);
            
            // update gameboard
            boardUpdated = this.processMove(p);
            if (boardUpdated) {
                // send move to other player
                moveSent = this.sendMove(p);
                if (moveSent) {
                    // lock board
                    this.setDisable(true);
                }
            }

            //decouples space from space on board
            this.setActiveSpace(null);
        }
        else 
        {
            //if there's a piece on the selected square when no active square
            if (spaces[x][y].getPiece() != null)
            {
                //make active square clicked square
                this.setActiveSpace(spaces[x][y]);
            }
        }
    }

    // Send move via Internet to other player
    public boolean sendMove(MoveInfo p)
    {
         try {
             ChessGUI.connection.send(p); // Steven: VERY HACKY! TODO: FIX THIS
         }
         catch (Exception e) {
             System.err.println("Error: Failed to send move");
             return false;
         }
         
         return true;
    }
    
    // Proccess a move after it has been made by a player
    protected boolean processMove(MoveInfo p)
    {
        if (moveIsValid(p))
        {
            Space oldSpace = spaces[p.getOldX()][p.getOldY()];
            Space newSpace = spaces[p.getNewX()][p.getNewY()];
            
            newSpace.setPiece( oldSpace.releasePiece() );
            return true;
        }
        else // invalid move
        {
            return false;
        }
    }
    
    // Proccess an opponent's move
    public void processOpponentMove(MoveInfo p)
    {
        boolean validMove = processMove(p);
        if (validMove)
        {
            // unlock board
            this.setDisable(false);
        }
    }

    public boolean moveIsValid(MoveInfo p)
    {
        if (p == null)
            return false;

        Space oldSpace = spaces[p.getOldX()][p.getOldY()];
        Space newSpace = spaces[p.getNewX()][p.getNewY()];
        
        // Moving to same space
        if (oldSpace == newSpace)
            return false;
        // TODO: Space is occupied by friendly piece
        // TODO: Move is part of piece's valid moves
        // TODO: Does not put player's own king into check
        
        return true;
    }
}
