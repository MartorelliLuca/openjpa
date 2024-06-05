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

    /*@Test
    public void testPinAndUnpin() {
        CacheMap cacheMap = new CacheMap();

        Object key = "key";
        Object value = "value";

        cacheMap.put(key, value);
        assertTrue(cacheMap.getPinnedKeys().isEmpty()); // Assert that no keys are pinned

        // Test pinning key
        cacheMap.pin(key);
        assertTrue(cacheMap.getPinnedKeys().contains(key)); // Assert that key is pinned
        assertEquals(value, cacheMap.get(key)); // Assert that value is still accessible

        // Test unpinning key
        cacheMap.unpin(key);
        assertTrue(cacheMap.getPinnedKeys().isEmpty()); // Assert that key is unpinned
        assertEquals(value, cacheMap.get(key)); // Assert that value is still accessible
    }

    @Test
    public void testPutWhenKeyExists() {
        //un cazzo
        CacheMap cacheMap = new CacheMap();

        // Add an existing key-value pair
        Object key = "existingKey";
        Object existingValue = "existingValue";
        Object newValue = "newValue";

        cacheMap.put(key, existingValue);

        // Attempt to put a new value with the same key
        Object result = cacheMap.put(key, newValue);

        // Assert that the previous value is returned
        assertEquals(existingValue, result);
        // Assert that the new value is added to cacheMap
        assertEquals(newValue, cacheMap.get(key));
    }

    @Test
    public void testPutWhenKeyExistsAndIsPinned() {
        //migliora altro
        CacheMap cacheMap = new CacheMap();

        // Aggiungi una chiave e un valore esistenti
        Object key = "existingPinnedKey";
        Object existingValue = "existingValue";
        Object newValue = "newValue";

        // Aggiungi una chiave esistente e il relativo valore alla mappa
        cacheMap.put(key, existingValue);

        // Pinna la chiave
        cacheMap.pin(key);

        // Tentativo di inserire un nuovo valore con una chiave esistente
        Object result = cacheMap.put(key, newValue);

        // Verifica che il valore precedente venga restituito
        assertEquals(existingValue, result);
        // Verifica che il nuovo valore sia stato aggiunto alla cacheMap
        assertEquals(newValue, cacheMap.get(key));
    }*/
}
