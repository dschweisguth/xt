package org.schweisguth.xttest.client;

import java.util.ArrayList;
import java.util.List;
import org.schweisguth.xt.client.seating.SeatingController;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class SeatingControllerTest extends BaseTest {
    private static final int MAX_PLAYERS = Game.MAX_PLAYERS;

    public void testOtherPlayersJoinBeforeClientPlayerRefreshes2() {
        for (int beforeRefreshCount = 0; beforeRefreshCount <= MAX_PLAYERS;
            beforeRefreshCount++) {
            ListenableGame game =
                new GameImpl(new JoiningState(createPlayers(beforeRefreshCount)));

            LocalClient client = new LocalClient(game, "client player");
            SeatingController controller = new SeatingController(client);
            client.sendRefreshEvent();

            assertEquals(expectedPlayers(client.getPlayers(), "client player"),
                controller.getPlayers());

        }
    }

    public void testOtherPlayersJoinAfterClientPlayerRefreshes() {
        for (int beforeRefreshCount = 0; beforeRefreshCount <= MAX_PLAYERS - 1;
            beforeRefreshCount++) {
            for (int afterRefreshCount = 1;
                afterRefreshCount <= MAX_PLAYERS - beforeRefreshCount;
                afterRefreshCount++) {
                ListenableGame game = new GameImpl(
                    new JoiningState(createPlayers(beforeRefreshCount)));

                LocalClient client = new LocalClient(game, "client player");
                SeatingController controller = new SeatingController(client);
                client.sendRefreshEvent();

                for (int i = 0; i < afterRefreshCount; i++) {
                    String player = "player" + (beforeRefreshCount + i);
                    new LocalClient(game, player).execute(new JoinCommand());
                }

                assertEquals(expectedPlayers(client.getPlayers(), "client player"),
                    controller.getPlayers());

            }
        }
    }

    public void testOtherPlayersJoinedAfterClientPlayerJoins() {
        for (int beforeRefreshCount = 0; beforeRefreshCount <= MAX_PLAYERS - 1;
            beforeRefreshCount++) {
            for (int afterRefreshCount = 0;
                afterRefreshCount <= MAX_PLAYERS - (beforeRefreshCount + 1);
                afterRefreshCount++) {
                for (int afterJoinCount = 1;
                    afterJoinCount <= MAX_PLAYERS -
                        (beforeRefreshCount + afterRefreshCount + 1);
                    afterJoinCount++) {
                    ListenableGame game = new GameImpl(
                        new JoiningState(createPlayers(beforeRefreshCount)));

                    LocalClient client = new LocalClient(game, "client player");
                    SeatingController controller = new SeatingController(client);

                    for (int i = 0; i < afterRefreshCount; i++) {
                        String player = "player" + (beforeRefreshCount + i);
                        new LocalClient(game, player).execute(
                            new JoinCommand());
                    }

                    client.execute(new JoinCommand());

                    for (int i = 0; i < afterJoinCount; i++) {
                        String player = "player" +
                            (beforeRefreshCount + afterRefreshCount + 1 + i);
                        new LocalClient(game, player).execute(
                            new JoinCommand());
                    }

                    assertEquals(
                        expectedPlayers(client.getPlayers(), "client player"),
                        controller.getPlayers());

                }
            }
        }
    }

    public void testRefreshMovingState() {
        ListenableGame game =
            new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        LocalClient client = new LocalClient(game, "player1");
        SeatingController controller = new SeatingController(client);
        client.sendRefreshEvent();

        assertEquals(expectedPlayers(client.getPlayers(), "player1"),
            controller.getPlayers());
        List expectedRacks = new ArrayList();
        expectedRacks.add(new Rack(AAAAAAA));
        expectedRacks.add(null);
        expectedRacks.add(new Rack(EEEEEEE));
        expectedRacks.add(null);
        assertEquals(expectedRacks, controller.getRacks());

    }

    private static String[] createPlayers(int pPlayerCount) {
        String[] players = new String[pPlayerCount];
        for (int player = 0; player < pPlayerCount; player++) {
            players[player] = "player" + player;
        }
        return players;
    }

    private static List expectedPlayers(List pPlayers, String pObservingPlayer) {
        int[][] playersAtPositionByPlayerCount =
            {
                { -1, -1, -1, -1 },
                { 0, -1, -1, -1 },
                { 0, -1, 1, -1 },
                { 0, 1, -1, 2 },
                { 0, 1, 2, 3 },
            };
        int playerCount = pPlayers.size();
        int[] playersAtPosition = playersAtPositionByPlayerCount[playerCount];
        int clientPlayerIndex = pPlayers.indexOf(pObservingPlayer);
        clientPlayerIndex = clientPlayerIndex == -1 ? 0 : clientPlayerIndex;

        List expectedPlayers = new ArrayList();
        for (int modelIndex = 0; modelIndex < MAX_PLAYERS; modelIndex++) {
            int playerAtPosition = playersAtPosition[modelIndex];
            String playerName;
            if (playerAtPosition == -1) {
                playerName = "";
            } else {
                playerAtPosition =
                    (playerAtPosition + clientPlayerIndex) % playerCount;
                playerName = (String) pPlayers.get(playerAtPosition);
            }
            expectedPlayers.add(playerName);
        }
        return expectedPlayers;

    }

}
