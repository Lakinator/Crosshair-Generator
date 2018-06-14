package UI;

import Crosshair.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 12.02.2018 | created by Lukas S
 */

public class DefaultPanel extends CrosshairPanel implements ActionListener {
    private CrDefault crosshair;
    private CrosshairUpdateListener listener;
    private JButton[] colorBtns;
    private JTextArea infoTextArea;

    public DefaultPanel(int width, int height, CrosshairUpdateListener listener) {
        setBounds(0, 0, width, height);
        setLayout(null);
        this.listener = listener;

        crosshair = new CrDefault();


        //Infotext

        infoTextArea = new JTextArea();
        infoTextArea.setBounds(0, 10, 200, 200);
        infoTextArea.setBackground(getBackground());
        infoTextArea.setFocusable(false);
        infoTextArea.setCursor(Cursor.getDefaultCursor());
        infoTextArea.setLineWrap(true);
        add(infoTextArea);


        //Color Buttons

        colorBtns = new JButton[4];

        CrColor cTemp = new CrColor();

        for(int i = 0; i < colorBtns.length; i++) {
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
        this.crosshair = (CrDefault) crosshair;
        initUiElements();
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
