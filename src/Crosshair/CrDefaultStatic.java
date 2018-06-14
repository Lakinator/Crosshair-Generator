package Crosshair;

/**
 * 09.02.2018 | created by Lukas S
 */

public class CrDefaultStatic extends CrosshairDefault {

    public CrDefaultStatic() {
        gap = 1.5;
        color = new CrColor();
    }

    public CrDefaultStatic(CrDefaultStatic crosshair) {
        gap = crosshair.gap;
        color = crosshair.color;
    }


    @Override
    public int getStyleId() {
        return 1;
    }

    @Override
    public String getStyleName() {
        return "Default Static";
    }

    @Override
    public String getInfo() {
        return "- Only color and gap are changeable\n- Static Gap\n- Only colorpresets";
    }

    @Override
    public String[] getCommands() {
        String[] result = new String[3];

        result[0] = "cl_crosshairstyle " + getStyleId();
        result[1] = "cl_fixedcrosshairgap " + gap;
        result[2] = "cl_crosshaircolor " + color.getSelectedIndex();

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
                case "cl_fixedcrosshairgap":
                    temp = cmd.split(" ");
                    gap = Double.valueOf(temp[temp.length - 1]);
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
        return String.format("[Id: %s | Gap: %s | Color: %s]", getStyleId(), gap, color.getColorMode().name());
    }

    @Override
    public Crosshair copy() {
        return new CrDefaultStatic(this);
    }

}
