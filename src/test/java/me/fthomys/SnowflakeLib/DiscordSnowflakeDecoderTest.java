package me.fthomys.SnowflakeLib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiscordSnowflakeDecoderTest {

    @Test
    public void testDecodeKnownDiscordSnowflake() {
        String idStr = "175928847299117063";
        SnowflakeDecoder decoder = new DiscordSnowflakeDecoder();
        DecodedId decoded = decoder.decode(idStr, "yyyy-MM-dd HH:mm:ss.SSS", "UTC");

        assertEquals("2016-04-30 11:18:25.796", decoded.getDatetime());
        assertEquals(41944705796L + 1420070400000L, decoded.getTimestamp());
    }
}