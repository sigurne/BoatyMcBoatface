package GUI;

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
        // Startup image
        File file = new File("C:/Users/Sigurd/OneDrive - NTNU/Skule/3.året/Sanntid/Boaty McBoatface prosjekt/boaty.jpg");
        Image image = new Image(file.toURI().toString());
        imageView = new ImageView(image);

        createScene();

        btnCam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Avoid starting several threads
                if (btnCam.getText().equalsIgnoreCase("start camera")){
                //new Thread(new VideoStreamFX(imageView)).start();
                }
                btnCam.setText("Camera running");
            }
        });

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
}
