package Main;

import GUI.GUI;
import javafx.application.Application;

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
    }
}
