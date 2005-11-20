package org.schweisguth.xttest.common.util.collection;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CollectionSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(ArrayListSetListTest.class);
            suite.addTestSuite(CollectionUtilTest.class);
            suite.addTestSuite(HashSetMapTest.class);
            suite.addTestSuite(HashStickyMapTest.class);
            suite.addTestSuite(HashStickySetTest.class);
            suite.addTestSuite(HashStickySetMapTest.class);
            return suite;
        }
        catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private CollectionSuite() {
    }

}
