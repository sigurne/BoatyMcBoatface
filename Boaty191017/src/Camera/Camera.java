package Camera;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 * A class to handle camera activity, open camera, take picture
 *
 * @author Sigurd N. Eikrem
 * @version 20-Oct-2017
 */
public class Camera {

    private int camToOpen = 0;
    Mat frame;

    public Camera() {
        // Open a videosource
        VideoCapture cam = new VideoCapture(camToOpen);
        frame = new Mat();
        // Keep sending images to make a video
        while (cam.isOpened()) {
            cam.read(frame);
        }
    }

    /**
     * Return captured frame
     *
     * @return frame
     */
    public synchronized Mat getFrame() {
        return frame;
    }
}
