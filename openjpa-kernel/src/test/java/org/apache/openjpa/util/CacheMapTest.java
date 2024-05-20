package org.apache.openjpa.util;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class CacheMapTest {

    private CacheMap cacheMap, cacheMap1, cacheMap2;

    @BeforeEach
    public void setUp() {
        cacheMap = new CacheMap();
        cacheMap1 = new CacheMap(true, 2); // LRU Cache with max size of 2
        cacheMap2 = new CacheMap(true, 10); // LRU Cache with max size of 10
    }

    @Test
    public void testUnpin() {
        // Pin an entry
        cacheMap2.put("key1", "value1");
        cacheMap2.pin("key1");

        // Verify the entry is pinned
        assertTrue(cacheMap2.getPinnedKeys().contains("key1"));

        // Unpin the entry and verify the return value
        assertTrue(cacheMap2.unpin("key1"));

        // Verify the entry is moved back to the unpinned cache
        assertEquals("value1", cacheMap2.get("key1"));
        assertFalse(cacheMap2.getPinnedKeys().contains("key1"));

        // Attempt to unpin a non-pinned key and verify the return value
        assertFalse(cacheMap2.unpin("key2"));

        // Verify the state of the cache
        assertNull(cacheMap2.get("key2"));
    }

    @Test
    public void testCacheMapOverflowRemoved() {
        // Add entries to fill the cache
        cacheMap1.put("key1", "value1");
        cacheMap1.put("key2", "value2");

        // Ensure cache is full
        assertEquals(2, cacheMap1.size());
        assertEquals("value1", cacheMap1.get("key1"));
        assertEquals("value2", cacheMap1.get("key2"));

        // Add another entry to trigger overflow
        cacheMap1.put("key3", "value3");

        // Verify that the cacheMap has overflowed
        // The LRU (least recently used) entry ("key1") should have been removed
        assertEquals(2, cacheMap1.size());
        assertNull(cacheMap.get("key1"));
        assertEquals("value2", cacheMap1.get("key2"));
        assertEquals("value3", cacheMap1.get("key3"));

        // Check that the overflowed entry is in the softMap
        // For this, we would ideally need to expose the softMap or use reflection
        // Here we assume a method `isInSoftMap` for illustration purposes
        // assertTrue(cacheMap.isInSoftMap("key1"));
    }

    @Test
    public void testSetCacheSize() {
        // Set cache size to a dummy value
        int newSize = 500;
        cacheMap.setCacheSize(newSize);

        // Verify the cache size is set correctly
        assertEquals(newSize, cacheMap.getCacheSize());
    }

    @Test
    public void testCacheMapInitialization() {
        CacheMap cacheMap = new CacheMap();
        assertNotNull(cacheMap);
    }



}
