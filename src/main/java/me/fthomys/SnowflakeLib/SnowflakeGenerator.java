package me.fthomys.SnowflakeLib;


import java.util.concurrent.locks.ReentrantLock;

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

    private long waitNextMillis(long current) {
        long now = System.currentTimeMillis();
        while (now <= current) {
            try {
                Thread.sleep(0, 10000);
            } catch (InterruptedException ignored) {}
            now = System.currentTimeMillis();
        }
        return now;
    }
}