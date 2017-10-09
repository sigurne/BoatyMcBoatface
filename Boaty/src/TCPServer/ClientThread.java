package TCPServer;

import BoatyMcBoatface.MsgType;
import GUI.VideoStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Read input from client. Execute command based on the message.
 *
 * @author Sigurd N. Eikrem
 * @version 12-Sep-2017
 */
class ClientThread implements Runnable {

    public Socket clientSocket;
    public PrintStream outToClient = null;
    public BufferedReader inFromClient = null;
    private String serverVersion;
    private MsgType msgType;

    /**
     *
     * @param clientSocket socket to be handled
     * @param version Server version
     */
    public ClientThread(Socket clientSocket, String version) {
        try {
            serverVersion = version;
            this.clientSocket = clientSocket;
            // Get Input and Output streams
            outToClient = new PrintStream(clientSocket.getOutputStream(), true);
            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Return a welcome message
     *
     * @return msg Return a welcome message
     */
    private String getWelcomeMessage() {
        String msg;
        msg = "Server version " + serverVersion;
        return msg;
    }

    /**
     *
     */
    @Override
    public void run() {
        String responseMsg;
        //Sends a welcome message to the user, with server info
        sendResponse(getWelcomeMessage());

        //Process commands one by one
        //String to save input line to
        int line;

        while ((line = readLine()) != -1) {
            //Checking that the object was successfully created
            if (line != -1) {

                //Execute command based on msgtype.. 
                responseMsg = doCommandList(line);

                //Sends response inFromClient
                    this.sendResponse(responseMsg);
            }
            else {
                System.out.println("Something wrong with parsing client input: "
                        + line);
            }
        }
        // Terminate the connection
        shutdown();
    }

    /**
     * Shuts down the TCP connection to the client. Posts information about
     * disconnecting
     */
    private void shutdown() {
        System.out.println("Closing connection to "
                + clientSocket.getInetAddress().getHostName()
                + " : " + clientSocket.getPort());
        try {
            if (inFromClient != null) {
                inFromClient.close();
                inFromClient = null;
            }
            if (outToClient != null) {
                outToClient.close();
                outToClient = null;
            }
            if (clientSocket != null) {
                clientSocket.close();
                clientSocket = null;
            }
        } catch (IOException ex) {
            System.out.println("Unable to close connection: " + ex.getMessage());
        }
        System.out.println("Connection successfully closed");
    }

    /**
     * Read a line of input from the client
     *
     * @return the read line
     */
    private int readLine() {
        try {
            int line = Integer.parseInt(inFromClient.readLine());
            return line;
        } catch (IOException ex) {
            return -1;
        }
    }

    /**
     * Print the welcome message
     *
     * @param welcomeMessage
     */
    private void sendResponse(String welcomeMessage) {
        if (outToClient != null) {
            outToClient.println(welcomeMessage);
        }
    }

    /**
     * Execute commands and/or return aguments based on message type.
     *
     * @return Returns a list with the values or message requested
     * @param fromClient Message from client
     */
    private String doCommandList(int fromClient) {
        String msg;
        msg = "Do command entered";
        
        switch(fromClient){
            case MsgType.START_VIDEO_STREAM :
                new Thread(new VideoStream()).start();
                msg = "Videostream started";
                break;
        }

        /**
         * ***Example*** //CHECKING WHAT COMMAND IN MSG TYPE.. UPDATING NEW X
         * VALUE if (fromClient.getType() == MsgType.UPDATE_ROTATION) { if
         * (argumentList != null && argumentList.get(0) != null) {
         * Values.BaseValueToPLS = argumentList.get(0); } else {
         * System.out.println("Arguments for specified " + fromClient.getType()
         * + " was wrong"); } }
         */
        return msg;
    }
}
