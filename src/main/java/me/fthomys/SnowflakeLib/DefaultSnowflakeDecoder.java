package me.fthomys.SnowflakeLib;

import java.time.Instant;
import java.util.Date;

public class DefaultSnowflakeDecoder implements SnowflakeDecoder {
    private final long epoch;

    public DefaultSnowflakeDecoder(Long epoch) {
        this.epoch = epoch;
    }

    @Override
    public DecodedId decode(Long id) {

        long timestamp = (id >> 22) + epoch;
        int workerId = (int) ((id >> 17) & 0x1F);
        int processId = (int) ((id >> 12) & 0x1F);
        int sequence = (int) (id & 0xFFF);

        Instant instant = Instant.ofEpochMilli(timestamp);
        Date date = new Date(timestamp);

        return new DecodedId(timestamp, workerId, processId, sequence, date, instant);
    }

}
