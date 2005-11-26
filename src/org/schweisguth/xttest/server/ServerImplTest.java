package org.schweisguth.xttest.server;

import junit.framework.TestCase;
import org.schweisguth.xt.client.server.ServerUtil;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.server.Server;
import org.schweisguth.xt.server.ServerImpl;

public class ServerImplTest extends TestCase {
    public void testMainNew() throws Exception {
        ServerImpl.clear();
        ServerImpl.main(new String[] {});
        Server server = ServerUtil.getServer();

        assertEquals(new GameImpl(), server.getGame());
    }


    public void testMainRestored() throws Exception {
        ServerImpl.clear();
        ServerImpl.main(new String[] {});
        Server server1 = ServerUtil.getServer();
        server1.execute(new Request("player1", new JoinCommand()));
        ServerImpl.main(new String[] {});
        Server server2 = ServerUtil.getServer();

        Game expectedGame =
            new GameImpl(new JoiningState(new String[] { "player1" }));
        assertEquals(expectedGame, server2.getGame());
    }


}
