package org.apache.openjpa.util;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CacheMapTest {

    private CacheMap cacheMap;

    @BeforeEach
    public void setUp() {
        cacheMap = new CacheMap();
    }

    @Test
    public void SetCacheSizeTest() {
        // Set cache size to a dummy value
        int newSize = 500;
        cacheMap.setCacheSize(newSize);

        // Verify the cache size is set correctly
        assertEquals(newSize, cacheMap.getCacheSize());
    }

    @Test
    public void CacheMapInitializationTest() {
        CacheMap cacheMap = new CacheMap();
        assertNotNull(cacheMap);
    }



}
