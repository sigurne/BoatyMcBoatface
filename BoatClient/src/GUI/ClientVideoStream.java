package GUI;

import Main.MsgType;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * UDP client
 *
 * @author Sigurd N. Eikrem
 * @version 22-Sep-2017
 */
public class ClientVideoStream implements Runnable {

    private DatagramSocket UDPClient;
    private byte[] receiveData;
    private BufferedReader inFromServer = null;
    private boolean stop = false;
    private ImageView imageView;

    public ClientVideoStream(ImageView iv) {
        imageView = iv;
    }

    /**
     *
     */
    @Override
    public void run() {
        byte[] buffer = new byte[20000];
        try {
            UDPClient = new DatagramSocket(6000);
            while (!stop) {
                receiveData = new byte[20000];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                UDPClient.receive(receivePacket);
                if (receivePacket.getLength() > 0) {
                    buffer = receivePacket.getData();
                    Image im = new Image(new ByteArrayInputStream(buffer));
                    imageView.setImage(im);
                }
            }
        } catch (IOException e) {
        }
                UDPClient.close();
    }

    /**
     * Terminate thread
     */
    public void terminate() {
        stop = true;
    }
}
