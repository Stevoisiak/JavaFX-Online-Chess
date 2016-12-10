# JavaFX-Online-Chess
Simple chess program written in JavaFX

## TODO list
* Piece movement
  * Create list of valid movements for each piece
  * Check against moveList when calling moveIsValid() [ChessBoard.java]
  * Create exceptions for pawn movement
* Board rules
  * Pawn piece promotion
  * Implement check & checkmate
  * Implement stalemate
* GUI
  * Add option for offline mode (In progress)
  * Add label to show which player's turn it is
* General
  * Move remaining networking code out of ChessBoard into ChessGUI
