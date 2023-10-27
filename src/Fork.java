public class Fork {
    private boolean free;

    public Fork() {
        this.free = true;
    }

    public synchronized boolean isFree() {
        return free;
    }

    public synchronized void setFree(boolean free) {
        this.free = free;
    }
}
