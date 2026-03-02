import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageGray {

    public static void main(String[] args) {

        try {
            BufferedImage image = ImageIO.read(new File("src/jahz_conde.png"));

            long start = System.currentTimeMillis();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {

                    int rgb = image.getRGB(x, y);

                    int r = (rgb >> 16) & 0xff;
                    int g = (rgb >> 8) & 0xff;
                    int b = rgb & 0xff;

                    //  grayscale formula
                    int gray = (r + g + b) / 3;

                    int newRGB = (gray << 16) | (gray << 8) | gray;

                    image.setRGB(x, y, newRGB);
                }
            }

            long end = System.currentTimeMillis();

            System.out.println("Single-thread time: " +
                    (end - start) / 1_000_000 + " ms");

            ImageIO.write(image, "png",
                    new File("src/output_single.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}