package BoatyMcBoatface;

/**
 * Message types used in the test application
 * @author Girts Strazdins, 2016-10-30, EDITED BY Sigurd Eikrem
 */
public class MsgType {
    // Sent from the client application
    public static final int SEND_COMMAND = 1; // The client sends an actuation command

    //Sent from the server
    //Server returns some info regarding what version it is running, etc
    public static final int SERVER_INFO = 101; 
    public static final int CMD_EXECUTED = 104; // Server returns requested sensor values
   
    //
    public static final int START_VIDEO_STREAM = 201; // Start sending images to the GUI
    
    public static final int IS_OBJ_DETECTED = 202; 
}
