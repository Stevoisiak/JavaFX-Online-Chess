# JavaFX-Online-Chess [![Build Status](https://travis-ci.org/Stevoisiak/JavaFX-Online-Chess.svg?branch=master)](https://travis-ci.org/Stevoisiak/JavaFX-Online-Chess)
An (incomplete) chess program written in JavaFX. Note that by default this program uses localhost for multiplayer.

## Feature List

* Fully functional chessboard GUI
* Movable chess piece objects with team-colored piece images
* Game board drawn differently for white/black
* Supports 2 player online games
  * Game board is frozen while waiting for opponent's move
* Offline mode for local play
* Unique piece movement for King, Queen, Rook, Bishop, Knight, and Pawn
  * Pawns restricted to one-way movement
* Chat area for messages between players
* Both client & server validate moves before updating board
* Licensed for reuse under MIT License
* Customizable board colors via stylesheet.css

## Missing Features

* Special pawn rules (Piece promotion, en passant)
* Indicators for check, checkmate, and stalemate
* Handling for edge case where client & server disagree on whether a move is valid
* Allow castling

## Bugs

* Queens, Rooks, and Bishops can move past occupied spaces
* GameBoard can be interacted with before client-server connection is established

## Edge Cases

* If the client cannot find a server (ie: client started before server), chatbox displays "Error: Failed to connect to server"
* Players cannot capture their own pieces. Clicking on a piece of the same color will update the currently selected piece

## Future plans

* When a piece is selected, hightlight spaces it can move to
* Implement check, checkmate, & stalemate
* Add ability to surrender
* Option to start a new game without closing connection
* Add indicator showing which player's turn it is

## TODO list
* Piece movement
  * Prevent Bishop, Rook, & Queen from moving over other pieces
  * Implement Pawn promotion and en passant
  * Implement castling
* Board rules
  * Pawn piece promotion.
  * Implement check & checkmate.
  * Implement stalemate.
  * When a piece is selected, highlight spaces it can move to
* GUI
  * Add label to show which player's turn it is.
  * Add "surrender" button
* Documentation
  * README: Add usage instructions.
  * README: List program status & implemented features.
  * README: List future improvements & features to add.
  * Document and screenshot general usage & test cases.
  * Revise design document based on changes made.
* General
  * Move remaining networking code out of ChessBoard into ChessGUI.
  
