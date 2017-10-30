package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.opencv.core.Core;

/**
 * TCP server. Opens a socket on default port. 
 * Uses thread pool for multi threading. Thread pool limits the number of clients 
 * to the specified maximum. Services a client as quickly as the system can sustain, 
 * instead of as quickly as they come in. 
 * 
 * @author Sigurd N. Eikrem
 * @version 12-Sep-2017
 */
public class Server implements Runnable {

    public static final String SERVER_VERSION = "v0.2";
    private static final int MAX_CLIENT_THREADS = 10;
    private static final int DEFAULT_SERVER_PORT = 5000;
    public static HashMap<String, Integer> serverData;
    private Object camera;
    private ArrayList<ServerListener> listeners;

   
    
    public Server(Object camera){
        serverData = new HashMap<>();
        //Load OpenCV library        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.camera = camera;
        // ArrayList for subscribers
        listeners = new ArrayList<>();
    }
    
    /**
     * Open server socket. Listen for client connections. 
     */
    @Override
    public void run() {
        // Server socket to be created. On default set port. 
        ServerSocket serverSocket;
        //Client socket object
        Socket clientSocket;
        //Thread pool handler, clients limited.
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_CLIENT_THREADS);
        try {
            //Creating the server socket on port..>
            serverSocket = new ServerSocket(DEFAULT_SERVER_PORT);
            System.out.println("Server started on port " + DEFAULT_SERVER_PORT
                    + ". Waiting for connection...");

            while (true) {
                //Waiting for connection on the socket.
                clientSocket = serverSocket.accept();
                System.out.println("Connection received from "
                        + clientSocket.getInetAddress().getHostName()
                        + " : " + clientSocket.getPort());

                //Puts connected client in the threadpool, and starts it in a new thread
                threadPool.execute(new ClientThread(clientSocket,SERVER_VERSION,camera) );
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    } 
    
    /**
     * Add data 
     * @param key
     * @param value 
     */
    public synchronized static void addServerData(String key, int value) {
        serverData.put(key, value);
        //Updates serverdata to master  ********** IKKJE FERDIG ******
        sendServerData();
    }
    
    /**
     * Getter method for server data
     * @return server data
     */
    public static HashMap getServerData(){
        return serverData;
    }
    
    /**
     * Send server data to main
     */
    private static void sendServerData(){
        
    }
    
    
    /**
     * Add a subscriber to this class
     * @param subscriber to be added.
     */
    public void addListener(ServerListener subscriber){
        listeners.add(subscriber);
    }
    
    /**
     * 
     */
    
}
