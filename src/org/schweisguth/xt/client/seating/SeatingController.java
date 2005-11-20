package org.schweisguth.xt.client.seating;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.schweisguth.xt.client.board.BoardView;
import org.schweisguth.xt.client.boardimpl.BoardController;
import org.schweisguth.xt.client.rackimpl.PlayerRackController;
import org.schweisguth.xt.client.rackimpl.RackController;
import org.schweisguth.xt.client.seat.SeatController;
import org.schweisguth.xt.client.seatimpl.SeatControllerImpl;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.util.MainFrameHolder;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Listener;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.contract.Assert;

public class SeatingController {
    // Fields
    private final SetList mSeatControllers = new ArraySetList();
    private final SeatingView mView;

    // Constructors

    public SeatingController(Client pClient) {
        Assert.assertNotNull(pClient);

        BoardController boardController = new BoardController(pClient);
        BoardView boardView = boardController.getBoardView();
        SetList seatViews = new ArraySetList();
        for (int i = 0; i < Game.MAX_PLAYERS; i++) {
            SeatControllerImpl seatController =
                new SeatControllerImpl(pClient, boardView);
            mSeatControllers.add(seatController);
            seatViews.add(seatController.getView());
        }
        boardController.setSeatController(getClientPlayerSeatController());
        mView = new SeatingView(boardController.getView(), seatViews);
        pClient.addListener(new SeatingListener(pClient));

    }

    private class SeatingListener implements Listener {
        private final Client mClient;

        private SeatingListener(Client pClient) {
            Assert.assertNotNull(pClient);
            mClient = pClient;
        }

        public void send(Event pEvent) {
            Game game = pEvent.getGame();
            if (game.getPlayers().size() != getPlayerCount()) {
                reseatPlayers(game);
            }
        }

        private int getPlayerCount() {
            return Game.MAX_PLAYERS - getNullModelCount();
        }

        private int getNullModelCount() {
            int nullModelCount = 0;
            for (Iterator seatControllers = mSeatControllers.iterator();
                seatControllers.hasNext();) {
                SeatControllerImpl seatController =
                    (SeatControllerImpl) seatControllers.next();
                if (seatController.getPlayer().equals("")) {
                    nullModelCount++;
                }
            }
            return nullModelCount;
        }

        private void reseatPlayers(Game pGame) {
            SetList players = pGame.getPlayers();
            String clientPlayer = mClient.getPlayer();
            for (int seat = 0; seat < mSeatControllers.size(); seat++) {
                SeatControllerImpl seatController =
                    (SeatControllerImpl) mSeatControllers.get(seat);
                int playerIndex = getPlayerIndex(seat, players, clientPlayer);
                String newPlayer =
                    playerIndex == -1 ? "" : (String) players.get(playerIndex);
                seatController.setPlayer(newPlayer, pGame);
            }
            MainFrameHolder.instance().getMainFrame().pack();
        }

        private int getPlayerIndex(int pSeat, SetList pPlayers,
            String pClientPlayer) {
            int playerCount = pPlayers.size();

            // Determine the index of the player using this controller's client
            // and fudge it to be an index into playerIndexesInSeatByPlayerCount
            int clientPlayerIndex = pPlayers.indexOf(pClientPlayer);
            clientPlayerIndex = clientPlayerIndex == -1 ? 0 : clientPlayerIndex;

            // Get the index from the following table, which is correct if the
            // client player is index 0, then adjust for the actual client player
            // index.
            final int[][] playerIndexesInSeatByPlayerCount =
                {
                    { -1, -1, -1, -1 },
                    { 0, -1, -1, -1 },
                    { 0, -1, 1, -1 },
                    { 0, 1, -1, 2 },
                    { 0, 1, 2, 3 },
                };
            int playerIndex =
                playerIndexesInSeatByPlayerCount[playerCount][pSeat];
            if (playerIndex != -1) {
                playerIndex = (clientPlayerIndex + playerIndex) % playerCount;
            }
            return playerIndex;

        }

    }

    // Methods: other

    public Component getView() {
        return mView;
    }

    public final SeatController getClientPlayerSeatController() {
        return (SeatController) mSeatControllers.get(0);
    }

    public List getPlayers() {
        List players = new ArrayList();
        for (Iterator seatControllers = mSeatControllers.iterator();
            seatControllers.hasNext();) {
            SeatControllerImpl seatController =
                (SeatControllerImpl) seatControllers.next();
            players.add(seatController.getPlayer());
        }
        return players;
    }

    public List getRacks() {
        List racks = new ArrayList();
        for (Iterator seatControllers = mSeatControllers.iterator();
            seatControllers.hasNext();) {
            SeatControllerImpl seatController =
                (SeatControllerImpl) seatControllers.next();
            RackController rackController = seatController.getRackController();
            racks.add(rackController instanceof PlayerRackController
                ? ((PlayerRackController) rackController).getRack()
                : null);
        }
        return racks;
    }

}
