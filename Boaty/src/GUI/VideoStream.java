package GUI;

import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Sigurd N. Eikrem
 * @version 28-Sep-2017
 */
public class VideoStream implements Runnable {

    private ImageView imageView;
    private int camToOpen;
    private DatagramSocket serverSocket;
    private byte[] sendData;
    private int port;
    private InetAddress IpAddress;

    public VideoStream() {
        camToOpen = 1;
        port = 6000;

    }

    @Override
    public void run() {
        try {
            IpAddress = InetAddress.getByName("192.168.0.100");
            serverSocket = new DatagramSocket(6000);

            // Open a videosource
            VideoCapture cam = new VideoCapture(camToOpen);
            Mat frame = new Mat();
            // 
            while (cam.isOpened()) {
                // resize før sending
                cam.read(frame);
                MatOfByte buffer = new MatOfByte();
                // sende buffer
                Imgcodecs.imencode(".png", frame, buffer);
                buffer.toArray();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpAddress, port);
                serverSocket.send(sendPacket);
            }
        } catch (Exception ex) {
            Logger.getLogger(VideoStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
