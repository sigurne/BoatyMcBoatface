package GUI;

import Main.Client;
import Main.MsgType;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 * Creates the GUI. Handle input from user.
 *
 * @author Sigurd N. Eikrem
 */
public class GUI extends Application {

    private BorderPane root;
    private Stage primaryStage;
    private ImageView imageView;
    private Button btnCam;
    private Button btnManAut;
    private HBox hBox;
    private final String TITLE = "Boaty McBoatface";
    private boolean manualMode;
    private boolean camRunning;
    private Thread videoThread;
    private ClientVideoStream videoStream;
    private Image image;

    /**
     * Launches the GUI
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        btnCam = new Button("Start camera");
        btnManAut = new Button("Manual");
        manualMode = true;
        camRunning = false;
        this.primaryStage = primaryStage;
        root = new BorderPane();

        loadImage();
        createScene();
        handleUI();
        updateGUIStatus();
    }

    /**
     * Create the base container for all content in a scene graph
     */
    private void createScene() {
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);

        Scene scene = new Scene(rootScene(), 700, 600);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Place objects in GUI
     *
     * @return the setup of the borderpane
     */
    private Parent rootScene() {
        hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: #082B59;");
        hBox.getChildren().addAll(btnCam, btnManAut);

        root.setBottom(hBox);
        root.setCenter(imageView);
        root.setStyle("-fx-background-color: #0A2D68;");

        return root;
    }

    /**
     * Return TRUE in manual mode.
     *
     * @return
     */
    public boolean getManual() {
        return manualMode;
    }

    /**
     * Load startup image
     */
    private void loadImage() {
        // Startup image
        File file = new File("C:/Users/Sigurd/OneDrive - NTNU/Boaty McBoatface/boaty.jpg");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
    }

    /**
     * Handle input from user
     */
    private void handleUI() {
        // Start/stop camera
        btnCam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!camRunning) {
                    videoStream = new ClientVideoStream(imageView);
                    videoThread = new Thread(videoStream);
                    videoThread.start();
                    new Thread(new Client(MsgType.START_VIDEO_STREAM)).start();
                    btnCam.setText("Stop camera");
                    camRunning = true;
                }
                else {
                    btnCam.setText("Stop camera");
                    try {
                        videoStream.terminate();
                    } catch (Exception ex) {
                        System.out.println("Failed to stop camera");
                    }
                    camRunning = false;
                }
            }
        });
        // Toggle man/auto
        btnManAut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                manualMode = !manualMode;
                if (manualMode) {
                    btnManAut.setText("Manual");
                }
                else {
                    btnManAut.setText("Auto");
                }
            }
        });
    }

    /** 
     * Update statuses in GUI
     */
    private void updateGUIStatus() {
        
    }
}
