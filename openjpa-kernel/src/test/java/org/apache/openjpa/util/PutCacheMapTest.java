package org.apache.openjpa.util;

import org.junit.After;
import org.junit.Assert;
import org.apache.openjpa.util.utils.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class PutCacheMapTest {

    private CacheMap cacheMap;

    private Object key;

    private Object value;
    private final KeyType stateOfKey;
    private final ValueType stateOfValue;
    private final Object existingValue;
    private final boolean expectedNull;
    private final boolean keyPinned;




    private enum KeyType {
        Null,
        NotExistedObject,
        ExistedObject,
        InvalidObject
    }

    private enum ValueType {
        Null,
        ValidObject,
        InvalidObject
    }

    public PutCacheMapTest(EntriesTuple entriesTuple) {
        this.stateOfKey = entriesTuple.stateOfKey();
        this.keyPinned = entriesTuple.keyPinned();
        this.stateOfValue = entriesTuple.stateOfValue();
        this.existingValue = new Object();
        this.expectedNull = entriesTuple.isNull();
    }

    @Parameterized.Parameters
    public static Collection<EntriesTuple> getReadInputTuples() {
        List<EntriesTuple> putEntriesList = new ArrayList<>();

        putEntriesList.add(new EntriesTuple(KeyType.Null,             ValueType.ValidObject,  false,false)); //case 1
        putEntriesList.add(new EntriesTuple(KeyType.Null,             ValueType.Null,         true, false)); //case 2
        putEntriesList.add(new EntriesTuple(KeyType.Null,             ValueType.InvalidObject,false,false)); //case 3
        putEntriesList.add(new EntriesTuple(KeyType.NotExistedObject, ValueType.ValidObject,  false,false)); //case 4
        putEntriesList.add(new EntriesTuple(KeyType.NotExistedObject, ValueType.Null,         true, false)); //case 5
        putEntriesList.add(new EntriesTuple(KeyType.NotExistedObject, ValueType.InvalidObject,false,false)); //case 6
        putEntriesList.add(new EntriesTuple(KeyType.InvalidObject,    ValueType.ValidObject,  true, false)); //case 7
        putEntriesList.add(new EntriesTuple(KeyType.InvalidObject,    ValueType.Null,         true, false)); //case 8
        putEntriesList.add(new EntriesTuple(KeyType.InvalidObject,    ValueType.InvalidObject,true, false)); //case 9
        putEntriesList.add(new EntriesTuple(KeyType.ExistedObject,    ValueType.ValidObject,  false,false)); //case 10
        putEntriesList.add(new EntriesTuple(KeyType.ExistedObject,    ValueType.Null,         true, false)); //case 11
        putEntriesList.add(new EntriesTuple(KeyType.ExistedObject,    ValueType.InvalidObject,false,false)); //case 12

        //New tests added so to upgrade coverage (after BA-DUA and JaCoCo report)
        putEntriesList.add(new EntriesTuple(KeyType.ExistedObject,    ValueType.ValidObject,  false,true));  //case 13
        putEntriesList.add(new EntriesTuple(KeyType.ExistedObject,    ValueType.Null,         true, true));  //case 14
        putEntriesList.add(new EntriesTuple(KeyType.ExistedObject,    ValueType.InvalidObject,false,true));  //case 15
        putEntriesList.add(new EntriesTuple(KeyType.NotExistedObject, ValueType.ValidObject,  false,true));  //case 16
        putEntriesList.add(new EntriesTuple(KeyType.NotExistedObject, ValueType.Null,         true, true));  //case 17
        putEntriesList.add(new EntriesTuple(KeyType.NotExistedObject, ValueType.InvalidObject,false,true));  //case 18

        //PIT



        return putEntriesList;
    }

    private static final class EntriesTuple {
        private final KeyType keyType;
        private final boolean keyPinned;
        private final ValueType valueType;
        private final boolean nullValue;

        private EntriesTuple(KeyType stateOfKey, ValueType stateOfValue, boolean nullValue, boolean keyPin) {
            this.keyType = stateOfKey;
            this.keyPinned = keyPin;
            this.valueType = stateOfValue;
            this.nullValue = nullValue;
        }

        public KeyType stateOfKey() {
            return keyType;
        }

        public ValueType stateOfValue() {
            return valueType;
        }
        public boolean isNull() {
            return nullValue;
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
                if(this.keyPinned) {
                    this.cacheMap.pin(key);
                }
                break;
            case ExistedObject:
                this.key = new Object();
                cacheMap.put(this.key, this.existingValue);
                if(this.keyPinned) {
                    this.cacheMap.pin(key);
                }
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

        Object retVal = this.cacheMap.put(this.key, this.value);

        System.out.println("\n______________________________________________");
        System.out.println("KeyState: " + this.stateOfKey);
        System.out.println("ValueState: " + this.stateOfValue);
        System.out.println("Key: " + this.key);
        System.out.println("Return Value: " + retVal);
        System.out.println("Value:        " + this.value);
        System.out.println("Cached Value: " + this.cacheMap.get(this.key));

        if(this.expectedNull){
            System.out.println(this.cacheMap.get(this.key) == null);
            Assert.assertNull(this.cacheMap.get(this.key));
        } else {
            assertEquals(this.value, this.cacheMap.get(this.key));
        }

        // Verifica che l'elemento rimanga nella cache anche se è pinnata
        if (keyPinned && key != null) {
            Assert.assertTrue(cacheMap.getPinnedKeys().contains(key));
            assertEquals(this.value, cacheMap.get(this.key));
        }
    }

/*    @After
    public void cleanUpEachTime(){
        this.cacheMap.clear();
    }*/

}
