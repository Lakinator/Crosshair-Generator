package Crosshair;

import java.awt.*;

/**
 * 09.02.2018 | created by Lukas S
 */

public class CrClassicDynamic extends CrosshairClassic {

    public CrClassicDynamic() {
        color = new CrColor();
        color.setColorMode(CrColor.ColorMode.CUSTOM);
        color.setCustomColor(Color.BLUE);
        size = 2;
        thickness = 0.5;
        gap = -1;
        dot = false;
        tStyle = false;
        outline = true;
        outlinethickness = 1;
    }

    public CrClassicDynamic(CrClassicDynamic crosshair) {
        color = crosshair.color;
        size = crosshair.size;
        thickness = crosshair.thickness;
        gap = crosshair.gap;
        dot = crosshair.dot;
        tStyle = crosshair.tStyle;
        outline = crosshair.outline;
        outlinethickness = crosshair.outlinethickness;
    }


    @Override
    public void draw(Graphics2D g, int posX, int posY, boolean isMoving) {
        double oldgap = gap;

        if (isMoving) {
            gap = Math.abs(gap) + 20;
        }

        super.draw(g, posX, posY, isMoving);
        gap = oldgap;
    }

    @Override
    public int getStyleId() {
        return 3;
    }

    @Override
    public String getStyleName() {
        return "Dynamic";
    }

    @Override
    public String getInfo() {
        return "-Fully customizable\n-Crosshairgap changes\n based on weapon,\n spray and movement\n-Custum color";
    }

    @Override
    public String[] getCommands() {
        String[] result = new String[14];

        result[0] = "cl_crosshairstyle " + getStyleId();
        result[1] = "cl_crosshairsize " + size;
        result[2] = "cl_crosshairthickness " + thickness;
        result[3] = "cl_crosshairgap " + gap;
        result[4] = "cl_crosshaircolor " + color.getSelectedIndex();
        result[5] = "cl_crosshaircolor_r " + color.getSelectedColor().getRed();
        result[6] = "cl_crosshaircolor_g " + color.getSelectedColor().getGreen();
        result[7] = "cl_crosshaircolor_b " + color.getSelectedColor().getBlue();
        result[8] = "cl_crosshairusealpha 1";
        result[9] = "cl_crosshairalpha " + color.getSelectedColor().getAlpha();
        result[10] = "cl_crosshair_drawoutline " + (outline ? "1" : "0");
        result[11] = "cl_crosshair_outlinethickness " + outlinethickness;
        result[12] = "cl_crosshairdot " + (dot ? "1" : "0");
        result[13] = "cl_crosshair_t " + (tStyle ? "1" : "0");

        return result;
    }

    @Override
    public String toString() {
        return String.format("[Id: %s |Size:%s |Thickness:%s |Gap:%s |Color:%s |Alpha:%s |Dot:%s |T_Style:%s |Outline:%s |Outlinethickness:%s]",
                getStyleId(),
                size,
                thickness,
                gap,
                color.getSelectedIndex() == 5 ?
                        String.format("R=%s,G=%s,B=%s", color.getSelectedColor().getRed(), color.getSelectedColor().getGreen(), color.getSelectedColor().getBlue())
                        :
                        color.getColorMode().name(), color.getSelectedColor().getAlpha(),
                dot,
                tStyle,
                outline,
                outlinethickness
        );
    }

    @Override
    public Crosshair copy() {
        return new CrClassicDynamic(this);
    }
}
