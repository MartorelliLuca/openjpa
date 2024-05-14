package org.apache.openjpa.persistence;

import org.junit.Assert;
import org.junit.Test;

public class ArgumentExceptionTest{
    @Test
    public void testIsFatal() {
        ArgumentException argumentException = new ArgumentException("THIS IS A DUMMY TEST", null, null, true);
        Assert.assertTrue(argumentException.isFatal());
    }
    @Test
    public void testIsFatal2() {
        ArgumentException argumentException = new ArgumentException("THIS IS A DUMMY TEST", null, null, false);
        Assert.assertFalse(argumentException.isFatal());
    }
}
