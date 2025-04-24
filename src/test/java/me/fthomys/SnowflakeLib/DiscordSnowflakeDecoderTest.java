package me.fthomys.SnowflakeLib;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DiscordSnowflakeDecoderTest {

    @Test
    public void testDecodeKnownDiscordSnowflake() {
        String idStr = "175928847299117063";
        DiscordSnowflakeDecoder decoder = new DiscordSnowflakeDecoder();
        String format = "yyyy-MM-dd HH:mm:ss";
        String zone = "UTC";

        DecodedId decoded = decoder.decode(idStr, format, zone);

        long expectedTimestamp = (175928847299117063L >> 22) + DiscordSnowflakeDecoder.DISCORD_EPOCH;
        assertEquals(expectedTimestamp, decoded.getTimestamp());

        assertEquals((int)((175928847299117063L >> 17) & 0x1F), decoded.getWorkerId());
        assertEquals((int)((175928847299117063L >> 12) & 0x1F), decoded.getProcessId());
        assertEquals((int)(175928847299117063L & 0xFFF), decoded.getSequence());

        assertEquals(new Date(expectedTimestamp), decoded.getDate());
        assertEquals(Instant.ofEpochMilli(expectedTimestamp), decoded.getInstant());
        assertEquals("UTC", decoded.getTimezone());

        String formattedExpected = DateTimeFormatter.ofPattern(format)
                .withZone(ZoneId.of(zone))
                .format(Instant.ofEpochMilli(expectedTimestamp));

        assertEquals(formattedExpected, DateTimeFormatter.ofPattern(format)
                .withZone(ZoneId.of(decoded.getTimezone()))
                .format(decoded.getInstant()));
    }
}