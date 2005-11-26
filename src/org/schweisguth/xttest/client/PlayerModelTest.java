package org.schweisguth.xttest.client;

import junit.framework.TestCase;
import org.schweisguth.xt.client.player.PlayerModel;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class PlayerModelTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new PlayerModel("player2"));
        tester.addOther(new PlayerModel(""));
        tester.setExpectedString("PlayerModel(player1, false)");
        tester.setShouldBeSerializable(false);
        tester.doAssert(new PlayerModel("player1"));
    }
}
