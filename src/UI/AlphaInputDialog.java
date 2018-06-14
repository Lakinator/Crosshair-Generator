package UI;

import javax.swing.*;
import java.awt.*;

/**
 * 16.02.2018 | created by Lukas S
 */

public class AlphaInputDialog extends JOptionPane {
    private JSlider slider;

    public AlphaInputDialog(int initValue) {
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, initValue);
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.addChangeListener(e -> {
            if (e.getSource() == slider) {
                if (!slider.getValueIsAdjusting()) {
                    this.setInputValue(slider.getValue());
                }
            }
        });

        this.setMessage(new Object[] { "Select a value (in %): ", slider });
        this.setMessageType(JOptionPane.QUESTION_MESSAGE);
        this.setOptionType(JOptionPane.OK_CANCEL_OPTION);
        this.setInputValue(initValue);
    }

    public int showAlphaDialog(Component parent) {
        JDialog dialog = this.createDialog(parent, "Select Alpha");
        dialog.setVisible(true);
        return (int) this.getInputValue();
    }

}
