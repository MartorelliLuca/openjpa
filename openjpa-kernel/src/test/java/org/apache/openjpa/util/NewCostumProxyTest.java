package org.apache.openjpa.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.apache.openjpa.util.objects.*;

import java.sql.Timestamp;
import java.util.*;

import static org.hamcrest.CoreMatchers.instanceOf;


@RunWith(value = Parameterized.class)
public class NewCostumProxyTest {
    private final OrigState origState;
    private long timeInMillisForCalendar;
    private long timeForDate;
    private int nanosForTimeStamp;
    private Collection<String> collection;
    private SortedSet<String> sortedSet;
    private Map<Integer, String> map;
    private SortedMap<Integer, String> sortedMap;
    private Object orig;

    private final boolean autoOff;
    private ProxyManagerImpl proxyManagerImpl;
    private final ExpectedUpcome expected;
    private enum OrigState {
        NULL,
        VALID,
        INVALID,
        PROXY,
        COLLECTION,
        SORTED_SET,
        MAP,
        SORTED_MAP,
        DATE,
        TIMESTAMP,
        CALENDAR,
        FINAL
    }

    private enum ExpectedUpcome {
        SUCCESS,
        FAILURE
    }

    public NewCostumProxyTest(TupleEntries inputTuple) {
        this.origState = inputTuple.stateOfOrig();
        this.autoOff = inputTuple.autoOff();
        this.expected = inputTuple.expected();
    }

    @Parameterized.Parameters
    public static Collection<TupleEntries> getInputTuples() {
        List<TupleEntries> proxyEntriesList = new ArrayList<>();
        proxyEntriesList.add(new TupleEntries(OrigState.VALID,         true,  ExpectedUpcome.SUCCESS)); //case 1
        proxyEntriesList.add(new TupleEntries(OrigState.VALID,         false, ExpectedUpcome.SUCCESS)); //case 2
        proxyEntriesList.add(new TupleEntries(OrigState.INVALID,       false, ExpectedUpcome.FAILURE)); //case 3
        proxyEntriesList.add(new TupleEntries(OrigState.INVALID,       true,  ExpectedUpcome.FAILURE)); //case 4
        proxyEntriesList.add(new TupleEntries(OrigState.NULL,          false, ExpectedUpcome.FAILURE)); //case 5
        proxyEntriesList.add(new TupleEntries(OrigState.NULL,          true,  ExpectedUpcome.FAILURE)); //case 6

        //New tests after JaCoCo and BaDua
        proxyEntriesList.add(new TupleEntries(OrigState.PROXY,         true,  ExpectedUpcome.SUCCESS)); //case 7
        proxyEntriesList.add(new TupleEntries(OrigState.PROXY,         false, ExpectedUpcome.SUCCESS)); //case 8
        proxyEntriesList.add(new TupleEntries(OrigState.COLLECTION,    false, ExpectedUpcome.SUCCESS)); //case 9
        proxyEntriesList.add(new TupleEntries(OrigState.COLLECTION,    true,  ExpectedUpcome.SUCCESS)); //case 10
        proxyEntriesList.add(new TupleEntries(OrigState.SORTED_SET,    false, ExpectedUpcome.SUCCESS)); //case 11
        proxyEntriesList.add(new TupleEntries(OrigState.SORTED_SET,    true,  ExpectedUpcome.SUCCESS)); //case 12
        proxyEntriesList.add(new TupleEntries(OrigState.DATE,          true,  ExpectedUpcome.SUCCESS)); //case 13
        proxyEntriesList.add(new TupleEntries(OrigState.DATE,          false, ExpectedUpcome.SUCCESS)); //case 14
        proxyEntriesList.add(new TupleEntries(OrigState.MAP,           false, ExpectedUpcome.SUCCESS)); //case 15
        proxyEntriesList.add(new TupleEntries(OrigState.MAP,           true,  ExpectedUpcome.SUCCESS)); //case 16
        proxyEntriesList.add(new TupleEntries(OrigState.SORTED_MAP,    false, ExpectedUpcome.SUCCESS)); //case 17
        proxyEntriesList.add(new TupleEntries(OrigState.SORTED_MAP,    true,  ExpectedUpcome.SUCCESS)); //case 18
        proxyEntriesList.add(new TupleEntries(OrigState.TIMESTAMP,     true,  ExpectedUpcome.SUCCESS)); //case 19
        proxyEntriesList.add(new TupleEntries(OrigState.TIMESTAMP,     false, ExpectedUpcome.SUCCESS)); //case 20
        proxyEntriesList.add(new TupleEntries(OrigState.CALENDAR,      false, ExpectedUpcome.SUCCESS)); //case 21
        proxyEntriesList.add(new TupleEntries(OrigState.CALENDAR,      true,  ExpectedUpcome.SUCCESS)); //case 22
        proxyEntriesList.add(new TupleEntries(OrigState.FINAL,         false, ExpectedUpcome.FAILURE)); //case 23
        proxyEntriesList.add(new TupleEntries(OrigState.FINAL,         true,  ExpectedUpcome.FAILURE)); //case 24

        return proxyEntriesList;
    }


    private static final class TupleEntries {
        private final OrigState origState;
        private final boolean autoOff;
        private final ExpectedUpcome expected;

        private TupleEntries(OrigState origState,
                             boolean autoOff,
                             ExpectedUpcome expected) {
            this.origState = origState;
            this.autoOff = autoOff;
            this.expected = expected;
        }
        public OrigState stateOfOrig() {
            return origState;
        }
        public boolean autoOff() {
            return autoOff;
        }
        public ExpectedUpcome expected() {
            return expected;
        }
    }

    @Before
    public void setUpEachTime(){
        this.proxyManagerImpl = new ProxyManagerImpl();
        ProxyDog proxyDog;
        UnproxyCat unproxyCat;
        switch (this.origState){
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
                proxyDog = getDog();
                this.orig = this.proxyManagerImpl.newCustomProxy(proxyDog, true);
                break;
            case COLLECTION:
                this.collection = new ArrayList<>();
                collection.add("Lionel");
                collection.add("Andrés");
                collection.add("Messi");
                this.orig = collection;
                break;
            case SORTED_SET:
                this.sortedSet = new TreeSet<>();
                sortedSet.add("Cuccitini");
                sortedSet.add("da");
                sortedSet.add("Rosario");
                this.orig = sortedSet;
                break;
            case MAP:
                this.map = new HashMap<>();
                map.put(1,"Ciro");
                map.put(2,"Luis");
                map.put(3,"Mateo");
                this.orig = map;
                break;
            case SORTED_MAP:
                this.sortedMap = new TreeMap<>();
                sortedMap.put(4,"Gabarron");
                sortedMap.put(5,"Gil");
                sortedMap.put(6,"Patric");
                this.orig = sortedMap;
                break;
            case DATE:
                Date date = new Date();
                this.timeForDate = System.currentTimeMillis();
                date.setTime(this.timeForDate);
                this.orig = date;
                break;
            case TIMESTAMP:
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                this.nanosForTimeStamp = 21;
                timestamp.setNanos(this.nanosForTimeStamp);
                this.orig = timestamp;
                break;
            case CALENDAR:
                Calendar calendar = Calendar.getInstance();
                this.timeInMillisForCalendar = System.currentTimeMillis();
                calendar.setTimeInMillis(timeInMillisForCalendar);
                this.orig = calendar;
                break;
            case FINAL:
                this.orig = new FinalClass();
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
        if(random.nextInt()%2 == 0){
            unproxyCat = new UnproxyCat("Silvestro", 4);
        }else{
            unproxyCat = new UnproxyCat("Tom", 2);
        }
        unproxyCat.miao();
        return unproxyCat;
    }

    @Test
    public void testNewCustomProxy() {
        Object retVal = this.proxyManagerImpl.newCustomProxy(this.orig, this.autoOff);
        if(this.expected == ExpectedUpcome.SUCCESS){
            Assert.assertThat(retVal, instanceOf(Proxy.class));
            if (orig instanceof Map) {
                Map originalMap = (Map) orig;
                Map proxyMap = (Map) retVal;
                Assert.assertEquals(originalMap.size(), proxyMap.size());
                for (Object key : originalMap.keySet()) {
                    Assert.assertEquals(originalMap.get(key), proxyMap.get(key));
                }
            } else if (orig instanceof Date) {
                Date originalDate = (Date) orig;
                Date proxyDate = (Date) retVal;
                Assert.assertEquals(originalDate.getTime(), proxyDate.getTime());
            } else if (orig instanceof Timestamp) {
                Timestamp originalTimestamp = (Timestamp) orig;
                Timestamp proxyTimestamp = (Timestamp) retVal;
                Assert.assertEquals(originalTimestamp.getTime(), proxyTimestamp.getTime());
                Assert.assertEquals(originalTimestamp.getNanos(), proxyTimestamp.getNanos());
            } else if (orig instanceof Calendar) {
                Calendar originalCalendar = (Calendar) orig;
                Calendar proxyCalendar = (Calendar) retVal;
                Assert.assertEquals(originalCalendar.getTimeInMillis(), proxyCalendar.getTimeInMillis());
            }
        } else {
            Assert.assertNull(retVal);
        }
    }




}
