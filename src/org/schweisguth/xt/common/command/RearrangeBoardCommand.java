package org.schweisguth.xt.common.command;

import java.util.List;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.util.contract.Assert;

public class RearrangeBoardCommand extends Command {
    private static final long serialVersionUID = 2205137017179821735L;

    // Fields
    private final Position mSource;
    private final Position mDestination;

    // Constructors

    public RearrangeBoardCommand(Position pSource, Position pDestination) {
        super("");
        Assert.assertNotNull(pSource);
        Assert.assertNotNull(pDestination);
        mSource = pSource;
        mDestination = pDestination;
    }

    // Methods: overrides

    protected List getFields() {
        List fields = super.getFields();
        fields.add(mSource);
        fields.add(mDestination);
        return fields;
    }

    // Methods: other

    public Position getSource() {
        return mSource;
    }

    public Position getDestination() {
        return mDestination;
    }

}
