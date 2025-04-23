package me.fthomys.SnowflakeLib;

public class SnowflakeDecoderFactory {

    public static SnowflakeDecoder create(DecoderType type) {
        switch (type) {
            case DISCORD:
                return new DiscordSnowflakeDecoder();
            case DEFAULT:
            default:
                return new DefaultSnowflakeDecoder(1609459200000L);
        }
    }

    public static SnowflakeDecoder create(DecoderType type, long customEpoch) {
        switch (type) {
            case DISCORD:
                return new DiscordSnowflakeDecoder();
            case DEFAULT:
            default:
                return new DefaultSnowflakeDecoder(customEpoch);
        }
    }
}
