package org.schweisguth.xt.client.util;

import java.awt.Window;
import javax.swing.JFrame;
import org.schweisguth.xt.common.util.contract.Assert;

public class MainFrameHolder {
    // Fields: static
    private static final MainFrameHolder INSTANCE = new MainFrameHolder();

    public static MainFrameHolder instance() {
        return INSTANCE;
    }

    // Fields: instance
    private Window mMainFrame = new JFrame();

    public Window getMainFrame() {
        return mMainFrame;
    }

    public void setMainFrame(Window pMainFrame) {
        Assert.assertNotNull(pMainFrame);
        mMainFrame = pMainFrame;
    }

}
