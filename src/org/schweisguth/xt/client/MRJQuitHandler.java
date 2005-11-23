package org.schweisguth.xt.client;

import com.apple.mrj.MRJApplicationUtils;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.util.contract.Assert;

public class MRJQuitHandler implements QuitHandler {
    public void register(Client pClient) {
        MRJApplicationUtils.registerQuitHandler(new QuitHandlerImpl(pClient));
    }

    private static class QuitHandlerImpl
        implements com.apple.mrj.MRJQuitHandler {
        private final Client mClient;

        public QuitHandlerImpl(Client pClient) {
            Assert.assertNotNull(pClient);
            mClient = pClient;
        }

        public void handleQuit() throws IllegalStateException {
            QuitUtil.quit(mClient);
        }

    }

}
