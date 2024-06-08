package org.apache.openjpa.util;

import static org.junit.Assert.*;
import org.junit.*;

public class PutMutationKillTest {
    @Test
    public void testEntryAddedAndRemoved() {
        CacheMap cacheMap = new CacheMap();

        final boolean[] entryAddedCalled = {false};
        final boolean[] entryRemovedCalled = {false};

        cacheMap = new CacheMap() {
            @Override
            protected void entryAdded(Object key, Object value) {
                entryAddedCalled[0] = true;
            }

            @Override
            protected void entryRemoved(Object key, Object value, boolean expired) {
                entryRemovedCalled[0] = true;
            }
        };

        Object key = "key";
        Object value = "value";

        cacheMap.put(key, value);

        assertTrue(entryAddedCalled[0]);
        assertFalse(entryRemovedCalled[0]);

        cacheMap.put(key, value);
        
        assertTrue(entryAddedCalled[0]);
        assertTrue(entryRemovedCalled[0]);
    }

    @Test
    public void testEntryAddedAndRemovedInPinnedMap() {
        CacheMap cacheMap = new CacheMap();

        final boolean[] entryAddedCalled = {false};
        final boolean[] entryRemovedCalled = {false};

        cacheMap = new CacheMap() {
            @Override
            protected void entryAdded(Object key, Object value) {
                entryAddedCalled[0] = true;
            }

            @Override
            protected void entryRemoved(Object key, Object value, boolean expired) {
                entryRemovedCalled[0] = true;
            }
        };

        Object key = "key";
        Object value = "value";

        // Add an entry directly to the pinned map
        cacheMap.pin(key);
        cacheMap.put(key, value);

        assertTrue(entryAddedCalled[0]);
        assertFalse(entryRemovedCalled[0]);

        // Adding a new value with the same key should cause entryRemoved to be called
        cacheMap.put(key, value);

        assertTrue(entryAddedCalled[0]);
        assertTrue(entryRemovedCalled[0]);

    }
}

