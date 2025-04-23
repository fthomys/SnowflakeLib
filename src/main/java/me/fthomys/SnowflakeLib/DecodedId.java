package me.fthomys.SnowflakeLib;

public class DecodedId {
    private final long timestamp;
    private final String datetime;
    private final int workerId;
    private final int processId;
    private final int sequence;
    private final String timezone;

    public DecodedId(long timestamp, String datetime, int workerId, int processId, int sequence, String timezone) {
        this.timestamp = timestamp;
        this.datetime = datetime;
        this.workerId = workerId;
        this.processId = processId;
        this.sequence = sequence;
        this.timezone = timezone;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getWorkerId() {
        return workerId;
    }

    public int getProcessId() {
        return processId;
    }

    public int getSequence() {
        return sequence;
    }

    public String getTimezone() {
        return timezone;
    }

    @Override
    public String toString() {
        return "DecodedId{" +
                "timestamp=" + timestamp +
                ", datetime='" + datetime + '\'' +
                ", workerId=" + workerId +
                ", processId=" + processId +
                ", sequence=" + sequence +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}
