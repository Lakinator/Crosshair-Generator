package Crosshair;

import java.awt.*;

/**
 * 09.02.2018 | created by Lukas S
 */

public class CrClassicStatic extends CrosshairClassic {
    private boolean dynamicWeaponGap;

    public CrClassicStatic() {
        color = new CrColor();
        color.setColorMode(CrColor.ColorMode.CUSTOM);
        color.setCustomColor(Color.WHITE);
        size = 2;
        thickness = 0.5;
        gap = -1;
        dot = false;
        tStyle = false;
        outline = true;
        outlinethickness = 1;
        dynamicWeaponGap = false;
    }

    public CrClassicStatic(CrClassicStatic crosshair) {
        color = crosshair.color;
        size = crosshair.size;
        thickness = crosshair.thickness;
        gap = crosshair.gap;
        dot = crosshair.dot;
        tStyle = crosshair.tStyle;
        outline = crosshair.outline;
        outlinethickness = crosshair.outlinethickness;
        dynamicWeaponGap = crosshair.dynamicWeaponGap;
    }


    @Override
    public int getStyleId() {
        return 4;
    }

    @Override
    public String getStyleName() {
        return "Static";
    }

    @Override
    public String getInfo() {
        return "-Fully customizable\n-Crosshairgap is static\n-Weapon based gap\n can be turned\n on and off\n-Custum color";
    }

    @Override
    public void initWithCommands(String[] commands) {
        super.initWithCommands(commands);

        for (String cmd : commands) {
            cmd = cmd.replace("\"", " ");
            cmd = cmd.trim();
            String cl_cmd = cmd.split(" ")[0];

            if (cl_cmd.equals("cl_crosshairgap_useweaponvalue")) {
                String[] temp = cmd.split(" ");
                dynamicWeaponGap = temp[temp.length - 1].equals("1");
            }
        }
    }

    @Override
    public String[] getCommands() {
        String[] result = new String[15];

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
        result[14] = "cl_crosshairgap_useweaponvalue " + (dynamicWeaponGap ? "1" : "0");

        return result;
    }

    @Override
    public String toString() {
        return String.format("[Id:%s|Size:%s|Thickness:%s|Gap:%s|Color:%s|Alpha:%s|Dot:%s|TStyle:%s|Outline:%s|OutThick:%s|DynamicGap:%s]",
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
                outlinethickness,
                dynamicWeaponGap
        );
    }

    @Override
    public Crosshair copy() {
        return new CrClassicStatic(this);
    }

    public boolean isDynamicWeaponGap() {
        return dynamicWeaponGap;
    }

    public void setDynamicWeaponGap(boolean dynamicWeaponGap) {
        this.dynamicWeaponGap = dynamicWeaponGap;
    }

}
