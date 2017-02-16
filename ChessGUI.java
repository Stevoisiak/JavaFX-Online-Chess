import java.util.Optional;
import Networking.Client;
import Networking.NetworkConnection;
import Networking.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    // Hack: Set connection as public static so it may be used
    //       by ChessBoard to send moves. (VERY HACKY!)
    public static NetworkConnection connection;

    private ChessBoard board;
    private TextArea chatArea; // chat messages
    private boolean playerIsWhite; // white player = server
    private boolean offlineMode = false;

    @Override
    public void start(Stage mainStage) 
    {
        mainStage.setTitle("Chess Game");
        mainStage.getIcons().add( new Image("assets/icons/app_icon.png") );

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        // add stylesheet
        mainScene.getStylesheets().add("assets/stylesheet.css");

        // prompt user to select team color
        choosePlayerColor();

        // draw chessboard
        board = new ChessBoard(playerIsWhite);
        root.setCenter(board); // sized 400x400

        // Initialize server/client
        if (!offlineMode)
        {
            // create chat box
            VBox chatBox = generateChatBox();
            root.setRight(chatBox);

            if (playerIsWhite)
            {
                connection = createServer();
                chatArea.appendText("Connecting to client...\n");
            }
            else
            {
                connection = createClient();
                chatArea.appendText("Connecting to server...\n");
                // lock board until white makes first move
                board.setDisable(true);
            }

            try
            {
                connection.startConnection();
            }
            catch ( Exception e )
            {
                System.err.println("Error: Failed to start connection");
                System.exit(1);
            }
        }

        // add menuBar
        MenuBar menuBar = generateMenuBar();
        root.setTop(menuBar);

        mainStage.show();
    }

    // Attempt to close socket on program termination
    // TODO: Check if connection has been created/established
    //       before attmepting to close
    @Override
    public void stop() {
        try {
            connection.closeConnection();
        }
        catch (NullPointerException e) {
            // Nothing to close. Connention never initialized and/or established
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Prompts the player to choose team color
    // TODO: Change return type to enum so we can return NULL
    //       if user exits without selecting a color;
    public void choosePlayerColor()
    {
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

        ButtonType buttonTypeWhite = new ButtonType("White (Server)");
        ButtonType buttonTypeBlack = new ButtonType("Black (Client)");
        ButtonType buttonTypeOffline = new ButtonType("Offline");

        newGameAlert.getButtonTypes().setAll(buttonTypeWhite, buttonTypeBlack, buttonTypeOffline);
        Optional<ButtonType> result = newGameAlert.showAndWait();

        if (result.get() == buttonTypeWhite)
        {
            this.playerIsWhite = true;
        }
        else if (result.get() == buttonTypeBlack)
        {
            this.playerIsWhite = false;
        }
        else // offline mode
        {
            this.playerIsWhite = true;
            this.offlineMode = true;
        }
    }

    // Initialize Server
    private Server createServer() {
        return new Server(444, data -> {
            // Below: Runs whenever data is revieved from the client.
            //        runLater() gives JavaFX time to draw GUI.
            Platform.runLater(() -> {
                if (data instanceof MoveInfo)
                {
                    board.processOpponentMove((MoveInfo) data);
                }
                else // if (data instanceof String)
                {
                    // Display in chat message box
                    chatArea.appendText(data.toString() + "\n");
                }
            });
        });
    }

    // Initialize Client
    private Client createClient() {
        // localhost IP address
        return new Client("127.0.0.1", 444, data -> {
            // Below: Runs whenever data is revieved from the server.
            //        runLater() gives JavaFX time to draw GUI.
            Platform.runLater(() -> {
                if (data instanceof MoveInfo)
                {
                    board.processOpponentMove((MoveInfo) data);
                }
                else // if (data instanceof String)
                {
                    // Display in chat message box
                    chatArea.appendText(data.toString() + "\n");
                }
            });
        });
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

        infoAlert.setContentText("Programmed by Maxwell Sirotin and Steven Vascellaro.\n\n" +
            "Chess icons by \"Colin M.L. Burnett\".\n\n" + 
            "Networking package & chat client based on \n\"JavaFX Software: Chat (Server-Client)\" \nby Almas Baimagambetov.\n\n" +
            "App icon by BlackVariant.");
        infoAlert.showAndWait();
    }

    // Generate chat window
    private VBox generateChatBox()
    {  
        // sends messages
        TextField chatField = new TextField();
        chatField.getStyleClass().add("chat-field");
        chatField.setOnAction(event -> {
            // Specify if message is from server or client
            String message = playerIsWhite ? "Server: " : "Client: ";

            message += chatField.getText();
            chatField.clear();
            chatArea.appendText(message + "\n");

            try {
                connection.send(message);
            }
            catch (Exception e) {
                chatArea.appendText("Failed to send\n");
            }
        });

        // displays messages
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.getStyleClass().add("chat-area");

        VBox chatBox = new VBox(20, chatArea, chatField);
        chatBox.getStyleClass().add("chat-box");

        return chatBox;
    }

    // Generate main menu bar
    private MenuBar generateMenuBar()
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
        // Note: Accelerator F1 does not work if TextField is
        //       in focus. This is a known issue in JavaFX.
        //       https://bugs.openjdk.java.net/browse/JDK-8148857
        menuItemAbout.setAccelerator( new KeyCodeCombination(KeyCode.F1) );
        menuItemAbout.setOnAction(e -> onDisplayAbout());
        menuHelp.getItems().add(menuItemAbout);

        return menuBar;
    }
}