package Crosshair;

import java.awt.*;

/**
 * 09.02.2018 | created by Lukas S
 */

public class CrClassic extends CrosshairClassic {
    double splitRatio; //percent 0 - 1 with 0.1 steps
    double innerSplitAlpha, outerSplitAlpha; //percent 0 - 1 with 0.1 steps
    double splitDistance;

    public CrClassic() {
        color = new CrColor();
        color.setColorMode(CrColor.ColorMode.CUSTOM);
        color.setCustomColor(Color.GREEN);
        size = 2;
        thickness = 0.5;
        gap = -1;
        dot = false;
        tStyle = false;
        outline = true;
        outlinethickness = 1;

        splitRatio = 0.5;
        innerSplitAlpha = 1;
        outerSplitAlpha = 0.5;
        splitDistance = gap; //Default no change
    }

    public CrClassic(CrClassic crosshair) {
        color = crosshair.color;
        size = crosshair.size;
        thickness = crosshair.thickness;
        gap = crosshair.gap;
        dot = crosshair.dot;
        tStyle = crosshair.tStyle;
        outline = crosshair.outline;
        outlinethickness = crosshair.outlinethickness;

        splitRatio = crosshair.splitRatio;
        innerSplitAlpha = crosshair.innerSplitAlpha;
        outerSplitAlpha = crosshair.outerSplitAlpha;
        splitDistance = crosshair.splitDistance;
    }


    @Override
    public void draw(Graphics2D g, int posX, int posY, boolean isMoving) {
        double oldgap = gap;
        double oldsize = size;
        CrColor oldColor = color.copy();

        if (isMoving) {
            color.setColorMode(CrColor.ColorMode.CUSTOM);

            //Inner
            int innerAlpha = (int) (((double) oldColor.getSelectedColor().getAlpha()) * innerSplitAlpha);
            color.setCustomColor(new Color(oldColor.getSelectedColor().getRed(), oldColor.getSelectedColor().getGreen(), oldColor.getSelectedColor().getBlue(), innerAlpha));
            gap = splitDistance;
            size *= 1 - splitRatio;
            super.draw(g, posX, posY, isMoving);

            size = oldsize;

            //Outer
            int outerAlpha = (int) (((double) oldColor.getSelectedColor().getAlpha()) * outerSplitAlpha);
            color.setCustomColor(new Color(oldColor.getSelectedColor().getRed(), oldColor.getSelectedColor().getGreen(), oldColor.getSelectedColor().getBlue(), outerAlpha));
            gap = Math.abs(gap) + 20;
            size *= splitRatio;
            super.draw(g, posX, posY, isMoving);
        } else {
            super.draw(g, posX, posY, isMoving);
        }

        gap = oldgap;
        size = oldsize;
        color = oldColor;
    }

    @Override
    public int getStyleId() {
        return 2;
    }

    @Override
    public String getStyleName() {
        return "Classic";
    }

    @Override
    public String getInfo() {
        return "-Fully customizable\n-Crosshairgap changes\n based on weapon,\n spray and movement\n-Spits up when\n crosshairgap changes\n-Custum color";
    }

    @Override
    public void initWithCommands(String[] commands) {
        super.initWithCommands(commands);

        String[] temp;
        double safeValue; //Make sure value is between 0 and 1

        for (String cmd : commands) {
            cmd = cmd.replace("\"", " ");
            cmd = cmd.trim();
            String sss = cmd.split(" ")[0];
            temp = null;

            switch (sss) {
                default:
                    break;
                case "cl_crosshair_dynamic_maxdist_splitratio":
                    temp = cmd.split(" ");
                    splitRatio = Double.valueOf(temp[temp.length - 1]);
                    break;
                case "cl_crosshair_dynamic_splitdist":
                    temp = cmd.split(" ");
                    splitDistance = Double.valueOf(temp[temp.length - 1]);
                    break;
                case "cl_crosshair_dynamic_splitalpha_innermod":
                    temp = cmd.split(" ");
                    safeValue = Double.valueOf(temp[temp.length - 1]);
                    innerSplitAlpha = Math.max(Math.min(safeValue, 1), 0);
                    break;
                case "cl_crosshair_dynamic_splitalpha_outermod":
                    temp = cmd.split(" ");
                    safeValue = Double.valueOf(temp[temp.length - 1]);
                    outerSplitAlpha = Math.max(Math.min(safeValue, 1), 0);
                    break;
            }

        }

    }

    @Override
    public String[] getCommands() {
        String[] result = new String[18];

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

        result[14] = "cl_crosshair_dynamic_maxdist_splitratio " + splitRatio;
        result[15] = "cl_crosshair_dynamic_splitdist " + splitDistance;
        result[16] = "cl_crosshair_dynamic_splitalpha_innermod " + innerSplitAlpha;
        result[17] = "cl_crosshair_dynamic_splitalpha_outermod " + outerSplitAlpha;

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
        return new CrClassic(this);
    }


    public double getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(double splitRatio) {
        this.splitRatio = splitRatio;
    }

    public double getInnerSplitAlpha() {
        return innerSplitAlpha;
    }

    public void setInnerSplitAlpha(double innerSplitAlpha) {
        this.innerSplitAlpha = innerSplitAlpha;
    }

    public double getOuterSplitAlpha() {
        return outerSplitAlpha;
    }

    public void setOuterSplitAlpha(double outerSplitAlpha) {
        this.outerSplitAlpha = outerSplitAlpha;
    }

    public double getSplitDistance() {
        return splitDistance;
    }

    public void setSplitDistance(double splitDistance) {
        this.splitDistance = splitDistance;
    }
}
