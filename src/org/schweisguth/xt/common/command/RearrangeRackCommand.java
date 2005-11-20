package org.schweisguth.xt.common.command;

import java.util.List;

public class RearrangeRackCommand extends Command {
    private static final long serialVersionUID = -1941523662906839141L;

    // Fields
    private final int mSource;
    private final int mDestination;

    // Constructors

    public RearrangeRackCommand(int pSource, int pDestination) {
        super("");
        mSource = pSource;
        mDestination = pDestination;
    }

    // Methods: overrides

    protected List getFields() {
        List fields = super.getFields();
        fields.add(new Integer(mSource));
        fields.add(new Integer(mDestination));
        return fields;
    }

    // Methods: other

    public int getSource() {
        return mSource;
    }

    public int getDestination() {
        return mDestination;
    }

}
