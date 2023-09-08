public class Core {
    private RingBuffer buffer;
    private int id;

    public Core(int bufferSize, int id) {
        buffer = new RingBuffer(bufferSize);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public RingBuffer getBuffer() {
        return buffer;
    }
}