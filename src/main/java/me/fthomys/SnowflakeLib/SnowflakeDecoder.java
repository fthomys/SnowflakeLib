package me.fthomys.SnowflakeLib;

public interface SnowflakeDecoder {
    DecodedId decode(String idStr, String format, String timezone);
}
