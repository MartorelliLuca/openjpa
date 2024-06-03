package org.apache.openjpa.util;

import org.junit.Assert;
import org.apache.openjpa.util.utils.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class PutCacheMapTest {

    private CacheMap cacheMap;

    private Object key;

    private Object value;
    private final KeyType stateOfKey;
    private final ValueType stateOfValue;
    private final Object existingValue;

    private enum KeyType {
        Null,
        ValidObject,
        InvalidObject
    }

    private enum ValueType {
        Null,
        ValidObject,
        InvalidObject
    }

    public PutCacheMapTest(EntriesTuple entriesTuple) {
        this.stateOfKey = entriesTuple.stateOfKey();
        this.stateOfValue = entriesTuple.stateOfValue();
        this.existingValue = new Object();
    }

    @Parameterized.Parameters
    public static Collection<EntriesTuple> getReadInputTuples() {
        List<EntriesTuple> putEntriesList = new ArrayList<>();

        putEntriesList.add(new EntriesTuple(KeyType.Null,           ValueType.ValidObject));            //case 1
        putEntriesList.add(new EntriesTuple(KeyType.Null,           ValueType.Null));                   //case 2
        putEntriesList.add(new EntriesTuple(KeyType.Null,           ValueType.InvalidObject));          //case 3
        putEntriesList.add(new EntriesTuple(KeyType.ValidObject,    ValueType.ValidObject));            //case 4
        putEntriesList.add(new EntriesTuple(KeyType.ValidObject,    ValueType.Null));                   //case 5
        putEntriesList.add(new EntriesTuple(KeyType.ValidObject,    ValueType.InvalidObject));          //case 6
        putEntriesList.add(new EntriesTuple(KeyType.InvalidObject,  ValueType.ValidObject));            //case 7
        putEntriesList.add(new EntriesTuple(KeyType.InvalidObject,  ValueType.Null));                   //case 8
        putEntriesList.add(new EntriesTuple(KeyType.InvalidObject,  ValueType.InvalidObject));          //case 9

        return putEntriesList;
    }

    private static final class EntriesTuple {
        private final KeyType keyType;
        private final ValueType valueType;

        private EntriesTuple(KeyType stateOfKey, ValueType stateOfValue) {
            this.keyType = stateOfKey;
            this.valueType = stateOfValue;
        }

        public KeyType stateOfKey() {
            return keyType;
        }

        public ValueType stateOfValue() {
            return valueType;
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
            case ValidObject:
                this.key = new Object();
                cacheMap.put(this.key, this.existingValue);
                break;
        }
        switch (stateOfValue) {
            case Null:
                this.value = null;
                break;
            case InvalidObject:
                this.value = new InvalidObject();
                break;
            case ValidObject:
                this.value = new Object();
                break;
        }
    }

    @Test
    public void testPut() {
        System.out.println("Key: " + this.key);
        System.out.println("Value: " + this.value);

        Object retVal = this.cacheMap.put(this.key, this.value);

        System.out.println("Return Value: " + retVal);
        System.out.println("Cached Value: " + this.cacheMap.get(this.key));

        if (this.key == null) {
            Assert.assertNull(retVal);
            Assert.assertEquals(this.value, this.cacheMap.get(this.key));
        } else {
            Assert.assertEquals(this.value, this.cacheMap.get(this.key));
            if (this.stateOfKey == KeyType.ValidObject) {
                Assert.assertEquals(this.existingValue, retVal);
            }
        }
    }
}
