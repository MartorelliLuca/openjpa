/*
package org.apache.openjpa.util;

import org.apache.openjpa.util.objects.InvalidObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class RemoveCacheMapTest {

    private CacheMap cacheMap;
    private Object key;
    private final KeyType stateOfKey;
    private final boolean keyPinned;
    private final Object existingValue = new Object();

    private enum KeyType {
        Null,
        NotExistedObject,
        ExistedObject,
        InvalidObject
    }

    public RemoveCacheMapTest(EntriesTuple entriesTuple) {
        this.stateOfKey = entriesTuple.stateOfKey();
        this.keyPinned = entriesTuple.keyPinned();
    }

    @Parameterized.Parameters
    public static Collection<EntriesTuple> getReadInputTuples() {
        List<EntriesTuple> putEntriesList = new ArrayList<>();
        putEntriesList.add(new EntriesTuple(KeyType.Null,             false)); // case 1
        putEntriesList.add(new EntriesTuple(KeyType.NotExistedObject, false)); // case 2
        putEntriesList.add(new EntriesTuple(KeyType.InvalidObject,    false)); // case 3
        putEntriesList.add(new EntriesTuple(KeyType.ExistedObject,    false)); // case 4

        //After Jacoco and BaDua reports
        putEntriesList.add(new EntriesTuple(KeyType.Null,             true));  // case 5
        putEntriesList.add(new EntriesTuple(KeyType.NotExistedObject, true)); // case 6
        putEntriesList.add(new EntriesTuple(KeyType.InvalidObject,    true)); // case 7
        putEntriesList.add(new EntriesTuple(KeyType.ExistedObject,    true)); // case 8
        return putEntriesList;
    }

    private static final class EntriesTuple {
        private final KeyType keyType;
        private final boolean keyPinned;

        private EntriesTuple(KeyType keyType, boolean keyPinned) {
            this.keyType = keyType;
            this.keyPinned = keyPinned;
        }

        public KeyType stateOfKey() {
            return keyType;
        }

        public boolean keyPinned() {
            return keyPinned;
        }
    }

    @Before
    public void setUpEachTime() {
        this.cacheMap = new CacheMap();
        switch (stateOfKey) {
            case Null:
                this.key = null;
                break;
            case InvalidObject:
                this.key = new InvalidObject();
                break;
            case NotExistedObject:
                this.key = new Object();
                if (this.keyPinned) {
                    this.cacheMap.pin(key);
                }
                break;
            case ExistedObject:
                this.key = new Object();
                cacheMap.put(this.key, this.existingValue);
                if (this.keyPinned) {
                    this.cacheMap.pin(key);
                }
                break;
        }
    }

    @Test
    public void testRemove() {
        Object deletedVal = this.cacheMap.remove(this.key);

        System.out.println("\n______________________________________________");
        System.out.println("KeyState: " + this.stateOfKey);
        System.out.println("Key: " + this.key);
        System.out.println("Return Value: " + deletedVal);
        System.out.println("Cached Value: " + this.cacheMap.get(this.key));

        if (this.key == null || this.key instanceof InvalidObject) {
            // Rimuovere una chiave null o un oggetto non valido non dovrebbe cambiare la mappa.
            assertNull(deletedVal);
            assertNull(this.cacheMap.get(this.key));
        } else if (this.stateOfKey == KeyType.ExistedObject) {
            // Se la chiave esisteva nella mappa, la rimozione dovrebbe restituire il valore associato
            assertEquals(this.existingValue, deletedVal);
            assertNull(this.cacheMap.get(this.key));
        } else {
            // Se la chiave non esisteva nella mappa, la rimozione dovrebbe restituire null
            assertNull(deletedVal);
            assertNull(this.cacheMap.get(this.key));
        }
    }
}
*/
