package BoatyMcBoatface;

import TCPServer.Server;
import org.opencv.core.Core;

/**
 * Initiate the project, start server,
 *
 * @author Sigurd N. Eikrem
 * @version 15-Sep-2017
 */
public class Main {

    private Server server;

    /**
     * Constructor
     */
    public Main() {
        server = new Server();
    }

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
