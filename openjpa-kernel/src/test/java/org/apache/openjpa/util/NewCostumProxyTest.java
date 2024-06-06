/*
package org.apache.openjpa.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.apache.openjpa.util.objects.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.instanceOf;


@RunWith(value = Parameterized.class)
public class NewCostumProxyTest {
    private final OrigState origState;
    private Object orig;

    private final boolean autoOff;
    private ProxyManagerImpl proxyManagerImpl;
    private Expected expected;
    private int nanosForTimeStamp;
    private enum OrigState {
        NULL,
        VALID,
        INVALID
    }

    private enum Expected {
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
        proxyEntriesList.add(new TupleEntries(OrigState.VALID,   true,  Expected.SUCCESS)); //case 1
        proxyEntriesList.add(new TupleEntries(OrigState.VALID,   false, Expected.SUCCESS)); //case 2
        proxyEntriesList.add(new TupleEntries(OrigState.INVALID, false, Expected.FAILURE)); //case 3
        proxyEntriesList.add(new TupleEntries(OrigState.INVALID, true,  Expected.FAILURE)); //case 4
        proxyEntriesList.add(new TupleEntries(OrigState.NULL,    false, Expected.FAILURE)); //case 5
        proxyEntriesList.add(new TupleEntries(OrigState.NULL,    true,  Expected.FAILURE)); //case 6
       
        return proxyEntriesList;
    }


    private static final class TupleEntries {
        private final OrigState origState;
        private final boolean autoOff;
        private final Expected expected;

        private TupleEntries(OrigState origState,
                             boolean autoOff,
                             Expected expected) {
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
        public Expected expected() {
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
        if(this.expected == Expected.SUCCESS){
            Assert.assertThat(retVal, instanceOf(Proxy.class));
        }else{
            Assert.assertNull(retVal);
        }

    }
    
    
}
*/
