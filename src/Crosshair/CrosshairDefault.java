package Crosshair;

import java.awt.*;

/**
 * 12.02.2018 | created by Lukas S
 */

public abstract class CrosshairDefault implements Crosshair {
    protected CrColor color;
    protected double gap;

    @Override
    public void draw(Graphics2D g, int posX, int posY, boolean isMoving) {

        g.setColor(new Color(0, 0, 0, 100));
        g.fillOval(posX - 2, posY - 2, 6, 6);

        g.setColor(color.getSelectedColor());
        g.fillOval(posX, posY, 2, 2);

        int realgap = (int) (gap == 0 ? 0.5 : gap * 2.0) + 3;

        //Oben
        g.drawLine(posX, posY - realgap, posX, posY - realgap - 8);
        g.fillPolygon(new int[]{posX, posX - 2, posX + 2}, new int[]{posY - realgap - 2, posY - realgap - 8, posY - realgap - 8}, 3);

        //Unten
        g.drawLine(posX, posY + realgap, posX, posY + realgap + 8);
        g.fillPolygon(new int[]{posX, posX - 2, posX + 2}, new int[]{posY + realgap + 2, posY + realgap + 8, posY + realgap + 8}, 3);

        //Links
        g.drawLine(posX - realgap, posY, posX - realgap - 8, posY);
        g.fillPolygon(new int[]{posX - realgap - 2, posX - realgap - 8, posX - realgap - 8}, new int[]{posY, posY - 2, posY + 2}, 3);

        //Rechts
        g.drawLine(posX + realgap, posY, posX + realgap + 8, posY);
        g.fillPolygon(new int[]{posX + realgap + 2, posX + realgap + 8, posX + realgap + 8}, new int[]{posY, posY - 2, posY + 2}, 3);

    }

    @Override
    public Color getColor() {
        return color.getSelectedColor();
    }

    @Override
    public void setColor(CrColor.ColorMode mode, Color color) {
        if (mode == CrColor.ColorMode.CUSTOM) {
            System.err.println("Color Mode not allowed!");
        } else {
            this.color.setColorMode(mode);
        }
    }

    public double getGap() {
        return gap;
    }

    public void setGap(double gap) {
        this.gap = gap;
    }

}
