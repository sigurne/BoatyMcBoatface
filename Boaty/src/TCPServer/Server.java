package TCPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public static final String SERVER_VERSION = "v0.1";
    private static final int MAX_CLIENT_THREADS = 10;
    private static final int DEFAULT_SERVER_PORT = 5000;
    
    /**
     * Open server socket. Listen for client connections. 
     */
    @Override
    public void run() {
        // Server socket to be created. On default set port. 
        ServerSocket serverSocket;
        //Client socket object
        Socket clientSocket;
        //Thread pool handler, limit set to 10 clients.
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
                threadPool.execute(new ClientThread(clientSocket,SERVER_VERSION));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
