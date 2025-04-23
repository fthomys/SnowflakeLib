package me.fthomys.SnowflakeLib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NtpClientTest {

    @Test
    public void testFetchTime() {
        Long time = NtpClient.fetchTime("0.de.pool.ntp.org");
        assertNotNull(time, "NTP time should not be null");

        long local = System.currentTimeMillis();
        long drift = Math.abs(local - time);

        assertTrue(drift < 10_000, "Drift is too large (more than 10s): " + drift);
    }
}