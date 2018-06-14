package Crosshair;

import java.awt.*;

/**
 * 09.02.2018 | created by Lukas S
 */

public class CrDefault extends CrosshairDefault {

    public CrDefault() {
        gap = 8;
        color = new CrColor();
    }

    public CrDefault(CrDefault crosshair) {
        gap = crosshair.gap;
        color = crosshair.color;
    }


    @Override
    public void draw(Graphics2D g, int posX, int posY, boolean isMoving) {

        double oldgap = gap;
        int innerOffset = 0;

        if (isMoving) {
            gap = Math.abs(gap) * 2;
            innerOffset = 10;
        }


        super.draw(g, posX, posY, isMoving);

        gap = oldgap;

        g.setColor(color.getSelectedColor());

        g.drawLine(posX + 3, posY - 10 - innerOffset, posX - 3, posY - 10 - innerOffset); //Oben
        g.drawLine(posX + 3, posY + 10 + innerOffset, posX - 3, posY + 10 + innerOffset); //Unten
        g.drawLine(posX - 10 - innerOffset, posY + 3, posX - 10 - innerOffset, posY - 3); //Links
        g.drawLine(posX + 10 + innerOffset, posY + 3, posX + 10 + innerOffset, posY - 3); //Rechts
    }

    @Override
    public int getStyleId() {
        return 0;
    }

    @Override
    public String getStyleName() {
        return "Default";
    }

    @Override
    public String getInfo() {
        return "- Crosshairgap changes based on\n  weapon, spray and movement\n-  Only color is changeable\n-  Only colorpresets";
    }

    @Override
    public String[] getCommands() {
        String[] result = new String[2];

        result[0] = "cl_crosshairstyle " + getStyleId();
        result[1] = "cl_crosshaircolor " + color.getSelectedIndex();

        return result;
    }

    @Override
    public void initWithCommands(String[] commands) {
        String[] temp;

        for (String cmd : commands) {
            cmd = cmd.replace("\"", " ");
            cmd = cmd.trim();
            String sss = cmd.split(" ")[0];
            temp = null;

            switch (sss) {
                default:
                    break;
                case "cl_crosshaircolor":
                    temp = cmd.split(" ");
                    int tempInt = Integer.valueOf(temp[temp.length - 1]);
                    if (tempInt > 4 || tempInt < 1) {
                        color.setSelectedIndex(1);
                    }
                    color.setSelectedIndex(tempInt);
                    break;

            }
        }
    }

    @Override
    public String toString() {
        return String.format("[Id: %s | Color: %s]", getStyleId(), color.getColorMode().name());
    }

    @Override
    public Crosshair copy() {
        return new CrDefault(this);
    }
}
