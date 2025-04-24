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
