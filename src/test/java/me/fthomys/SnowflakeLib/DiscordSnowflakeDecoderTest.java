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
        long id = 175928847299117063L;
        DiscordSnowflakeDecoder decoder = new DiscordSnowflakeDecoder();
        String format = "yyyy-MM-dd HH:mm:ss";

        DecodedId decoded = decoder.decode(id);

        long expectedTimestamp = (id >> 22) + DiscordSnowflakeDecoder.DISCORD_EPOCH;
        assertEquals(expectedTimestamp, decoded.getTimestamp());

        assertEquals((int)((id >> 17) & 0x1F), decoded.getWorkerId());
        assertEquals((int)((id >> 12) & 0x1F), decoded.getProcessId());
        assertEquals((int)(id & 0xFFF), decoded.getSequence());

        assertEquals(new Date(expectedTimestamp), decoded.getDate());
        assertEquals(Instant.ofEpochMilli(expectedTimestamp), decoded.getInstant());
        assertEquals(expectedTimestamp, decoded.getDate().getTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)
                .withZone(ZoneId.systemDefault());
        String formattedDate = formatter.format(decoded.getInstant());
        assertEquals(formattedDate, formatter.format(decoded.getDate().toInstant()));
    }
}