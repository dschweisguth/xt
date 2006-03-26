package org.schweisguth.xttest.perf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import org.schweisguth.xttest.perf.common.EmptyObject;

public class SizeMeasurement {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(buffer);
        stream.writeObject(new EmptyObject());
        //stream.writeObject(new GameImpl(
        //    new MovingState(new String[] { "player1", "player2" },
        //    new String[] { "AAAAAAA", "EEEEEEE" })
        //));
        stream.close();
        System.out.println(buffer.toByteArray().length);
    }
}
