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
    public void testIsUser() {
        ArgumentException argumentException = new ArgumentException("THIS IS A DUMMY TEST", null, null, true);
        Assert.assertEquals(4, argumentException.getType());
    }
}
