package GUI;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Sigurd N. Eikrem
 * @version 28-Sep-2017
 */
public class VideoStream implements Runnable {

    private int camToOpen;
    private DatagramSocket serverSocket;
    private byte[] sendData;
    private int UDPport;
    private InetAddress IpAddress;

    public VideoStream() {
        camToOpen = 0;
        UDPport = 6000;
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
            // Open a videosource
            VideoCapture cam = new VideoCapture(camToOpen);
            Mat frame = new Mat();
            // Keep sending images to make a video
            while (true) {
                cam.read(frame);
                // resize 
                Size size = new Size(200, 100);
                Imgproc.resize(frame, frame, size);
                // Prepare data to send
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
