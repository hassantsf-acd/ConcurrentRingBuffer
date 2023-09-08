import java.util.Random;

public class SystemThread implements Runnable {
    private final int id;
    private final Core core;

    public SystemThread(Core core, int id) {
        this.core = core;
        this.id = id;
    }

    private void before(int timestamp) {
        core.getBuffer().put("[" + timestamp + "] Thread " + id + " Before");
    }

    private void main() throws InterruptedException {
        Thread.sleep(5_000);
    }

    private void after(int timestamp) {
        core.getBuffer().put("[" + timestamp + "] Thread " + id + " After");
    }

    @Override
    public void run() {
        while (true) {
            var timestamp = new Random().nextInt(1000000);
            before(timestamp);

            try {
                main();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            after(timestamp);
        }
    }
}