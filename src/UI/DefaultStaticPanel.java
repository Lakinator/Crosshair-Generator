package UI;

import Crosshair.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 11.02.2018 | created by Lukas S
 */

public class DefaultStaticPanel extends CrosshairPanel implements ChangeListener, ActionListener {
    private CrDefaultStatic crosshair;
    private CrosshairUpdateListener listener;
    private JSlider gapSlider;
    private JButton[] colorBtns;
    private JTextArea infoTextArea;

    public DefaultStaticPanel(int width, int height, CrosshairUpdateListener listener) {
        setBounds(0, 0, width, height);
        setLayout(null);
        this.listener = listener;

        crosshair = new CrDefaultStatic();


        //Infotext

        infoTextArea = new JTextArea();
        infoTextArea.setBounds(0, 10, 200, 200);
        infoTextArea.setBackground(getBackground());
        infoTextArea.setFocusable(false);
        infoTextArea.setCursor(Cursor.getDefaultCursor());
        infoTextArea.setLineWrap(true);
        add(infoTextArea);


        //Gap

        JLabel gapLabel = new JLabel("Set gap:");
        gapLabel.setBounds(width / 2 - 30, 10, width / 8, 20);
        add(gapLabel);

        gapSlider = new JSlider(JSlider.HORIZONTAL, -10, 50, 0);
        gapSlider.setBounds(width / 3, gapLabel.getY() + 20, width / 3, 50);
        gapSlider.addChangeListener(this);
        gapSlider.setMajorTickSpacing(60);
        gapSlider.setPaintLabels(true);
        add(gapSlider);


        //Color Buttons

        colorBtns = new JButton[4];

        CrColor cTemp = new CrColor();

        for (int i = 0; i < colorBtns.length; i++) {
            colorBtns[i] = new JButton();
            colorBtns[i].setBounds(width / 2 - (100 - (i * 50)), height / 2 - 50, 50, 50);
            cTemp.setSelectedIndex(i + 1);
            colorBtns[i].setBackground(cTemp.getSelectedColor());
            colorBtns[i].setBorderPainted(false);
            colorBtns[i].addActionListener(this);
            colorBtns[i].setFocusPainted(false);
            colorBtns[i].setRolloverEnabled(false);
            add(colorBtns[i]);
        }

        initUiElements();

        listener.onChanged(crosshair);
    }

    public void initUiElements() {
        gapSlider.setValue((int) (crosshair.getGap() * 2.0));
        for (JButton colorBtn : colorBtns) {
            if (colorBtn.getBackground().getRGB() == crosshair.getColor().getRGB()) colorBtn.setText("A");
            else colorBtn.setText("");
        }
        infoTextArea.setText("\tInfo\n" + crosshair.getInfo());
    }


    @Override
    public String getName() {
        return crosshair.getStyleName();
    }

    @Override
    public Crosshair getCrosshair() {
        return crosshair;
    }

    @Override
    public void setCrosshair(Crosshair crosshair) {
        this.crosshair = (CrDefaultStatic) crosshair;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider == gapSlider) {
            crosshair.setGap(((double) gapSlider.getValue()) / 2.0);
        }


        listener.onChanged(crosshair);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        CrColor t = new CrColor();
        for (int i = 0; i < colorBtns.length; i++) {
            if (btn == colorBtns[i]) {
                t.setSelectedIndex(i + 1);
                crosshair.setColor(t.getColorMode(), null);
                colorBtns[i].setText("A");
            } else {
                colorBtns[i].setText("");
            }
        }

        listener.onChanged(crosshair);
    }
}
