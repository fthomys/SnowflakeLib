package me.fthomys.SnowflakeLib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnowflakeDecoderTest {

    @Test
    public void testDecodeId() {
        long epoch = 1609459200000L;
        SnowflakeGenerator generator = new SnowflakeFactory()
                .withEpoch(epoch)
                .withWorkerId(2)
                .withProcessId(3)
                .build();

        String id = generator.generateId();
        SnowflakeDecoder decoder = new DefaultSnowflakeDecoder(epoch);
        DecodedId decoded = decoder.decode(id, "yyyy-MM-dd HH:mm:ss", "UTC");

        assertNotNull(decoded);
        assertEquals(2, decoded.getWorkerId());
        assertEquals(3, decoded.getProcessId());
        assertNotNull(decoded.getDate());
    }
}
