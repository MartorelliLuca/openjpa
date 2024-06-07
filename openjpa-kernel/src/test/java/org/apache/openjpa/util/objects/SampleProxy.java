package org.apache.openjpa.util.objects;

import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.Proxy;

    /*This is a sample class for the Proxy case in CopyCostumProxyTest,
            not much, but an honest work*/

public class SampleProxy implements Proxy {
    private String value;

    public SampleProxy(String value) {
        this.value = value;
    }

    @Override
    public void setOwner(OpenJPAStateManager sm, int field) {}

    @Override
    public OpenJPAStateManager getOwner() {
        return null;
    }

    @Override
    public int getOwnerField() {
        return 0;
    }

    @Override
    public ChangeTracker getChangeTracker() {
        return null;
    }

    @Override
    public Object copy(Object orig) {
        if (orig instanceof SampleProxy) {
            SampleProxy original = (SampleProxy) orig;
            return new SampleProxy(original.value);
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}
