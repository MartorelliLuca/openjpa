package org.apache.openjpa.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.concurrent.locks.Lock;

import static org.junit.Assert.*;

public class RemoveMutationKillTest {

    @Test
    public void testEntryRemovedInPinnedMap() {
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
                assertFalse(expired); // Assicura che expired sia falso
            }
        };

        Object key = "key";
        Object value = "value";

        // Aggiunge un'entry direttamente alla mappa pinnata
        cacheMap.pin(key);
        cacheMap.put(key, value);
        cacheMap.remove(key);

        // Assicura che entryRemoved sia chiamato e expired sia falso
        assertTrue(entryRemovedCalled[0]);
    }

    @Test
    public void testEntryRemovedNotInPinnedMap() {
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
                assertFalse(expired); // Assicura che expired sia falso
            }
        };

        Object key = "key";
        Object value = "value";

        // Aggiunge un'entry direttamente alla mappa pinnata
        cacheMap.put(key, value);
        cacheMap.remove(key);

        // Assicura che entryRemoved sia chiamato e expired sia falso
        assertTrue(entryRemovedCalled[0]);
    }

}
