package me.fthomys.SnowflakeLib;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DiscordSnowflakeDecoder implements SnowflakeDecoder {
    public static final long DISCORD_EPOCH = 1420070400000L; // Jan 1st, 2015

    @Override
    public DecodedId decode(String idStr, String format, String timezone) {
        long id = Long.parseUnsignedLong(idStr);

        long timestampPart = (id >> 22);
        long timestamp = timestampPart + DISCORD_EPOCH;

        int workerId = (int)((id >> 17) & 0x1F);     // 5 bits
        int processId = (int)((id >> 12) & 0x1F);    // 5 bits
        int sequence = (int)(id & 0xFFF);            // 12 bits

        Instant instant = Instant.ofEpochMilli(timestamp);
        String formatted = DateTimeFormatter.ofPattern(format)
                .withZone(ZoneId.of(timezone == null ? TimeZone.getDefault().getID() : timezone))
                .format(instant);

        return new DecodedId(timestamp, formatted, workerId, processId, sequence, timezone);
    }
}
