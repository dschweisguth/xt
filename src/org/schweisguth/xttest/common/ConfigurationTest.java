package org.schweisguth.xttest.common;

import org.schweisguth.xt.common.configuration.Configuration;
import junit.framework.TestCase;

public class ConfigurationTest extends TestCase {
    public void testGetString() {
        assertEquals("foo",
            Configuration.instance().get(getClass(), "testString", "bar"));
    }

    public void testGetEmptyString() {
        assertEquals("",
            Configuration.instance().get(getClass(), "testEmptyString", "baz"));
    }

    public void testGetStringDefault() {
        assertEquals("default",
            Configuration.instance().get(getClass(), "noSuchProperty", "default"));
    }

    public void testGetInt() {
        assertEquals(666,
            Configuration.instance().getInt(getClass(), "testInt", 0));
    }

    public void testGetIntDefault() {
        assertEquals(3,
            Configuration.instance().getInt(getClass(), "noSuchProperty", 3));
    }

}
