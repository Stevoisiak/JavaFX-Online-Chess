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

    Button playAsWhiteButton;
    Button playAsBlackButton;
    
    public void start(Stage mainStage) 
    {
        mainStage.setTitle("Chess Game");
        //mainStage.getIcons().add( new Image("assets/icons/app_icon.png") );
        
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root, 400, 400);
        mainStage.setScene(mainScene);
        
        VBox vbox = new VBox();
        vbox.setPadding( new Insets(16) );
        vbox.setSpacing(16);
        vbox.setAlignment( Pos.CENTER );
        root.setCenter(vbox);
        
        // add stylesheet
        //mainScene.getStylesheets().add("assets/caspian.css");
        
        
        
        // custom code below --------------------------------------------
        playAsWhiteButton = new Button("Play as white (Host)");
        //playAsWhiteButton.setOnAction(e -> playAsWhite());
        vbox.getChildren().add(playAsWhiteButton);
        
        playAsBlackButton = new Button("Play as black (Client)");
        //playAsBlackButton.setOnAction(e -> playAsBlack());
        vbox.getChildren().add(playAsBlackButton);
        
        // Menu ---
        MenuBar menuBar = new MenuBar();
        root.setTop(menuBar);
  
        Menu menuActions = new Menu("Actions");
        menuBar.getMenus().add(menuActions);
  
        MenuItem menuItemAbout = new MenuItem("About");
        menuItemAbout.setGraphic( new ImageView( new Image("assets/icons/about.png", 16, 16, true, true) ) );
        menuItemAbout.setAccelerator( new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN) ); // BUG: Key combo not working
        menuItemAbout.setOnAction(e -> displayAbout());
        menuActions.getItems().add(menuItemAbout);
         
        MenuItem menuItemHelp = new MenuItem("Help");
        menuItemHelp.setGraphic( new ImageView( new Image("assets/icons/help.png", 16, 16, true, true) ) );
        menuItemHelp.setAccelerator( new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN) );
        menuItemHelp.setOnAction(e -> displayHelp());
        menuActions.getItems().add(menuItemHelp);
         
        MenuItem menuItemQuit = new MenuItem("Quit");
        menuItemQuit.setOnAction(
            new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent event)
                {
                    mainStage.close();
                }
            }
        );
        menuItemQuit.setGraphic( new ImageView( new Image("assets/icons/quit.png", 16, 16, true, true) ) );
        menuItemQuit.setAccelerator( new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN) );
        menuActions.getItems().add(menuItemQuit);
        
        
        // custom code above --------------------------------------------

        mainStage.show();
    }
    
    // Display 'about' menu
    public void displayAbout()
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
    
    // Display 'help' menu
    public void displayHelp()
    {
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("Help");
        infoAlert.setHeaderText(null); 
        infoAlert.setContentText("This is a simple networked chess program.\n" +
                                 "To start, pick a color to play as.");
        infoAlert.showAndWait();
    }
}