package me.fthomys.SnowflakeLib;


import java.util.concurrent.locks.ReentrantLock;


/**
 * The SnowflakeGenerator class is responsible for generating unique, time-ordered identifiers
 * using a Snowflake algorithm. This class ensures thread safety during ID generation and provides
 * optional NTP-based clock drift check for time synchronization.
 *
 * The IDs are composed of a timestamp, worker ID, process ID, and sequence number. The class
 * uses locks to ensure thread-safe ID generation across multiple threads.
 */
public final class SnowflakeGenerator {
    private static final int MAX_SEQUENCE = (1 << 12) - 1;
    private final long epoch;
    private final int workerId;
    private final int processId;
    private final boolean ntpCheck;
    private final String ntpServer;
    private final ReentrantLock lock = new ReentrantLock(true);

    private long lastTimestamp = -1L;
    private int sequence = 0;

    public SnowflakeGenerator(long epoch, int workerId, int processId, boolean ntpCheck, String ntpServer) {
        this.epoch = epoch;
        this.workerId = workerId & 0x1F;
        this.processId = processId & 0x1F;
        this.ntpCheck = ntpCheck;
        this.ntpServer = ntpServer;

        if (ntpCheck) checkClockDrift();
    }



    /**
     * Generates a unique identifier based on the Snowflake algorithm. The generated ID is a
     * combination of the current timestamp, worker ID, process ID, and a sequence number,
     * ensuring uniqueness across multiple threads and processes.
     *
     * @return a unique 64-bit identifier
     * @throws IllegalStateException if the system clock moves backwards
     */
    public Long generateId() {
        lock.lock();
        try {
            long now = System.currentTimeMillis();

            if (now < lastTimestamp) {
                throw new IllegalStateException("Clock moved backwards. Refusing to generate ID.");
            }

            if (now == lastTimestamp) {
                sequence = (sequence + 1) & MAX_SEQUENCE;
                if (sequence == 0) {
                    now = waitNextMillis(now);
                }
            } else {
                sequence = 0;
            }

            lastTimestamp = now;

            return ((now - epoch) << 22) |
                    ((long) workerId << 17) |
                    ((long) processId << 12) |
                    sequence;
        } finally {
            lock.unlock();
        }
    }



    /**
     * Verifies if there is a drift between the local system clock and the time
     * retrieved from a configured NTP server. If the drift exceeds a predefined
     * threshold, a warning is logged.
     *
     * The method performs the following checks and steps:
     * 1. Ensures that the NTP check feature is enabled. If disabled, the method exits.
     * 2. Validates that the NTP server address is non-null and non-empty. If invalid,
     *    throws an {@code IllegalArgumentException}.
     * 3. Attempts to fetch the current time from the specified NTP server using
     *    {@code NtpClient.fetchTime}. If the result is null (e.g., due to network
     *    issues), the method exits.
     * 4. Compares the local system time with the fetched NTP time. If the absolute
     *    difference exceeds 1000 milliseconds, logs a warning indicating the detected
     *    clock drift.
     *
     * @throws IllegalArgumentException if the NTP server is null or empty.
     */
    private void checkClockDrift() {
        if (!ntpCheck) return;


        if (ntpServer == null || ntpServer.isEmpty()) {
            throw new IllegalArgumentException("NTP server cannot be null or empty.");
        }


        Long ntpTime = NtpClient.fetchTime(ntpServer);
        if (ntpTime == null) return;

        long localTime = System.currentTimeMillis();
        long drift = Math.abs(localTime - ntpTime);

        if (drift > 1000) {
            System.err.println("Warning: Clock drift detected. Local time is off by " + drift + " ms.");
        }
    }

    /**
     * Waits until the current system time is greater than the provided timestamp.
     * This method continuously checks the system time in milliseconds and
     * suspends the thread briefly in case the time has not surpassed the given timestamp.
     *
     * @param current the timestamp in milliseconds that the method should wait to pass
     * @return the first system time in milliseconds that is greater than the provided timestamp
     */
    private long waitNextMillis(long current) {
        long now = System.currentTimeMillis();
        while (now <= current) {
            try {
                Thread.sleep(0, 10000);
            } catch (InterruptedException ignored) {
            }
            now = System.currentTimeMillis();
        }
        return now;
    }
}