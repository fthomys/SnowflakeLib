package me.fthomys.SnowflakeLib;

/**
 * Factory class for creating instances of {@link SnowflakeDecoder} based on a specified decoding type and an optional custom epoch time.
 * This class provides factory methods to initialize decoders compatible with different Snowflake ID systems such as Discord, Twitter,
 * or a general-purpose default implementation.
 */
public class SnowflakeDecoderFactory {

    public static SnowflakeDecoder create(DecoderType type) {
        return create(type, 1609459200000L);
    }


    /**
     * Creates and returns an instance of the {@link SnowflakeDecoder} based on the specified decoder type
     * and custom epoch. This factory method supports the creation of decoders for specific Snowflake ID
     * implementations such as Discord, Twitter, or a general-purpose default implementation.
     *
     * @param type The type of decoder to create. Must be one of {@link DecoderType#DISCORD},
     *             {@link DecoderType#TWITTER}, or {@link DecoderType#DEFAULT}.
     * @param customEpoch The custom epoch timestamp in milliseconds for the default decoder.
     *                    This value is ignored for Discord and Twitter decoders.
     * @return A new instance of {@link SnowflakeDecoder} tailored to the specified decoder type.
     */
    public static SnowflakeDecoder create(DecoderType type, long customEpoch) {
        if (type == DecoderType.DISCORD) {
            return new DiscordSnowflakeDecoder();
        }
        if (type == DecoderType.TWITTER) {
            return new TwitterSnowflakeDecoder();
        }
        return new DefaultSnowflakeDecoder(customEpoch);
    }
}
