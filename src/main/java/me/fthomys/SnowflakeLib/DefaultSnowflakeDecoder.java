package me.fthomys.SnowflakeLib;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DefaultSnowflakeDecoder implements SnowflakeDecoder {
    private final long epoch;

    public DefaultSnowflakeDecoder(long epoch) {
        this.epoch = epoch;
    }

    public DecodedId decode(String idStr, String format, String timezone) {
        long id = Long.parseLong(idStr);

        long timestamp = (id >> 22) + epoch;
        int workerId = (int) ((id >> 17) & 0x1F);
        int processId = (int) ((id >> 12) & 0x1F);
        int sequence = (int) (id & 0xFFF);

        Instant instant = Instant.ofEpochMilli(timestamp);
        String formatted = DateTimeFormatter.ofPattern(format)
                .withZone(ZoneId.of(timezone == null ? TimeZone.getDefault().getID() : timezone))
                .format(instant);

        return new DecodedId(timestamp, formatted, workerId, processId, sequence, timezone);
    }
}