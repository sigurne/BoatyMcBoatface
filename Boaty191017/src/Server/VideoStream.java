package Server;

import Camera.Camera;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Send videostream over UDP
 *
 * @author Sigurd N. Eikrem
 * @version 28-Sep-2017
 */
public class VideoStream implements Runnable {

    private DatagramSocket serverSocket;
    private byte[] sendData;
    private int UDPport;
    private InetAddress IpAddress;
    private Camera camera;

    public VideoStream(Object camera) {
        UDPport = 6000;
        this.camera = (Camera) camera;
    }

    /**
     * Send one frame at a time over UDP.
     */
    @Override
    public void run() {
        try {
            System.out.println("Start videostream");
            IpAddress = InetAddress.getByName("192.168.0.100");
            serverSocket = new DatagramSocket();

            // Keep sending images to make a video
            while (true) {
                
                Mat frame = camera.getFrame();
                // resize 
                Size size = new Size(200, 100);
                Imgproc.resize(frame, frame, size);
                // Encode image
                MatOfByte buffer = new MatOfByte();
                Imgcodecs.imencode(".jpg", frame, buffer);
                sendData = buffer.toArray();
                // Send packet
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpAddress, UDPport);
                serverSocket.send(sendPacket);
            }
        } catch (Exception ex) {
        }
    }
}
