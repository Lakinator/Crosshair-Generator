package UI;

import Crosshair.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 15.02.2018 | created by Lukas S
 */

public class CustomPanel extends CrosshairPanel implements ChangeListener, ItemListener, ActionListener {
    private CrosshairUpdateListener listener;
    private CrosshairClassic[] crosshairs;
    private int selectedCrosshair;

    private JSlider sizeSlider;
    private JSlider thicknessSlider;
    private JSlider gapSlider;
    private JSlider outlineSlider;
    private JCheckBox outlineBox;
    private JCheckBox dotBox;
    private JCheckBox tBox;

    private JCheckBox dynamicGapBox; //Extra Setting for Static + Static-Dynamic

    private JComboBox<String> crosshairMenu;
    private String[] crosshairMenuItems;

    private JTextArea infoTextArea;

    private JPanel extraSettingsPanel;
    private JSlider splitRatioSlider;
    private JSlider splitDistanceSlider;
    private JButton innerAlphaButton;
    private JButton outerAlphaButton;


    public CustomPanel(int width, int height, CrosshairUpdateListener listener) {
        setBounds(0, 0, width, height);
        setLayout(null);

        this.listener = listener;
        this.selectedCrosshair = 0;

        crosshairs = new CrosshairClassic[]{new CrClassic(), new CrClassicDynamic(), new CrClassicStatic(), new CrClassicStaticPlus()};

        //Dropdown Menu

        crosshairMenuItems = new String[4];
        for (int i = 0; i < crosshairMenuItems.length; i++) {
            crosshairMenuItems[i] = crosshairs[i].getStyleName();
        }

        crosshairMenu = new JComboBox<>(crosshairMenuItems);
        crosshairMenu.setBounds(width - (width / 5), 10, width / 6, 20);
        crosshairMenu.addItemListener(this);
        add(crosshairMenu);


        //Infotext

        infoTextArea = new JTextArea();
        infoTextArea.setBounds(crosshairMenu.getX(), crosshairMenu.getY() + 30, crosshairMenu.getWidth(), 200);
        infoTextArea.setBackground(getBackground());
        infoTextArea.setFocusable(false);
        infoTextArea.setCursor(Cursor.getDefaultCursor());
        infoTextArea.setLineWrap(true);
        add(infoTextArea);


        //Size

        JLabel sizeLabel = new JLabel("Set size:");
        sizeLabel.setBounds(width / 10, 10, width / 8, 20);
        add(sizeLabel);

        sizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
        sizeSlider.setBounds(10, sizeLabel.getY() + 20, width / 4, 40);
        sizeSlider.addChangeListener(this);
        sizeSlider.setMajorTickSpacing(50);
        sizeSlider.setPaintLabels(true);
        add(sizeSlider);


        //Thickness

        JLabel thicknessLabel = new JLabel("Set thickness:");
        thicknessLabel.setBounds(width / 12, sizeLabel.getY() + 60, width / 8, 20);
        add(thicknessLabel);

        thicknessSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
        thicknessSlider.setBounds(10, thicknessLabel.getY() + 20, width / 4, 40);
        thicknessSlider.addChangeListener(this);
        thicknessSlider.setMajorTickSpacing(50);
        thicknessSlider.setPaintLabels(true);
        add(thicknessSlider);


        //Gap

        JLabel gapLabel = new JLabel("Set gap:");
        gapLabel.setBounds(width / 10, thicknessLabel.getY() + 60, width / 8, 20);
        add(gapLabel);

        gapSlider = new JSlider(JSlider.HORIZONTAL, -10, 50, 0);
        gapSlider.setBounds(10, gapLabel.getY() + 20, width / 4, 40);
        gapSlider.addChangeListener(this);
        gapSlider.setMajorTickSpacing(60);
        gapSlider.setPaintLabels(true);
        add(gapSlider);


        //Outline

        JLabel outlineLabel = new JLabel("Set outline:");
        outlineLabel.setBounds(width / 12, gapLabel.getY() + 60, width / 8, 20);
        add(outlineLabel);

        outlineSlider = new JSlider(JSlider.HORIZONTAL, 1, 3, 1);
        outlineSlider.setBounds(10, outlineLabel.getY() + 20, width / 4, 40);
        outlineSlider.addChangeListener(this);
        outlineSlider.setMajorTickSpacing(1);
        outlineSlider.setPaintLabels(true);
        outlineSlider.setSnapToTicks(true);
        add(outlineSlider);


        //Checkboxes

        dotBox = new JCheckBox("Enable/Disable Dot");
        dotBox.setBounds(width / 4 + 40, 30, width / 4, 20);
        dotBox.addItemListener(this);
        add(dotBox);

        tBox = new JCheckBox("Enable/Disable T-Style");
        tBox.setBounds(dotBox.getX(), dotBox.getY() + 60, width / 4, 20);
        tBox.addItemListener(this);
        add(tBox);

        dynamicGapBox = new JCheckBox("Enable/Disable Weapon Gap");
        dynamicGapBox.setBounds(tBox.getX(), tBox.getY() + 60, width / 4, 20);
        dynamicGapBox.addItemListener(this);
        dynamicGapBox.setVisible(false);
        add(dynamicGapBox);

        outlineBox = new JCheckBox("Enable/Disable Outline");
        outlineBox.setBounds(dynamicGapBox.getX(), dynamicGapBox.getY() + 60, width / 4, 20);
        outlineBox.addItemListener(this);
        add(outlineBox);


        //Extra Panel


        extraSettingsPanel = new JPanel();
        extraSettingsPanel.setLayout(null);
        extraSettingsPanel.setBounds(dotBox.getX() + dotBox.getWidth(), 10, width - crosshairMenu.getX(), 250);
        add(extraSettingsPanel);


        //Dynamic Split Ratio

        JLabel splitRatioLabel = new JLabel("Set Splitratio (in %):");
        splitRatioLabel.setBounds(20, 0, extraSettingsPanel.getWidth() - 20, 20);
        extraSettingsPanel.add(splitRatioLabel);

        splitRatioSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        splitRatioSlider.setBounds(10, splitRatioLabel.getY() + 20, extraSettingsPanel.getWidth() - 20, 50);
        splitRatioSlider.addChangeListener(this);
        splitRatioSlider.setMajorTickSpacing(100);
        splitRatioSlider.setMinorTickSpacing(10);
        splitRatioSlider.setPaintTicks(true);
        splitRatioSlider.setPaintLabels(true);
        splitRatioSlider.setSnapToTicks(true);
        extraSettingsPanel.add(splitRatioSlider);


        //Dynamic Split Distance

        JLabel splitDistanceLabel = new JLabel("Set Splitdistance:");
        splitDistanceLabel.setBounds(20, splitRatioLabel.getY() + 70, extraSettingsPanel.getWidth() - 20, 20);
        extraSettingsPanel.add(splitDistanceLabel);

        splitDistanceSlider = new JSlider(JSlider.HORIZONTAL, -10, 50, 0);
        splitDistanceSlider.setBounds(10, splitDistanceLabel.getY() + 20, extraSettingsPanel.getWidth() - 20, 40);
        splitDistanceSlider.addChangeListener(this);
        splitDistanceSlider.setMajorTickSpacing(60);
        splitDistanceSlider.setPaintLabels(true);
        extraSettingsPanel.add(splitDistanceSlider);


        //Dynamic Alpha Buttons

        JLabel innerAlphaLabel = new JLabel("Inner Alpha");
        innerAlphaLabel.setBounds(10, splitDistanceSlider.getY() + splitRatioSlider.getHeight() + 10, 70, 20);
        extraSettingsPanel.add(innerAlphaLabel);

        innerAlphaButton = new JButton();
        innerAlphaButton.setBounds(10, innerAlphaLabel.getY() + 20, 70, 50);
        innerAlphaButton.setBorderPainted(false);
        innerAlphaButton.addActionListener(this);
        innerAlphaButton.setFocusPainted(false);
        innerAlphaButton.setRolloverEnabled(false);
        extraSettingsPanel.add(innerAlphaButton);


        JLabel outerAlphaLabel = new JLabel("Outer Alpha");
        outerAlphaLabel.setBounds(extraSettingsPanel.getWidth() - 70, splitDistanceSlider.getY() + splitRatioSlider.getHeight() + 10, 70, 20);
        extraSettingsPanel.add(outerAlphaLabel);

        outerAlphaButton = new JButton();
        outerAlphaButton.setBounds(outerAlphaLabel.getWidth() + 20, outerAlphaLabel.getY() + 20, 70, 50);
        outerAlphaButton.setBorderPainted(false);
        outerAlphaButton.addActionListener(this);
        outerAlphaButton.setFocusPainted(false);
        outerAlphaButton.setRolloverEnabled(false);
        extraSettingsPanel.add(outerAlphaButton);


        initUiElements();
        this.listener.onChanged(getCrosshair());
    }

    public void initUiElements() {
        //Standard
        sizeSlider.setValue((int) (crosshairs[selectedCrosshair].getSize() * 2.0));
        thicknessSlider.setValue((int) (crosshairs[selectedCrosshair].getThickness() * 2.0));
        gapSlider.setValue((int) (crosshairs[selectedCrosshair].getGap() * 2.0));
        dotBox.setSelected(crosshairs[selectedCrosshair].isDot());
        tBox.setSelected(crosshairs[selectedCrosshair].isTStyle());
        outlineSlider.setValue((int) crosshairs[selectedCrosshair].getOutlinethickness());
        outlineBox.setSelected(crosshairs[selectedCrosshair].isOutline());

        //Other
        crosshairMenu.setSelectedIndex(selectedCrosshair);
        infoTextArea.setText(getCrosshair().getInfo());
        extraSettingsPanel.setVisible(selectedCrosshair == 0); //Only visible for first style

        if (selectedCrosshair == 0) {
            //Extra Settings are active

            innerAlphaButton.setBackground(
                    new Color(
                            getCrosshair().getColor().getRed(),
                            getCrosshair().getColor().getGreen(),
                            getCrosshair().getColor().getBlue(),
                            (int) (((double) getCrosshair().getColor().getAlpha()) * ((CrClassic) getCrosshair()).getInnerSplitAlpha())
                    )
            );
            innerAlphaButton.setText((int) (((CrClassic) getCrosshair()).getInnerSplitAlpha() * 100.0) + "%");

            outerAlphaButton.setBackground(
                    new Color(
                            getCrosshair().getColor().getRed(),
                            getCrosshair().getColor().getGreen(),
                            getCrosshair().getColor().getBlue(),
                            (int) (((double) getCrosshair().getColor().getAlpha()) * ((CrClassic) getCrosshair()).getOuterSplitAlpha())
                    )
            );
            outerAlphaButton.setText((int) (((CrClassic) getCrosshair()).getOuterSplitAlpha() * 100.0) + "%");
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (slider == sizeSlider) {
            crosshairs[selectedCrosshair].setSize(((double) sizeSlider.getValue()) / 2.0);
        } else if (slider == thicknessSlider) {
            crosshairs[selectedCrosshair].setThickness(((double) thicknessSlider.getValue()) / 2.0);
        } else if (slider == gapSlider) {
            crosshairs[selectedCrosshair].setGap(((double) gapSlider.getValue()) / 2.0);
        } else if (slider == outlineSlider) {
            crosshairs[selectedCrosshair].setOutlinethickness(outlineSlider.getValue());
        }

        if (selectedCrosshair == 0) {
            //Extra Settings are active

            if (slider == splitRatioSlider) {
                ((CrClassic) getCrosshair()).setSplitRatio(splitRatioSlider.getValue() / 100.0);
            } else if (slider == splitDistanceSlider) {
                ((CrClassic) getCrosshair()).setSplitDistance(splitDistanceSlider.getValue() / 2.0);
            }
        }


        listener.onChanged(getCrosshair());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getItem() == outlineBox) {
            crosshairs[selectedCrosshair].setOutline(e.getStateChange() == ItemEvent.SELECTED);
        } else if (e.getItem() == dotBox) {
            crosshairs[selectedCrosshair].setDot(e.getStateChange() == ItemEvent.SELECTED);
        } else if (e.getItem() == tBox) {
            crosshairs[selectedCrosshair].setTStyle(e.getStateChange() == ItemEvent.SELECTED);
        } else if (e.getItem() == dynamicGapBox) {
            if (selectedCrosshair == 2) {
                ((CrClassicStatic) getCrosshair()).setDynamicWeaponGap(e.getStateChange() == ItemEvent.SELECTED);
            } else if (selectedCrosshair == 3) {
                ((CrClassicStaticPlus) getCrosshair()).setDynamicWeaponGap(e.getStateChange() == ItemEvent.SELECTED);
            }
        } else if (e.getItem() == crosshairMenu.getSelectedItem()) {
            selectedCrosshair = crosshairMenu.getSelectedIndex();
            infoTextArea.setText(getCrosshair().getInfo());

            if (selectedCrosshair > 1) {
                dynamicGapBox.setVisible(true);
            } else {
                dynamicGapBox.setVisible(false);
            }
            initUiElements();
        }

        listener.onChanged(getCrosshair());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == innerAlphaButton) {
            int result = new AlphaInputDialog((int) (((CrClassic) getCrosshair()).getInnerSplitAlpha() * 100.0)).showAlphaDialog(this);
            ((CrClassic) getCrosshair()).setInnerSplitAlpha(result / 100.0);
        } else if (e.getSource() == outerAlphaButton) {
            int result = new AlphaInputDialog((int) (((CrClassic) getCrosshair()).getOuterSplitAlpha() * 100.0)).showAlphaDialog(this);
            ((CrClassic) getCrosshair()).setOuterSplitAlpha(result / 100.0);
        }

        initUiElements();
    }


    @Override
    public String getName() {
        return "Custom";
    }

    @Override
    public Crosshair getCrosshair() {
        return crosshairs[selectedCrosshair];
    }

    @Override
    public void setCrosshair(Crosshair crosshair) {
        int index = crosshair.getStyleId() - 2;
        selectedCrosshair = index;
        crosshairs[index] = (CrosshairClassic) crosshair;
        initUiElements();
    }
}
