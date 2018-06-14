package Crosshair;

import java.awt.*;

/**
 * 12.02.2018 | created by Lukas S
 */

public abstract class CrosshairClassic implements Crosshair {
    protected double size;
    protected double thickness;
    protected double gap;
    protected CrColor color;
    protected boolean dot;
    protected boolean tStyle;
    protected boolean outline;
    protected double outlinethickness;

    @Override
    public void draw(Graphics2D g, int posX, int posY, boolean isMoving) {

        int realsize = ((int)(size * 2.0));
        int realthickness = ((int)(thickness * 2.0));
        int realgap = (int)(gap*2 + (thickness * 2.0) + 8);
        realgap /= 2;

        int outlinethickness = (int) this.outlinethickness;


        //Dot
        if (dot) {

            if (outline) {
                g.setColor(new Color(0, 0, 0, color.getSelectedColor().getAlpha()));
                g.fillRect(posX - (realthickness / 2) - outlinethickness, posY - (realthickness / 2) - outlinethickness, realthickness + (outlinethickness * 2), realthickness + (outlinethickness * 2));
            }


            g.setColor(color.getSelectedColor());
            g.fillRect(posX - (realthickness / 2), posY - (realthickness / 2), realthickness, realthickness);
        }

        //Oben

        if (!tStyle) {
            if (outline) {
                g.setColor(new Color(0, 0, 0, color.getSelectedColor().getAlpha()));
                g.fillRect(posX - (realthickness / 2) - outlinethickness, posY - realsize - realgap - outlinethickness, realthickness + (outlinethickness * 2), realsize + (outlinethickness * 2));
            }

            g.setColor(color.getSelectedColor());
            g.fillRect(posX - (realthickness / 2), posY - realsize - realgap, realthickness, realsize);
        }

        //Unten

        if (outline) {
            g.setColor(new Color(0, 0, 0, color.getSelectedColor().getAlpha()));
            g.fillRect(posX - (realthickness / 2) - outlinethickness, posY + realgap - outlinethickness, realthickness + (outlinethickness * 2), realsize + (outlinethickness * 2));
        }

        g.setColor(color.getSelectedColor());
        g.fillRect(posX - (realthickness / 2), posY + realgap, realthickness, realsize);

        //Links

        if (outline) {
            g.setColor(new Color(0, 0, 0, color.getSelectedColor().getAlpha()));
            g.fillRect(posX - realsize - realgap - outlinethickness, posY - (realthickness / 2) - outlinethickness, realsize + (outlinethickness * 2), realthickness + (outlinethickness * 2));
        }

        g.setColor(color.getSelectedColor());
        g.fillRect(posX - realsize - realgap, posY - (realthickness / 2), realsize, realthickness);

        //Rechts

        if (outline) {
            g.setColor(new Color(0, 0, 0, color.getSelectedColor().getAlpha()));
            g.fillRect(posX + realgap - outlinethickness, posY - (realthickness / 2) - outlinethickness, realsize + (outlinethickness * 2), realthickness + (outlinethickness * 2));
        }

        g.setColor(color.getSelectedColor());
        g.fillRect(posX + realgap, posY - (realthickness / 2), realsize, realthickness);
    }

    @Override
    public void initWithCommands(String[] commands) {
        String[] temp;
        Color cTemp;

        for (String cmd : commands) {
            cmd = cmd.replace("\"", " ");
            cmd = cmd.trim();
            String sss = cmd.split(" ")[0];
            temp = null;
            cTemp = null;

            switch (sss) {
                default:
                    break;
                case "cl_crosshairsize":
                    temp = cmd.split(" ");
                    size = Double.valueOf(temp[temp.length - 1]);
                    break;
                case "cl_crosshairthickness":
                    temp = cmd.split(" ");
                    thickness = Double.valueOf(temp[temp.length - 1]);
                    break;
                case "cl_crosshairgap":
                    temp = cmd.split(" ");
                    gap = Double.valueOf(temp[temp.length - 1]);
                    break;
                case "cl_crosshaircolor":
                    temp = cmd.split(" ");
                    int tempInt = Double.valueOf(temp[temp.length - 1]).intValue();
                    if (tempInt > 4 || tempInt < 1) {
                        color.setSelectedIndex(1);
                    }
                    color.setSelectedIndex(tempInt);
                    break;
                case "cl_crosshaircolor_r":
                    temp = cmd.split(" ");
                    cTemp = new Color(Double.valueOf(temp[temp.length - 1]).intValue(), color.getCustomColor().getGreen(), color.getCustomColor().getBlue(), color.getCustomColor().getAlpha());
                    color.setCustomColor(cTemp);
                    break;
                case "cl_crosshaircolor_g":
                    temp = cmd.split(" ");
                    cTemp = new Color(color.getCustomColor().getRed(), Double.valueOf(temp[temp.length - 1]).intValue(), color.getCustomColor().getBlue(), color.getCustomColor().getAlpha());
                    color.setCustomColor(cTemp);
                    break;
                case "cl_crosshaircolor_b":
                    temp = cmd.split(" ");
                    cTemp = new Color(color.getCustomColor().getRed(), color.getCustomColor().getGreen(), Double.valueOf(temp[temp.length - 1]).intValue(), color.getCustomColor().getAlpha());
                    color.setCustomColor(cTemp);
                    break;
                case "cl_crosshairalpha":
                    temp = cmd.split(" ");
                    cTemp = new Color(color.getCustomColor().getRed(), color.getCustomColor().getGreen(), color.getCustomColor().getBlue(), Double.valueOf(temp[temp.length - 1]).intValue());
                    color.setCustomColor(cTemp);
                    break;
                case "cl_crosshair_drawoutline":
                    temp = cmd.split(" ");
                    outline = temp[temp.length - 1].equals("1");
                    break;
                case "cl_crosshair_outlinethickness":
                    temp = cmd.split(" ");
                    outlinethickness = Double.valueOf(temp[temp.length - 1]);
                    break;
                case "cl_crosshairdot":
                    temp = cmd.split(" ");
                    dot = temp[temp.length - 1].equals("1");
                    break;
                case "cl_crosshair_t":
                    temp = cmd.split(" ");
                    tStyle = temp[temp.length - 1].equals("1");
                    break;

            }
        }
    }

    @Override
    public Color getColor() {
        return color.getSelectedColor();
    }

    @Override
    public void setColor(CrColor.ColorMode mode, Color color) {
        this.color.setColorMode(mode);

        if (mode == CrColor.ColorMode.CUSTOM) {
            this.color.setCustomColor(color);
        }
    }


    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getGap() {
        return gap;
    }

    public void setGap(double gap) {
        this.gap = gap;
    }

    public boolean isDot() {
        return dot;
    }

    public void setDot(boolean dot) {
        this.dot = dot;
    }

    public boolean isTStyle() {
        return tStyle;
    }

    public void setTStyle(boolean tStyle) {
        this.tStyle = tStyle;
    }

    public boolean isOutline() {
        return outline;
    }

    public void setOutline(boolean outline) {
        this.outline = outline;
    }

    public double getOutlinethickness() {
        return outlinethickness;
    }

    public void setOutlinethickness(double outlinethickness) {
        this.outlinethickness = outlinethickness;
    }
}
