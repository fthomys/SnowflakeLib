package me.fthomys.SnowflakeLib;

import java.time.Instant;
import java.util.Date;

public class DiscordSnowflakeDecoder implements SnowflakeDecoder {
    public static final long DISCORD_EPOCH = 1420070400000L; // Jan 1st, 2015

    @Override
    public DecodedId decode(String idStr, String format, String timezone) {
        long id = Long.parseUnsignedLong(idStr);

        long timestamp = (id >> 22) + DISCORD_EPOCH;
        int workerId = (int) ((id >> 17) & 0x1F);
        int processId = (int) ((id >> 12) & 0x1F);
        int sequence = (int) (id & 0xFFF);

        Instant instant = Instant.ofEpochMilli(timestamp);
        Date date = new Date(timestamp);

        return new DecodedId(timestamp, workerId, processId, sequence, timezone, date, instant);
    }
}
