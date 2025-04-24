package me.fthomys.SnowflakeLib;


/**
 * SnowflakeFactory is a builder class for creating instances of {@link SnowflakeGenerator}.
 * It provides configuration options for customizing the parameters used in ID generation.
 *
 * The SnowflakeFactory allows adjusting parameters such as the epoch, worker ID, and process ID,
 * as well as enabling or disabling NTP-based clock drift detection and specifying a custom NTP server.
 */
public class SnowflakeFactory {
    private long epoch = 1609459200000L;
    private int workerId = 1;
    private int processId = getProcessIdFromRuntimeMxBean();
    private boolean ntpCheck = false;
    private String ntpServer = "0.de.pool.ntp.org";

    private int getProcessIdFromRuntimeMxBean() {
        String name = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(name.split("@")[0]);
    }

    public SnowflakeFactory withEpoch(long epoch) {
        this.epoch = epoch;
        return this;
    }

    public SnowflakeFactory withWorkerId(int workerId) {
        this.workerId = workerId;
        return this;
    }

    public SnowflakeFactory withProcessId(int processId) {
        this.processId = processId;
        return this;
    }

    public SnowflakeFactory enableNtpCheck(boolean enable) {
        this.ntpCheck = enable;
        return this;
    }

    public SnowflakeFactory withCustomNtpServer(String ntpServer) {
        this.ntpServer = ntpServer;
        return this;
    }

    public SnowflakeGenerator build() {
        return new SnowflakeGenerator(epoch, workerId, processId, ntpCheck, ntpServer);
    }
}
