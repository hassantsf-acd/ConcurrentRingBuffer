import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class RingBuffer {
    private String[] buffer;
    private int front;
    private int rear;
    private int size;
    private int initialSize;
    private final int maxSize;
    private final ReentrantLock lock;

    public RingBuffer(int initialSize) {
        this.buffer = new String[initialSize];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
        this.initialSize = initialSize;
        this.maxSize = 5 * initialSize;
        this.lock = new ReentrantLock();
    }

    public int getSize() {
        return size;
    }

    public void put(String data) {
        lock.lock();
        try {
            if (isFull()) {
                increaseSize();
            }

            rear = (rear + 1) % buffer.length;
            buffer[rear] = data;
            size++;
        } finally {
            lock.unlock();
        }
    }

    public String get() {
        lock.lock();
        try {
            if (isEmpty()) {
                throw new IllegalStateException("Buffer is empty");
            }

            var data = buffer[front];
            front = (front + 1) % buffer.length;
            size--;
            return data;
        } finally {
            lock.unlock();
        }
    }

    public boolean isFull() {
        return size == buffer.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void increaseSize() {
        if (buffer.length >= maxSize) {
            throw new IllegalStateException("Buffer has reached maximum size");
        }

        lock.lock();
        try {
            var newBufferSize = buffer.length + initialSize;
            var newBuffer = Arrays.copyOf(buffer, newBufferSize);

            for (int i = 0; i < size; i++) {
                newBuffer[i] = buffer[(front + i) % buffer.length];
            }

            buffer = newBuffer;
            front = 0;
            rear = size - 1;
        } finally {
            lock.unlock();
        }
    }

    public String[] flush() {
        lock.lock();
        try {
            var contents = new String[size];
            var currentIndex = front;

            for (int i = 0; i < size; i++) {
                contents[i] = buffer[currentIndex];
                currentIndex = (currentIndex + 1) % buffer.length;
            }

            front = 0;
            rear = -1;
            size = 0;

            return contents;
        } finally {
            lock.unlock();
        }
    }
}
