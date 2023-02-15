package fund.pillow.assignment.ratelimit.filter;

public class TBLimiter {

    private double capacity;
    private double tokens;
    private long lastRefill;
    private final double refillRate;


    public TBLimiter(double permitsPerSecond) {
        this.capacity = permitsPerSecond;
        this.tokens = permitsPerSecond;
        this.refillRate = permitsPerSecond;
        this.lastRefill = System.nanoTime();
    }

    public Integer timeWait(){
        return (int)((tokens - 1) / capacity)*1000;
    }


    public synchronized void acquire() {
        refill();
        if (tokens >= 1) {
            tokens -= 1;
        } else {
            double timeToWait = (tokens - 1) / capacity;
            try {
                Thread.sleep((long) (timeToWait * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            tokens = 0;
        }
    }

    private void refill() {
        long now = System.nanoTime();
        double tokensToAdd = ((now - lastRefill) * refillRate) / 1000000000.0;
        tokens = Math.min(capacity, tokens + tokensToAdd);
        lastRefill = now;
    }
}
