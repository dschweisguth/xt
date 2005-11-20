package org.schweisguth.xt.common.command;

import java.io.Serializable;
import org.schweisguth.xt.common.util.contract.Assert;
import org.schweisguth.xt.common.util.object.ValueObject;

public abstract class Command extends ValueObject implements Serializable {
    private static final long serialVersionUID = 3039861198699932220L;

    // Fields
    private final String mName;

    // Constructors

    protected Command(String pName) {
        Assert.assertNotNull(pName);
        mName = pName;
    }

    public String getName() {
        return mName;
    }

}
