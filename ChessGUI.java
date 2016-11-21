import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.event.*; 
import javafx.animation.*;
import javafx.geometry.*;
import java.io.*;
import java.util.*;

public class ChessGUI extends Application 
{
    public static void main(String[] args) 
    {
        // Automatic VM reset, thanks to Joseph Rachmuth.
        try
        {
            launch(args);
            System.exit(0);
        }
        catch (Exception error)
        {
            error.printStackTrace();
            System.exit(0);
        }
    }

    boolean playerIsWhite;
    
    public void start(Stage mainStage) 
    {
        mainStage.setTitle("Chess Game");
        //mainStage.getIcons().add( new Image("assets/icons/app_icon.png") );

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        VBox vbox = new VBox();
        vbox.setAlignment( Pos.TOP_CENTER );
        root.setCenter(vbox);

        // add stylesheet
        mainScene.getStylesheets().add("assets/stylesheet.css");
        
        // Game logic ---
        
        // prompt user to select team color
        boolean playerIsWhite = choosePlayerColor();
        
        // draw chessboard
        ChessBoard board = new ChessBoard(playerIsWhite);
        vbox.getChildren().add(board);
        
        // Add menuBar
        MenuBar menuBar = generateMenuBar();
        root.setTop(menuBar);

        mainStage.show();
    }
    
    // Prompts the player to choose team color
    // TODO: Change return type to enum so we can return NULL
    //       if user exits without selecting a color;
    public boolean choosePlayerColor()
    {
        // Set to white by default
        boolean playerIsWhite = true;
        
        // TODO: If a chess game is currently ongoing, warn that
        //         "Starting a new game while a match is in progress will count as a forfiet."
        //         "Do you still want to start a new game?"
        //            "Yes"   "No"
        //
        //       If no, just break/return and ignore the following code

        // Prompt user for new game
        Alert newGameAlert = new Alert(AlertType.CONFIRMATION);
        newGameAlert.setTitle("Start new game");
        newGameAlert.setHeaderText(null);
        newGameAlert.setContentText("Pick team color");

        ButtonType buttonTypeWhite = new ButtonType("Play White (Server)");
        ButtonType buttonTypeBlack = new ButtonType("Play Black (Client)");

        newGameAlert.getButtonTypes().setAll(buttonTypeWhite, buttonTypeBlack);
        Optional<ButtonType> result = newGameAlert.showAndWait();

        if (result.get() == buttonTypeWhite)
        {
            playerIsWhite = true;
        }
        else if (result.get() == buttonTypeBlack)
        {
            playerIsWhite = false;
        }
        
        return playerIsWhite;
    }

    // Quits program
    public void onQuit()
    {
        Platform.exit();
        System.exit(0);
    }

    // Display 'about' menu
    public void onDisplayAbout()
    {
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("About this program");
        infoAlert.setHeaderText(null); 

        // set window icon
        Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add( new Image("assets/icons/about.png") );

        // the graphic replaces the standard icon on the left
        //infoAlert.setGraphic( new ImageView( new Image("assets/icons/cat.png", 64, 64, true, true) ) );

        infoAlert.setContentText("Programmed by Maxwell Sirotin and Steven Vascellaro.\n" +
            "Chess Icons by \"Colin M.L. Burnett\".");
        infoAlert.showAndWait();
    }
    
    // Generate main menu bar
    public MenuBar generateMenuBar()
    {
        MenuBar menuBar = new MenuBar();
        
        Menu gameMenu = new Menu("Game");
        menuBar.getMenus().add(gameMenu);

        MenuItem menuItemQuit = new MenuItem("Quit");
        menuItemQuit.setOnAction(e -> onQuit());
        //menuItemQuit.setGraphic( new ImageView( new Image("assets/icons/quit.png", 16, 16, true, true) ) );
        menuItemQuit.setAccelerator( new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN) );
        gameMenu.getItems().add(menuItemQuit);

        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().add(menuHelp);

        MenuItem menuItemAbout = new MenuItem("About");
        //menuItemAbout.setGraphic( new ImageView( new Image("assets/icons/about.png", 16, 16, true, true) ) );
        menuItemAbout.setAccelerator( new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN) ); // BUG: Key combo not working
        menuItemAbout.setOnAction(e -> onDisplayAbout());
        menuHelp.getItems().add(menuItemAbout);

        return menuBar;
    }
}