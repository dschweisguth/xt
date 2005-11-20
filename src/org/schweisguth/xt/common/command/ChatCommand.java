package org.schweisguth.xt.common.command;

import java.util.List;
import org.schweisguth.xt.common.util.contract.Assert;

public class ChatCommand extends Command {
    private static final long serialVersionUID = -5002385019965857474L;

    private final String mText;

    public ChatCommand(String pText) {
        super("");
        Assert.assertNotNull(pText);
        mText = pText;
    }

    protected List getFields() {
        return append(super.getFields(), mText);
    }

    public String getText() {
        return mText;
    }

}
