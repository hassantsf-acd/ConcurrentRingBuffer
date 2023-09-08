import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class PullingThread implements Runnable {
    private Core core;
    private ReentrantLock lock;

    public PullingThread(Core core) {
        this.core = core;
        this.lock = new ReentrantLock();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            var buffer = core.getBuffer();

            if (buffer.isFull()) {
                lock.lock();
                try {
                    var bufferData = buffer.flush();
                    saveDataToFile(bufferData);
                } finally {
                    lock.unlock();
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDataToFile(String[] bufferData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("buffer_data" + core.getId() + ".txt", true))) {
            System.out.println("Core " + core.getId() + " flushed!");
            var now = new Date();
            writer.println(now);
            for (var data : bufferData) {
                writer.println(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}