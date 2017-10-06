package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import Main.MsgType;

/**
 * TCP client
 *
 * @author Sigurd N. Eikrem
 * @version 22-Sep-2017
 */
public class Client implements Runnable {

    private static final String SERVER_NAME = "192.168.0.101";
    private static final int PORT = 5000;
    public PrintStream outToServer = null;
    public BufferedReader inFromServer = null;
    private int msgType = -1;
    
    public Client(){
        
    }
    
    public Client(int msgType){
        this.msgType = msgType;
    }

    /**
     * Connect to server.
     */
    @Override
    public void run() {
        String inputLine;

        try {
            //Connecting
            System.out.println("Connecting to " + SERVER_NAME + " on port " + PORT);
            Socket client = new Socket(SERVER_NAME, PORT);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            
            outToServer = new PrintStream(client.getOutputStream(), true);
            if (msgType != -1){
            outToServer.println(msgType);
            }
            inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while ((inputLine = readLine()) != null) {
                System.out.println("Server message: " + inputLine);
            }
            client.close();
        } catch (IOException e) {
        }
    }

    /**
     * Read a line of input from the server
     *
     * @return the read line
     */
    private String readLine() {
        try {
            return inFromServer.readLine();
        } catch (IOException ex) {
            return null;
        }
    }
}
