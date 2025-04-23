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

        Set<String> ids = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            String id = generator.generateId();
            assertFalse(ids.contains(id), "Duplicate ID generated");
            ids.add(id);
        }
    }

    @Test
    public void testMonotonicity() {
        SnowflakeGenerator generator = new SnowflakeFactory().build();
        long last = Long.parseLong(generator.generateId());
        for (int i = 0; i < 100; i++) {
            long current = Long.parseLong(generator.generateId());
            assertTrue(current > last, "IDs not monotonically increasing");
            last = current;
        }
    }
}

