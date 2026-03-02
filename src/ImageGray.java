import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGray {
    static void main() {

        try {
            BufferedImage image = new BufferedImage(new File("src/jahz_condepng.png")));

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {

                    int rgb = image.getRGB(x, y);
                    int r =  (rgb >> 16) & 0xff;
                    int g =  (rgb >> 8) & 0xff;
                    int b =  rgb & 0xff;

                    int newRGB = (r << 16) | (g << 8) | b;

                    image.setRGB(x, y, newRGB);
                }
            }

            // output the iamge
        } catch (IOException e) {

        }
    }
}
