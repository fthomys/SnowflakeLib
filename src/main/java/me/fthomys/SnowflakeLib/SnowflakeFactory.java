package me.fthomys.SnowflakeLib;
public class SnowflakeFactory {
    private long epoch = 1609459200000L;
    private int workerId = 1;
    private int processId = 1;
    private boolean ntpCheck = false;
    private String ntpServer = "0.de.pool.ntp.org";

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

    public SnowflakeFactory withNtpServer(String ntpServer) {
        this.ntpServer = ntpServer;
        return this;
    }

    public SnowflakeGenerator build() {
        return new SnowflakeGenerator(epoch, workerId, processId, ntpCheck, ntpServer);
    }
}
