package me.fthomys.SnowflakeLib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnowflakeDecoderFactoryTest {

    @Test
    public void testCreateDefaultDecoder() {
        SnowflakeDecoder decoder = SnowflakeDecoderFactory.create(DecoderType.DEFAULT, 1609459200000L);
        assertInstanceOf(DefaultSnowflakeDecoder.class, decoder);
    }

    @Test
    public void testCreateDiscordDecoder() {
        SnowflakeDecoder decoder = SnowflakeDecoderFactory.create(DecoderType.DISCORD);
        assertInstanceOf(DiscordSnowflakeDecoder.class, decoder);
    }

    @Test
    public void testCreateDefaultDecoderWithCustomEpoch() {
        long customEpoch = 1609459200000L;
        SnowflakeDecoder decoder = SnowflakeDecoderFactory.create(DecoderType.DEFAULT, customEpoch);
        assertInstanceOf(DefaultSnowflakeDecoder.class, decoder);
    }
}