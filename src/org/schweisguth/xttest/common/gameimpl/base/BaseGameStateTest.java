package org.schweisguth.xttest.common.gameimpl.base;

import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xt.common.util.contract.AssertionFailedError;
import org.schweisguth.xttest.testutil.BaseTest;

public abstract class BaseGameStateTest extends BaseTest {
    protected static void assertWillFail(final StateImpl pState,
        String pPlayer, final Command pCommand) {
        try {
            new LocalClient(new GameImpl(pState), pPlayer).execute(pCommand);
        } catch (AssertionFailedError e) {
            return;
        }
        fail();
    }
}
