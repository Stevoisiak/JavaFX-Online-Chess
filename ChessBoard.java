import javafx.scene.layout.GridPane;

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
            }
        }

        //put pieces in start positions
        this.defineStartPositions();
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
    public void defineStartPositions()
    {
        // white pieces
        this.spaces[0][0].setPiece( new Rook  (true) );
        this.spaces[1][0].setPiece( new Knight(true) );
        this.spaces[2][0].setPiece( new Bishop(true) );
        this.spaces[3][0].setPiece( new Queen (true) );
        this.spaces[4][0].setPiece( new King  (true) );
        this.spaces[5][0].setPiece( new Bishop(true) );
        this.spaces[6][0].setPiece( new Knight(true) );
        this.spaces[7][0].setPiece( new Rook  (true) );

        for (int i = 0; i < this.spaces[0].length; i++)
            this.spaces[i][1].setPiece( new Pawn(true) );

        // black pieces
        this.spaces[0][7].setPiece( new Rook  (false) );
        this.spaces[1][7].setPiece( new Knight(false) );
        this.spaces[2][7].setPiece( new Bishop(false) );
        this.spaces[3][7].setPiece( new Queen (false) );
        this.spaces[4][7].setPiece( new King  (false) );
        this.spaces[5][7].setPiece( new Bishop(false) );
        this.spaces[6][7].setPiece( new Knight(false) );
        this.spaces[7][7].setPiece( new Rook  (false) );

        for (int i = 0; i < this.spaces[0].length; i++)
            this.spaces[i][6].setPiece( new Pawn(false) );
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

            // Hacky: Determine if ChessGUI's offlineMode is enabled by checking
            //        if 'connection' was ever initialized.
            boolean offlineMode = (ChessGUI.connection == null);

            // update gameboard
            if (this.processMove(p) && !offlineMode) {
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
            // Hacky: This only works because ChessGUI has
            //        public static NetworkConnection connection.
            //        Ideally, connection would be private.
            ChessGUI.connection.send(p);
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
        Piece piece;
        MoveList[] moves;

        // TODO:
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

        // Check piece's move list
        piece = oldSpace.getPiece();
        moves = piece.getPieceMoves();
        boolean matchesPieceMoves = false;

        //for Pieces that move more than 1 base move (Bishop, Rook, Queen)
        int multiMoveCount;
        int stretchedMoveX;
        int stretchedMoveY;

        //labels this loop to break out later
        MoveLoop:
        for (MoveList m : moves)
        {//iterates through mutiple times if has multiple possible moves
            multiMoveCount = 1;
            if(piece.usesSingleMove() == false) {multiMoveCount = 8;}
            
            boolean hasCollided = false;
            
            for(int c = 1; c <= multiMoveCount; c++)
            {
                //if the prior run hit a piece of opponent's color, done with this move
                if (hasCollided){break;}
                
                //stretches a base move out to see if it matches the move made
                stretchedMoveX = m.getX() * c;
                stretchedMoveY = m.getY() * c;

                Space tempSpace;

                //If OOB, go to next move of the piece -- ensures space exists later
                try
                {
                    tempSpace = spaces[p.getOldX() + stretchedMoveX]
                    [p.getOldY() + stretchedMoveY];
                }
                catch (Exception e) { break; }
                
                //handles piece collision and capturing
                if(tempSpace.isOccupied())
                {
                    hasCollided = true;
                    boolean piecesSameColor = tempSpace.getPiece().getColor() == oldSpace.getPiece().getColor();
                    //stops checking this move if pieces are the same color
                    if (piecesSameColor){ break; }
                }
                
                //if stretched move matches made move
                if ( p.getGapX() == stretchedMoveX && p.getGapY() == stretchedMoveY)
                {
                    matchesPieceMoves = true;
                    //breaks out of MoveLoop (both loops)
                    break MoveLoop;
                }
            }
        }
        if (!matchesPieceMoves) { return false; }
        
        return true;
    }
}
