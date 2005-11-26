package org.schweisguth.xttest.client.server;

import org.schweisguth.xt.client.server.ServerUtil;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.server.Server;
import org.schweisguth.xt.server.ServerImpl;
import org.schweisguth.xttest.testutil.BaseTest;

public class ServerUtilTest extends BaseTest {
    public void testGetServer() throws Exception {
        ServerImpl.clear();
        ServerImpl.main(new String[] { });
        Server server = ServerUtil.getServer();
        assertEquals(new GameImpl(), server.getGame());
    }
}
