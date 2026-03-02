import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SingleWordCount {

    static void main() {


        try (BufferedReader br = new BufferedReader(new FileReader("src/bigfile.txt"))) {
            int count = 0;
            String line;

            long begin =  System.currentTimeMillis();
            while ((line = br.readLine()) != null) {
                count += line.trim().split("\\s+").length;
            }

            long end =  System.currentTimeMillis();

            System.out.println("WORDS = " + count);
            System.out.println("TIME = " + (end - begin));
        } catch (IOException e) {

        }
    }
}
