package org.schweisguth.xt.client.rackimpl;

import java.awt.Component;

public class NoPlayerRackController implements RackController {
    // Fields
    private final NoPlayerRackView mView;

    // Constructors

    public NoPlayerRackController() {
        mView = new NoPlayerRackView();
    }

    // Methods: implements RackController

    public Component getView() {
        return mView;
    }

    public void dispose() {
    }

}
