package Crosshair;

import java.awt.*;

/**
 * 11.02.2018 | created by Lukas S
 */

public class CrColor {

    public enum ColorMode {
        GREEN, YELLOW, BLUE, CYAN, CUSTOM // 1 - 2 - 3 - 4 - 5
    }

    private Color[] colors = new Color[]{new Color(90, 190, 50, 200), new Color(255, 220, 0, 200), new Color(0, 255, 255, 200), new Color(110, 200, 200, 200), Color.WHITE};
    private int selectedColor;

    public CrColor() {
        selectedColor = 1;
    }

    public CrColor(CrColor color) {
        this.selectedColor = color.selectedColor;
        this.setCustomColor(color.getCustomColor());
    }


    public Color getSelectedColor() {
        return colors[selectedColor - 1];
    }

    public void setColorMode(ColorMode mode) {
        selectedColor = mode.ordinal() + 1;
    }

    public ColorMode getColorMode() {
        return ColorMode.values()[selectedColor - 1];
    }

    public void setSelectedIndex(int index) {
        selectedColor = index;
    }

    public int getSelectedIndex() {
        return selectedColor;
    }

    public void setCustomColor(Color color) {
        colors[colors.length - 1] = color;
    }

    public Color getCustomColor() {
        return colors[colors.length - 1];
    }

    public CrColor copy() {
        return new CrColor(this);
    }

}
