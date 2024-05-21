package org.apache.openjpa.util;

import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;


public class CacheMapTest {

    private CacheMap cacheMap;

    @Test
    public void testUnpin() {
        CacheMap cacheMap2 = new CacheMap(true, 10); // LRU Cache with max size of 10
        cacheMap2.put("key1", "value1");
        cacheMap2.pin("key1");

        assertTrue(cacheMap2.getPinnedKeys().contains("key1"));
        assertTrue(cacheMap2.unpin("key1"));
        assertEquals("value1", cacheMap2.get("key1"));
        assertFalse(cacheMap2.getPinnedKeys().contains("key1"));
        assertFalse(cacheMap2.unpin("key2"));
        assertNull(cacheMap2.get("key2"));
    }

    @Test
    public void testCacheMapOverflowRemoved() {
        CacheMap cacheMap1 = new CacheMap(true, 2); // LRU Cache with max size of 2
        cacheMap1.put("key1", "value1");
        cacheMap1.put("key2", "value2");

        assertEquals(2, cacheMap1.size());
        assertEquals("value1", cacheMap1.get("key1"));
        assertEquals("value2", cacheMap1.get("key2"));

        cacheMap1.put("key3", "value3");
    }

    @Test
    public void testSetCacheSize() {
        cacheMap = new CacheMap();
        int newSize = 500;
        cacheMap.setCacheSize(newSize);

        assertEquals(newSize, cacheMap.getCacheSize());
    }

    @Test
    public void testCacheMapInitialization() {
        CacheMap cacheMap = new CacheMap();
        assertNotNull(cacheMap);
        assertEquals(1000, cacheMap.getCacheSize());
    }

    @Test
    public void testPinAndUnpin() {
        cacheMap = new CacheMap();
        cacheMap.put("key1", "value1");

        assertFalse(cacheMap.getPinnedKeys().contains("key1"));
        assertTrue(cacheMap.pin("key1"));
        assertTrue(cacheMap.getPinnedKeys().contains("key1"));
        assertTrue(cacheMap.unpin("key1"));
        assertFalse(cacheMap.getPinnedKeys().contains("key1"));
    }

    @Test
    public void testPutAndGet() {
        cacheMap = new CacheMap();
        cacheMap.put("key1", "value1");
        assertEquals("value1", cacheMap.get("key1"));

        cacheMap.put("key2", "value2");
        assertEquals("value2", cacheMap.get("key2"));
    }

    @Test
    public void testRemove() {

        cacheMap = new CacheMap();
        cacheMap.put("key1", "value1");
        assertEquals("value1", cacheMap.get("key1"));

        cacheMap.remove("key1");
        assertNull(cacheMap.get("key1"));
    }

    @Test
    public void testContainsKey() {
        cacheMap = new CacheMap();
        cacheMap.put("key1", "value1");
        assertTrue(cacheMap.containsKey("key1"));
        assertFalse(cacheMap.containsKey("key2"));
    }

    @Test
    public void testContainsValue() {
        cacheMap = new CacheMap();
        cacheMap.put("key1", "value1");
        assertTrue(cacheMap.containsValue("value1"));
        assertFalse(cacheMap.containsValue("value2"));
    }

    @Test
    public void testClear() {
        cacheMap = new CacheMap();
        cacheMap.put("key1", "value1");
        cacheMap.put("key2", "value2");
        assertEquals(2, cacheMap.size());

        cacheMap.clear();
        assertEquals(0, cacheMap.size());
        assertNull(cacheMap.get("key1"));
        assertNull(cacheMap.get("key2"));
    }

    @Test
    public void testKeySet() {
        cacheMap = new CacheMap();
        cacheMap.put("key1", "value1");
        cacheMap.put("key2", "value2");
        Set keySet = cacheMap.keySet();
        assertTrue(keySet.contains("key1"));
        assertTrue(keySet.contains("key2"));
    }

    @Test
    public void testValues() {
        cacheMap = new CacheMap();
        cacheMap.put("key1", "value1");
        cacheMap.put("key2", "value2");
        Collection values = cacheMap.values();
        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }

    @Test
    public void testEntrySet() {
        cacheMap = new CacheMap();
        cacheMap.put("key1", "value1");
        cacheMap.put("key2", "value2");
        Set<Map.Entry> entrySet = cacheMap.entrySet();
        assertEquals(2, entrySet.size());
    }

    @Test
    public void testIsEmpty() {
        cacheMap = new CacheMap();
        assertTrue(cacheMap.isEmpty());
        cacheMap.put("key1", "value1");
        assertFalse(cacheMap.isEmpty());
    }

    @Test
    public void testSize() {
        cacheMap = new CacheMap();
        assertEquals(0, cacheMap.size());
        cacheMap.put("key1", "value1");
        assertEquals(1, cacheMap.size());
    }

    @Test
    public void CacheMapInitializationTest() {
        CacheMap cacheMap = new CacheMap();
        assertNotNull(cacheMap);
    }
}
