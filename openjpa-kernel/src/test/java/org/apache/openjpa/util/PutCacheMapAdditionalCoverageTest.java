package org.apache.openjpa.util;


import org.junit.Assert;
import org.junit.Test;
public class PutCacheMapAdditionalCoverageTest {

    @Test
    public void testEntryRemovedAndEntryAdded() {
        Object existingValue = new Object();

        // Set up CacheMap with LRU eviction and maxSize > 0
        CacheMap cacheMap = new CacheMap(true, 2);
        Object key = new Object();
        Object value = new Object();

        // Add an existing entry to the cache map
        cacheMap.put(key, existingValue);

        // Add a new entry with the same key but different value
        Object retVal = cacheMap.put(key, value);

        // Verify the entry was removed and added again
        Assert.assertEquals(existingValue, retVal);
        Assert.assertEquals(value, cacheMap.get(key));
    }
}
