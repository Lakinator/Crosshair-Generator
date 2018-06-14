package UI;

import Crosshair.Crosshair;

import javax.swing.*;

/**
 * 10.02.2018 | created by Lukas S
 */

public abstract class CrosshairPanel extends JPanel {
    public abstract String getName();
    public abstract Crosshair getCrosshair();
    public abstract void setCrosshair(Crosshair crosshair);
}
