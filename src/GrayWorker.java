import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class GrayWorker implements Runnable {

    private BufferedImage image;
    private int startRow;
    private int endRow;

    public GrayWorker(BufferedImage image, int startRow, int endRow) {
        this.image = image;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    public void run() {
        for (int y = startRow; y < endRow; y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int rgb = image.getRGB(x, y);

                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                int gray = (r + g + b) / 3;

                int newRGB = (gray << 16) | (gray << 8) | gray;

                image.setRGB(x, y, newRGB);
            }
        }
    }

    static void main() {
        try {
            BufferedImage image = ImageIO.read(new File("src/jahz_condepng.png"));

            int threadCount = Runtime.getRuntime().availableProcessors();
            Thread[] threads = new Thread[threadCount];

            int height = image.getHeight();
            int rowsPerThread = height / threadCount;

            long start = System.nanoTime();

            for (int i = 0; i < threadCount; i++) {

                int startRow = i * rowsPerThread;
                int endRow = (i == threadCount - 1)
                        ? height
                        : startRow + rowsPerThread;

                threads[i] = new Thread(
                        new GrayWorker(image, startRow, endRow)
                );

                threads[i].start();
            }

            // WAIT FOR ALL THREADS
            for (int i = 0; i < threadCount; i++) {
                threads[i].join();
            }

            long end = System.nanoTime();

            System.out.println("Multi-thread time (" +
                    threadCount + " threads): " +
                    (end - start) / 1_000_000 + " ms");

            ImageIO.write(image, "png",
                    new File("src/output_multi.png"));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
}