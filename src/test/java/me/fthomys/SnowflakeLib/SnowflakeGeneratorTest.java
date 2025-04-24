package me.fthomys.SnowflakeLib;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SnowflakeGeneratorTest {

    @Test
    public void testGenerateUniqueIds() {
        SnowflakeGenerator generator = new SnowflakeFactory()
                .withEpoch(1609459200000L)
                .withWorkerId(1)
                .withProcessId(1)
                .build();

        Set<Long> ids = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            Long id = generator.generateId();
            assertFalse(ids.contains(id), "Duplicate ID generated");
            ids.add(id);
        }
    }

    @Test
    public void testMonotonicity() {
        SnowflakeGenerator generator = new SnowflakeFactory().build();
        long last = generator.generateId();
        for (int i = 0; i < 100000; i++) {
            long current = generator.generateId();
            assertTrue(current > last, "IDs not monotonically increasing");
            last = current;
        }
    }

    @Test
    public void testThreadedIdGeneration() throws InterruptedException {
        SnowflakeGenerator generator = new SnowflakeFactory().build();
        Set<Long> ids = new HashSet<>();
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    Long id = generator.generateId();
                    synchronized (ids) {
                        assertFalse(ids.contains(id), "Duplicate ID generated in thread");
                        ids.add(id);
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    public void testNtpCheckEnabled() {
        SnowflakeGenerator generator = new SnowflakeFactory()
                .withEpoch(1609459200000L)
                .withWorkerId(1)
                .withProcessId(1)
                .enableNtpCheck(true)
                .build();

        long id = generator.generateId();
        long currentTime = System.currentTimeMillis();
        long generatedTime = (id >> 22) + 1609459200000L;
        assertTrue(generatedTime <= currentTime, "Generated ID timestamp is in the future");
        assertTrue(generatedTime >= currentTime - 1000, "Generated ID timestamp is too far in the past");
    }
}

