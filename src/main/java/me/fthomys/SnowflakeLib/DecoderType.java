package me.fthomys.SnowflakeLib;

/**
 * Represents the decoding types supported by the system for interpreting unique identifier formats.
 * The decoding strategy determines how a specific ID is to be interpreted and broken down
 * into its constituent parts.
 *
 * Enum Constants:
 *  - DEFAULT: A general-purpose decoding type meant for standard cases. Meant for IDs that are generated with this library!
 *  - DISCORD: Decoding style tailored specifically for IDs used in Discord's snowflake system.
 *  - TWITTER: Decoding style tailored specifically for IDs used in Twitter's snowflake system.
 */
public enum DecoderType {
    DEFAULT,
    DISCORD,
    TWITTER
}
