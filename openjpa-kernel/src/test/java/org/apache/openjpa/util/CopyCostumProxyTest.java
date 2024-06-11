package org.apache.openjpa.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.apache.openjpa.util.objects.*;

import java.util.*;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(value = Parameterized.class)
public class CopyCostumProxyTest {
    private final OrigState origState;

    private Object orig;
    private ProxyManagerImpl proxyManagerImpl;
    private long timeInMillisForCalendar;
    private long timeForDate;
    private Collection<String> collection;
    private Map<Integer, String> map;
    private Class<Calendar> calendarClass;

    private final ExpectedUpcome expected;

    private enum OrigState {
        NULL,
        VALID,
        INVALID,
        PROXY,
        COLLECTION,
        MAP,
        DATE,
        CALENDAR
    }

    private enum ExpectedUpcome {
        SUCCESS,
        FAILURE
    }

    public CopyCostumProxyTest(TupleEntries inputTuple) {
        this.origState = inputTuple.stateOfOrig();
        this.expected = inputTuple.expected();
    }

    @Parameterized.Parameters
    public static Collection<TupleEntries> getInputTuples() {
        List<TupleEntries> proxyEntriesList = new ArrayList<>();
        proxyEntriesList.add(new TupleEntries(OrigState.VALID, ExpectedUpcome.SUCCESS)); //case 1
        proxyEntriesList.add(new TupleEntries(OrigState.INVALID, ExpectedUpcome.FAILURE)); //case 2
        proxyEntriesList.add(new TupleEntries(OrigState.NULL, ExpectedUpcome.FAILURE)); //case 3

        //added after JaCoCo and BaDua
        proxyEntriesList.add(new TupleEntries(OrigState.PROXY, ExpectedUpcome.SUCCESS)); //case 4
        proxyEntriesList.add(new TupleEntries(OrigState.COLLECTION, ExpectedUpcome.SUCCESS)); //case 5
        proxyEntriesList.add(new TupleEntries(OrigState.MAP, ExpectedUpcome.SUCCESS)); //case 6
        proxyEntriesList.add(new TupleEntries(OrigState.DATE, ExpectedUpcome.SUCCESS)); //case 7
        proxyEntriesList.add(new TupleEntries(OrigState.CALENDAR, ExpectedUpcome.SUCCESS)); //case 8

        return proxyEntriesList;
    }

    private static final class TupleEntries {
        private final OrigState origState;
        private final ExpectedUpcome expected;

        private TupleEntries(OrigState origState, ExpectedUpcome expected) {
            this.origState = origState;
            this.expected = expected;
        }

        public OrigState stateOfOrig() {
            return origState;
        }

        public ExpectedUpcome expected() {
            return expected;
        }
    }

    @Before
    public void setUpEachTime() {
        this.proxyManagerImpl = new ProxyManagerImpl();
        ProxyDog proxyDog;
        UnproxyCat unproxyCat;
        switch (this.origState) {
            case NULL:
                this.orig = null;
                break;
            case VALID:
                proxyDog = getDog();
                this.orig = proxyDog;
                break;
            case INVALID:
                unproxyCat = getCat();
                this.orig = unproxyCat;
                break;
            case PROXY:
                this.orig = new SampleProxy("sample");
                break;
            case COLLECTION:
                this.collection = new ArrayList<>();
                collection.add("Lazio");
                this.orig = collection;
                break;
            case MAP:
                this.map = new HashMap<>();
                map.put(3, "River");
                this.orig = map;
                break;
            case DATE:
                Date date = new Date();
                this.timeForDate = System.currentTimeMillis();
                date.setTime(this.timeForDate);
                this.orig = date;
                break;
            case CALENDAR:
                Calendar calendar = Calendar.getInstance();
                this.timeInMillisForCalendar = System.currentTimeMillis();
                calendar.setTimeInMillis(timeInMillisForCalendar);
                this.orig = calendar;
                break;
        }
    }

    private static ProxyDog getDog() {
        ProxyDog proxyDog;
        proxyDog = new ProxyDog();
        proxyDog.bau();
        return proxyDog;
    }

    private static UnproxyCat getCat() {
        UnproxyCat unproxyCat;
        Random random = new Random(System.currentTimeMillis());
        if (random.nextInt() % 2 == 0) {
            unproxyCat = new UnproxyCat("Silvestro", 4);
        } else {
            unproxyCat = new UnproxyCat("Tom", 2);
        }
        unproxyCat.miao();
        return unproxyCat;
    }

    @Test
    public void testNewCustomProxy() {
        Object retVal = this.proxyManagerImpl.copyCustom(this.orig);

        if (this.expected == ExpectedUpcome.SUCCESS) {
            Assert.assertNotNull("Expected non-null result for success case", retVal);

            switch (this.origState) {
                case VALID:
                    Assert.assertThat(retVal, instanceOf(ProxyDog.class));
                    ProxyDog origDog = (ProxyDog) this.orig;
                    ProxyDog copiedDog = (ProxyDog) retVal;
                    Assert.assertEquals(origDog.getName(), copiedDog.getName());
                    Assert.assertEquals(origDog.getAge(), copiedDog.getAge());
                    break;
                case COLLECTION:
                    Assert.assertThat(retVal, instanceOf(Collection.class));
                    Collection<?> origCollection = (Collection<?>) this.orig;
                    Collection<?> copiedCollection = (Collection<?>) retVal;
                    Assert.assertEquals(origCollection.size(), copiedCollection.size());
                    Assert.assertTrue(copiedCollection.containsAll(origCollection));
                    break;
                case MAP:
                    Assert.assertThat(retVal, instanceOf(Map.class));
                    Map<?, ?> origMap = (Map<?, ?>) this.orig;
                    Map<?, ?> copiedMap = (Map<?, ?>) retVal;
                    Assert.assertEquals(origMap.size(), copiedMap.size());
                    Assert.assertTrue(copiedMap.entrySet().containsAll(origMap.entrySet()));
                    break;
                case DATE:
                    Assert.assertThat(retVal, instanceOf(Date.class));
                    Date origDate = (Date) this.orig;
                    Date copiedDate = (Date) retVal;
                    Assert.assertEquals(origDate.getTime(), copiedDate.getTime());
                    break;
                case CALENDAR:
                    Assert.assertThat(retVal, instanceOf(Calendar.class));
                    Calendar origCalendar = (Calendar) this.orig;
                    Calendar copiedCalendar = (Calendar) retVal;
                    Assert.assertEquals(origCalendar.getTimeInMillis(), copiedCalendar.getTimeInMillis());
                    break;
            }
        } else {
            Assert.assertNull("Expected null result for failure case", retVal);
        }
    }
}
