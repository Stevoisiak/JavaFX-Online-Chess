# JavaFX-Online-Chess
Simple chess program written in JavaFX

## TODO list
* Piece movement
  * ~~Create list of valid movements for each piece~~
  * Check against moveList when calling moveIsValid(). [ChessBoard.java]
    * Allow Bishop, Rook, & Queen to move further across the board
    * Prevent Bishop, Rook, & Queen from moving over other pieces
  * Create exceptions for pawn movement.
  * Implement castling
* Board rules
  * Pawn piece promotion.
  * Implement check & checkmate.
  * Implement stalemate.
  * When a piece is selected, highlight spaces it can move to
* GUI
  * Add option for offline mode. (In progress)
  * Add label to show which player's turn it is.
  * Only allow White player to make the first move.
  * Add "surrender" button
* Documentation
  * README: Add usage instructions.
  * README: List program status & implemented features.
  * README: List future improvements & features to add.
  * Document and screenshot general usage & test cases.
  * Revise design document based on changes made.
* General
  * Move remaining networking code out of ChessBoard into ChessGUI.
  
