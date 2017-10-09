package BoatyMcBoatface;

import TCPServer.Server;
import org.opencv.core.Core;

/**
 * Initiate the project, start server, open gui
 *
 * @author Sigurd N. Eikrem
 * @version 15-Sep-2017
 */
public class Main {

    private Server server;

    /**
     * Create a main object
     *
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.run(args);
    }

    /**
     * Constructor
     */
    public Main() {
        server = new Server();
    }

    /**
     *
     */
    private void run(String[] args) {
        //Starts server to handle connecting clients
        new Thread(server).start();
        System.out.println("Started server");
        //Load OpenCV library        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    }
}
