package org.apache.openjpa.util;

import org.apache.openjpa.util.utils.InvalidObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Before;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)

public class PutCacheMapTest {
    private CacheMap cacheMap;
    private Object key;
    private Object value;
    private boolean expectedException;

    public PutCacheMapTest(Object key, Object value, boolean expectedException) {
        this.key = key;
        this.value = value;
        this.expectedException = expectedException;
    }

    @Before
    public void setUp() {
        cacheMap = new CacheMap();
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                //Key Type                     Value Type                    Exception
                { new Object(),                new Object(),                 false },             // Valid, Valid
                { new InvalidObject(),         new Object(),                 true },              // Invalid, Valid
                { null,                        new Object(),                 false },             // Null, Valid
                { new Object(),                new InvalidObject(),          true },              // Valid, Invalid
                { new InvalidObject(),         new InvalidObject(),          false },             // Invalid, Invalid
                { null,                        new InvalidObject(),          true },              // Null, Invalid
                { new Object(),                null,                         false },             // Valid, Null
                { new InvalidObject(),         null,                         true },              // Invalid, Null
                { null,                        null,                         false }              // Null, Null
        });
    }

    @Test
    public void testPut() {
        if (expectedException) {
            assertThrows(IllegalArgumentException.class, () -> {
                cacheMap.put(key, value);
            });
        } else {
            cacheMap.put(key, value);
            assertEquals(value, cacheMap.get(key));
        }
    }


}
