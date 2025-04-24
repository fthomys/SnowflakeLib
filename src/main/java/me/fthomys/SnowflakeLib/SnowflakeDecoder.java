package me.fthomys.SnowflakeLib;

public interface SnowflakeDecoder {

    /**
     * Decodes a given snowflake ID into its constituent parts: timestamp, worker ID,
     * process ID, and sequence, along with the corresponding date and instant representations.
     *
     * @param id The snowflake ID to decode. Must be a non-null Long value.
     * @return A DecodedId object containing the parsed components of the provided snowflake ID.
     */
    DecodedId decode(Long id);
}
