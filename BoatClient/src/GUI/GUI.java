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
import static javafx.scene.paint.Color.color;
import javafx.stage.Stage;
import org.opencv.core.Core;
import static javafx.scene.paint.Color.color;

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
    private EventHandler eventHandler;
    private HBox hBox;
    private final String TITLE = "Boaty McBoatface";
    private boolean manualMode;
    private Thread videoThread;
    private ClientVideoStream videoStream;
    private Image image;
    private Image image2;
    private MsgType msgType;

    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        btnCam = new Button("Start camera");
        btnManAut = new Button("Manual");
        manualMode = true;
        this.primaryStage = primaryStage;
        root = new BorderPane();

        
        loadImage();
        createScene();

        videoStream = new ClientVideoStream(imageView);
        

        btnCam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // new Thread(new ClientVideoStream(imageView)).start();
                //
                new Thread(new Client(msgType.START_VIDEO_STREAM)).start();
                videoThread = new Thread(videoStream);
                videoThread.start();
                btnCam.setText("Camera running");
            }
        });

        btnManAut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                manualMode = !manualMode;
                if (manualMode) {
                    btnManAut.setText("Manual");
                    videoStream.terminate();
                }
                else {
                    btnManAut.setText("Auto");

                }
            }
        });
    }

    /**
     * Create the base container for all content in a scene graph
     */
    private void createScene() {
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);
// 
        Scene scene = new Scene(rootScene(), 700, 600);
        scene.setFill(Color.rgb(10, 45, 104));

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
     *
     */
    private void loadImage() {
        // Startup image
        File file = new File("C:/Users/Sigurd/OneDrive - NTNU/Boaty McBoatface/boaty.jpg");
        image = new Image(file.toURI().toString());
        imageView = new ImageView(image);

        // TEST image 
        File file2 = new File("C:\\Users\\Sigurd\\OneDrive - NTNU\\Skule\\3.året\\Bildebehandling\\Testbilder2\\cola1.png");
        image2 = new Image(file2.toURI().toString());
    }
}
