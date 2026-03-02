import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SingleWordCountWorker implements Runnable {

    int start, end, count;
    ArrayList<String> lines;

    public SingleWordCountWorker(int start, int end, ArrayList<String> lines) {
        this.start = start;
        this.end = end;
        this.lines = lines;
        this.count = 0;
    }

    @Override
    public void run() {
        String line;

        for (int i = start; i <= end; i++) {
            line = lines.get(i);
            count += line.trim().split("\\s+").length;
        }
    }

    public int getCount() {
        return count;
    }

    static void main() {

        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/bigfile.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {

        }

        int threadCount = 16;
        int n = lines.size();
        int partition = n / threadCount;

        SingleWordCountWorker[] workers = new SingleWordCountWorker[threadCount];

        for (int i = 0; i < threadCount; i++) {
            workers[i] = new SingleWordCountWorker(partition * i, i == threadCount - 1 ? n - 1 : (partition*(i+1) - 1), lines);
        }

        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(workers[i]);
        }

        long begin = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }

        int count = 0;
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
                count += workers[i].getCount();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("COUNT = " + count);
        System.out.println("TIME = " + (end - begin));

    }
}
