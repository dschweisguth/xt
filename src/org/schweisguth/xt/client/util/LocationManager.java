package org.schweisguth.xt.client.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import org.schweisguth.xt.client.prefs.Preferences;

public class LocationManager extends ComponentAdapter {
    // Constants
    private static final String X_LOC = "xLoc";
    private static final String Y_LOC = "yLoc";

    // Methods: static

    public static void manage(Component pComponent) {
        Preferences prefs =
            Preferences.userNodeForPackage(pComponent.getClass());
        Point upperLeft = getPositionToCenter(pComponent);
        pComponent.setLocation(
            prefs.getInt(X_LOC, upperLeft.x),
            prefs.getInt(Y_LOC, upperLeft.y));
        pComponent.addComponentListener(new LocationManager(pComponent));
    }

    private static Point getPositionToCenter(Component pComponent) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = pComponent.getSize();
        return new Point(Math.max(screenSize.width - frameSize.width, 0) / 2,
            Math.max(screenSize.height - frameSize.height, 0) / 2);
    }

    // Fields
    private final Component mComponent;

    // Constructors

    private LocationManager(Component pComponent) {
        mComponent = pComponent;
    }

    // Methods: overrides

    public void componentMoved(ComponentEvent e) {
        super.componentMoved(e);
        if (e.getSource() == mComponent) {
            Preferences prefs =
                Preferences.userNodeForPackage(mComponent.getClass());
            prefs.putInt(X_LOC, mComponent.getX());
            prefs.putInt(Y_LOC, mComponent.getY());
        }
    }

}
