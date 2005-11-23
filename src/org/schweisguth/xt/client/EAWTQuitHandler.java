package org.schweisguth.xt.client;

import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.util.contract.Assert;
import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;

public class EAWTQuitHandler implements QuitHandler {
    public void register(Client pClient) {
        Application application = new Application();
        application.addApplicationListener(new QuitListener(pClient));
    }

    private static class QuitListener extends ApplicationAdapter {
        private final Client mClient;

        public QuitListener(Client pClient) {
            Assert.assertNotNull(pClient);
            mClient = pClient;
        }

        public void handleQuit(ApplicationEvent pApplicationEvent) {
            QuitUtil.quit(mClient);
        }

    }

}
