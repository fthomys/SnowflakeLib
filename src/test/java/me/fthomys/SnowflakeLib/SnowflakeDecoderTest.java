package me.fthomys.SnowflakeLib;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SnowflakeDecoderTest {

    @Test
    public void testDecodeKnownDiscordSnowflake() {
        long id = 175928847299117063L;
        long ts = 1330779680;
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

    @Test
    public void testDecodeValidSnowflake() {
        long customTimestamp = 1577836800000L;
        long timestampPart = (customTimestamp - TwitterSnowflakeDecoder.TWITTER_EPOCH) << 22;

        int workerId = 17;
        int processId = 9;
        int sequence = 123;

        long workerPart = ((long) workerId & 0x1F) << 17;
        long processPart = ((long) processId & 0x1F) << 12;
        long sequencePart = sequence & 0xFFF;

        long snowflakeId = timestampPart | workerPart | processPart | sequencePart;

        TwitterSnowflakeDecoder decoder = new TwitterSnowflakeDecoder();
        DecodedId decoded = decoder.decode(snowflakeId);

        assertEquals(customTimestamp, decoded.getTimestamp());
        assertEquals(workerId, decoded.getWorkerId());
        assertEquals(processId, decoded.getProcessId());
        assertEquals(sequence, decoded.getSequence());

        assertEquals(new Date(customTimestamp), decoded.getDate());
        assertEquals(Instant.ofEpochMilli(customTimestamp), decoded.getInstant());
        assertEquals(customTimestamp, decoded.getDate().getTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        String formatted = formatter.format(decoded.getInstant());
        assertEquals(formatted, formatter.format(decoded.getDate().toInstant()));
    }

    @Test
    public void testDecodeId() {
        long epoch = 1609459200000L;
        SnowflakeGenerator generator = new SnowflakeFactory()
                .withEpoch(epoch)
                .withWorkerId(2)
                .build();

        Long id = generator.generateId();
        SnowflakeDecoder decoder = new DefaultSnowflakeDecoder(epoch);
        DecodedId decoded = decoder.decode(id);

        assertNotNull(decoded);
        assertEquals(2, decoded.getWorkerId());
        assertNotNull(decoded.getDate());
        assertNotNull(decoded.getInstant());
        assertEquals(decoded.getDate().getTime(), decoded.getInstant().toEpochMilli());
        assertEquals(decoded.getDate().getTime(), decoded.getTimestamp());
        assertEquals(decoded.getDate().getTime(), (id >> 22) + epoch);
        assertEquals(decoded.getDate().getTime(), decoded.getTimestamp());
        assertEquals((id >> 17) & 0x1F, decoded.getWorkerId());
        assertEquals((id >> 12) & 0x1F, decoded.getProcessId());
        assertEquals(id & 0xFFF, decoded.getSequence());
        assertEquals((id >> 22) + epoch, decoded.getTimestamp());
    }
}