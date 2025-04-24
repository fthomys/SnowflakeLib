package me.fthomys.SnowflakeLib;

public class SnowflakeDecoderFactory {

    public static SnowflakeDecoder create(DecoderType type) {
        return create(type, 1609459200000L);
    }

    public static SnowflakeDecoder create(DecoderType type, long customEpoch) {
        if (type == DecoderType.DISCORD) {
            return new DiscordSnowflakeDecoder();
        }
        return new DefaultSnowflakeDecoder(customEpoch);
    }
}
