package Server;

import Camera.Camera;

/**
 * TO BE MOVED TO SHOTCALLER/MAIN
 *
 * @author Sigurd N. Eikrem
 * @version 15-Sep-2017
 */
public class MasterServer {

    private Server server;
    private Camera camera;

    /**
     * Constructor
     */
    public MasterServer() {
        server = new Server(camera);
    }

    /**
     * Create a main object
     *
     * @param args
     */
    public static void main(String[] args) {
        MasterServer main = new MasterServer();
        main.run(args);
    }

    /**
     *
     */
    private void run(String[] args) {
        //Starts server to handle connecting clients
        new Thread(server).start();
        System.out.println("Started server");
    }
}
