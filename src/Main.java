import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.print("Enter n = ");
        var n = scanner.nextInt();
        System.out.print("Enter m = ");
        var m = scanner.nextInt();
        scanner.close();

        for (int i = 1; i <= n; i++) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("buffer_data" + i + ".txt"))) {
                writer.print("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Core[] cores = new Core[n];

        for (int i = 0; i < n; i++) {
            cores[i] = new Core(m, i + 1);
        }

        var pullingThreads = new Thread[n];



        for (int i = 0; i < n; i++) {
            int numberOfThreads = new Random().nextInt(3) + 1;

            for (int j = 0; j < numberOfThreads; j++) {
                var systemThread = new Thread(new SystemThread(cores[i], j + 1));
                systemThread.start();
            }
        }

        for (int i = 0; i < n; i++) {
            var pullingThread = new PullingThread(cores[i]);
            pullingThreads[i] = new Thread(pullingThread);
            pullingThreads[i].start();
        }
    }
}
