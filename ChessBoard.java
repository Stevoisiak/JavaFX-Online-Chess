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
            for (int y = 0; y < spaces[1].length; y++)
            {
                boolean light = ( (x + y) % 2 != 0 ); // checkerboard space colors
                spaces[x][y] = new Space(light, x, y);

                //if white, add Spaces so ensured bottom left is 0,0
                //if Black, add Spaces so ensured bottom left is 7,7
                if (playerIsWhite) { this.add(spaces[x][y], x, 7 - y); }
                else { this.add(spaces[x][y], 7 - x, y); }

                // Gets values into event handler
                final int xVal = x;
                final int yVal = y;
                //runs things that happen when a space is clicked
                spaces[x][y].setOnAction( e -> onSpaceClick(xVal, yVal) );

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
            if (s.getY() == 1){s.setPiece( new Pawn(true) );}
            else {
                switch (s.getX())
                {
                    case 0: s.setPiece( new Rook(true) );
                            break;
                    case 1: s.setPiece( new Knight(true) );
                            break;
                    case 2: s.setPiece( new Bishop(true) );
                            break;
                    case 3: s.setPiece( new Queen(true) );
                            break;
                    case 4: s.setPiece( new King(true) );
                            break;
                    case 5: s.setPiece( new Bishop(true) );
                            break;
                    case 6: s.setPiece( new Knight(true) );
                            break;
                    case 7: s.setPiece( new Rook(true) );
                            break;
                }
            }
        }
        //black pieces
        else if(s.getY() == 6  || s.getY() == 7)
        {
            if (s.getY() == 6){s.setPiece( new Pawn(false) );}
            else {
                switch (s.getX())
                {
                    case 0: s.setPiece( new Rook(false) );
                            break;
                    case 1: s.setPiece( new Knight(false) );
                            break;
                    case 2: s.setPiece( new Bishop(false) );
                            break;
                    case 3: s.setPiece( new Queen(false) );
                            break;
                    case 4: s.setPiece( new King(false) );
                            break;
                    case 5: s.setPiece( new Bishop(false) );
                            break;
                    case 6: s.setPiece( new Knight(false) );
                            break;
                    case 7: s.setPiece( new Rook(false) );
                            break;
                }
            }
        }
    }

    public void onSpaceClick(int x, int y)
    {
        Space clickedSpace = spaces[x][y];
        // if piece is selected && user didn't click on allied piece
        if (activeSpace != null &&
            activeSpace.getPiece() != null &&
            clickedSpace.getPieceColor() != activeSpace.getPieceColor())
        {            
            MoveInfo p;
            p = new MoveInfo(activeSpace.getX(), activeSpace.getY(), x, y);
            
            // update gameboard
            if (this.processMove(p)) {
                // send move to other player
                if (this.sendMove(p)) {
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
        if (processMove(p))
        {
            // unlock board
            this.setDisable(false);
        }
    }

    public boolean moveIsValid(MoveInfo p)
    {
        Space oldSpace;
        Space newSpace;
        
        // TODO:
        //  -Check against list of piece's valid moves ( piece.getMoveIndex() )
        //  -Check if player's king is put into check
        //  -Pawn logic (Possibly implement as part of pawn's movelist?)
        //  -Castling logic
        
        // Check for null move
        if (p == null) { return false; }
       
        // Note: Ideally we would check the space coordinates
        //       beforehand, but the try-catch blocks below were
        //       easier to implement.

        // Check if oldSpace in range
        try { oldSpace = spaces[p.getOldX()][p.getOldY()]; }
        catch (NullPointerException e) { return false; }
        
        // Check if newSpace in range
        try { newSpace = spaces[p.getNewX()][p.getNewY()]; }
        catch (NullPointerException e) { return false; }
        
        // Check if oldSpace is empty; (no movable piece)
        if (!oldSpace.isOccupied()) { return false; }

        // Check if piece isn't moving
        if (oldSpace == newSpace) { return false; }
        
        // Piece capturing logic
        if (newSpace.isOccupied())
        {
            Piece movedPiece = oldSpace.getPiece();
            Piece capturedPiece = newSpace.getPiece();
            
            // Cannot capture own piece
            if (movedPiece.getColor() == capturedPiece.getColor()) { return false; }
        }
        
        return true;
    }
}
