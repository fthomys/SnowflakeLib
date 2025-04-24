package me.fthomys.SnowflakeLib;

import java.time.Instant;
import java.util.Date;

public class DecodedId {
    private final long timestamp;
    private final int workerId;
    private final int processId;
    private final int sequence;
    private final Date date;
    private final Instant instant;

    public DecodedId(long timestamp, int workerId, int processId, int sequence,
                     Date date, Instant instant) {
        this.timestamp = timestamp;
        this.workerId = workerId;
        this.processId = processId;
        this.sequence = sequence;
        this.date = new Date(date.getTime());
        this.instant = instant;
    }

    public long getTimestamp() {
        return timestamp;
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


    public Date getDate() {
        return date;
    }

    public Instant getInstant() {
        return instant;
    }

    @Override
    public String toString() {
        return "DecodedId{" +
                "timestamp=" + timestamp +
                ", workerId=" + workerId +
                ", processId=" + processId +
                ", sequence=" + sequence +
                ", instant=" + instant +
                ", date=" + date +
                '}';
    }
}
