package Crosshair;

import java.awt.*;

/**
 * 09.02.2018 | created by Lukas S
 */

public interface Crosshair {
    void draw(Graphics2D g, int posX, int posY, boolean isMoving); //Mittelpunkt
    int getStyleId();
    String getStyleName();
    String getInfo();
    String[] getCommands();
    void initWithCommands(String[] commands);
    Color getColor();
    void setColor(CrColor.ColorMode mode, Color color);
    String toString();
    Crosshair copy();
}
