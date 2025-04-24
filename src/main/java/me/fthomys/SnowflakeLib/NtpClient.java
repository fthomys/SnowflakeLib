package me.fthomys.SnowflakeLib;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * A flexible and robust NTP client with timezone-aware timestamp features.
 */
public class NtpClient {

    public static class NtpTimestamp {
        private final long unixMillis;
        private final long unixSeconds;
        private final long fractionalMillis;

        public NtpTimestamp(long unixSeconds, long fractionalPart) {
            this.unixSeconds = unixSeconds;
            this.fractionalMillis = fractionalPart / 4294967L;
            this.unixMillis = (unixSeconds * 1000L) + fractionalMillis;
        }

        public long getUnixMillis() {
            return unixMillis;
        }

        public long getUnixSeconds() {
            return unixSeconds;
        }

        public long getFractionalMillis() {
            return fractionalMillis;
        }

        public Date toDate() {
            return new Date(unixMillis);
        }

        public Instant toInstant() {
            return Instant.ofEpochMilli(unixMillis);
        }

        public ZonedDateTime toZonedDateTime(String zoneId) {
            return toInstant().atZone(ZoneId.of(zoneId));
        }

        public ZonedDateTime toZonedDateTime(ZoneId zoneId) {
            return toInstant().atZone(zoneId);
        }

        public ZonedDateTime toZonedDateTime() {
            return toInstant().atZone(ZoneId.systemDefault());
        }

        @Override
        public String toString() {
            return String.format("NtpTimestamp{millis=%d, instant=%s, localZoned=%s}",
                    unixMillis, toInstant(), toZonedDateTime());
        }
    }

    public static NtpTimestamp fetchTimestamp(String server, int port, int timeoutMillis) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(server);
            byte[] buffer = new byte[48];
            buffer[0] = 0x1B; // LI, Version, Mode
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

            socket.setSoTimeout(timeoutMillis);
            socket.send(packet);
            socket.receive(packet);

            long secondsSince1900 = ((buffer[40] & 0xFFL) << 24) |
                    ((buffer[41] & 0xFFL) << 16) |
                    ((buffer[42] & 0xFFL) << 8) |
                    (buffer[43] & 0xFFL);

            long fraction = ((buffer[44] & 0xFFL) << 24) |
                    ((buffer[45] & 0xFFL) << 16) |
                    ((buffer[46] & 0xFFL) << 8) |
                    (buffer[47] & 0xFFL);

            long unixSeconds = secondsSince1900 - 2208988800L;

            return new NtpTimestamp(unixSeconds, fraction);
        } catch (IOException e) {
            return null;
        }
    }

    public static Long fetchTime(String server) {
        NtpTimestamp ts = fetchTimestamp(server, 123, 1000);
        return ts != null ? ts.getUnixMillis() : null;
    }
}
