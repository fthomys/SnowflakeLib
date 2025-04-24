package me.fthomys.SnowflakeLib;

import java.time.Instant;
import java.util.Date;

public class TwitterSnowflakeDecoder implements SnowflakeDecoder {
    public static final long TWITTER_EPOCH = 1288834974657L; // Nov 4th, 2010

    /**
     * Decodes a given snowflake ID into its constituent parts: timestamp, worker ID,
     * process ID, and sequence, along with the corresponding date and instant representations.
     *
     * @param id The snowflake ID to decode. Must be a non-null Long value.
     * @return A DecodedId object containing the parsed components of the provided snowflake ID.
     */
    @Override
    public DecodedId decode(Long id) {

        long timestamp = (id >> 22) + TWITTER_EPOCH;
        int workerId = (int) ((id >> 17) & 0x1F);
        int processId = (int) ((id >> 12) & 0x1F);
        int sequence = (int) (id & 0xFFF);

        Instant instant = Instant.ofEpochMilli(timestamp);
        Date date = new Date(timestamp);

        return new DecodedId(timestamp, workerId, processId, sequence, date, instant);
    }
}
