package Main;

import GUI.GUI;
import javafx.application.Application;
import org.opencv.core.Core;

/**
 *
 * @author Sigurd N. Eikrem
 * @version 22-Sep-2017
 */
public class Main {

    public static void main(String[] args) {
        // new Thread(new Client()).start();
        //Open GUI      
        Application.launch(GUI.class, args);
        //Load OpenCV library        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
