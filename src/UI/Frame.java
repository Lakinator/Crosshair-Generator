package UI;

import Crosshair.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 10.02.2018 | created by Lukas S
 */

public class Frame extends JFrame implements ActionListener {

    private static Crosshair crosshairLaki, crosshairTrilluxe, crosshairFallen;

    static {
        try {
            crosshairLaki = new CrosshairImporter(null).read(new InputStreamReader(ClassLoader.getSystemResource("presets/laki.cfg").openStream()));
            crosshairTrilluxe = new CrosshairImporter(null).read(new InputStreamReader(ClassLoader.getSystemResource("presets/trilluxe.cfg").openStream()));
            crosshairFallen = new CrosshairImporter(null).read(new InputStreamReader(ClassLoader.getSystemResource("presets/fallen.cfg").openStream()));
            System.out.println("Presets loaded");
        } catch (IOException e) {
            System.err.println("Failed loading presets");
            e.printStackTrace();
        }
    }

    private Crosshair crosshair;
    private DrawPanel drawPanel;
    private JTabbedPane jTabbedPane;
    private CrosshairPanel[] tabPanels;

    private JMenuBar menuBar;
    private JMenu fileMenu, colorMenu, presetMenu, aboutMenu;
    private JMenuItem importItem, exportItem;
    private JMenuItem colorGreenItem, colorYellowItem, colorBlueItem, colorCyanItem, colorCustomItem;
    private JMenuItem presetLakiItem, presetTrilluxeItem, presetFallenItem;
    private JMenuItem aboutApplicationItem;


    public Frame() {
        setTitle("Crosshair Generator BETA");
        setSize(800, 600);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initDrawPanel();
        initTabMenu();
        initMenu();

        crosshair = tabPanels[jTabbedPane.getSelectedIndex()].getCrosshair();
        updateTabs();

        setVisible(true);
    }


    private void initDrawPanel() {
        //Draw Panel

        drawPanel = new DrawPanel();
        MouseHandler m = new MouseHandler();
        drawPanel.addMouseListener(m);
        drawPanel.addMouseMotionListener(m);

        add(drawPanel);
    }

    private void initTabMenu() {
        //Tabs
        jTabbedPane = new JTabbedPane();
        jTabbedPane.setBounds(0, 0, getWidth() - 5, getHeight() / 2);

        CrosshairUpdateListener listener = crosshair -> {
            Frame.this.crosshair = crosshair;
            drawPanel.repaint();
        };

        tabPanels = new CrosshairPanel[3];
        tabPanels[0] = new DefaultPanel(getWidth() - 5, getHeight() / 2, listener);
        tabPanels[1] = new DefaultStaticPanel(getWidth() - 5, getHeight() / 2, listener);
        tabPanels[2] = new CustomPanel(getWidth() - 5, getHeight() / 2, listener);


        for (CrosshairPanel panel : tabPanels) {
            jTabbedPane.addTab(panel.getName(), panel);
        }

        jTabbedPane.addChangeListener(e -> {
            crosshair = tabPanels[jTabbedPane.getSelectedIndex()].getCrosshair();
            drawPanel.repaint();
        });

        add(jTabbedPane);
    }

    private void initMenu() {
        //Menus

        menuBar = new JMenuBar();

        //File Menu

        fileMenu = new JMenu("File");
        importItem = new JMenuItem("Import");
        fileMenu.add(importItem);
        importItem.addActionListener(this);
        exportItem = new JMenuItem("Export");
        exportItem.addActionListener(this);
        fileMenu.add(exportItem);

        //Color Menu

        colorMenu = new JMenu("Color");
        colorGreenItem = new JMenuItem("Green");
        colorGreenItem.addActionListener(this);
        colorYellowItem = new JMenuItem("Yellow");
        colorYellowItem.addActionListener(this);
        colorBlueItem = new JMenuItem("Blue");
        colorBlueItem.addActionListener(this);
        colorCyanItem = new JMenuItem("Cyan");
        colorCyanItem.addActionListener(this);
        colorCustomItem = new JMenuItem("Custom...");
        colorCustomItem.addActionListener(this);

        colorMenu.add(colorGreenItem);
        colorMenu.add(colorYellowItem);
        colorMenu.add(colorBlueItem);
        colorMenu.add(colorCyanItem);
        colorMenu.addSeparator();
        colorMenu.add(colorCustomItem);

        //Preset Menu

        presetMenu = new JMenu("Presets");
        presetLakiItem = new JMenuItem("Laki");
        presetLakiItem.addActionListener(this);
        presetTrilluxeItem = new JMenuItem("TrilluXe");
        presetTrilluxeItem.addActionListener(this);
        presetFallenItem = new JMenuItem("FalleN");
        presetFallenItem.addActionListener(this);

        presetMenu.add(presetLakiItem);
        presetMenu.add(presetTrilluxeItem);
        presetMenu.add(presetFallenItem);

        //Help Menu

        aboutMenu = new JMenu("Help");
        aboutApplicationItem = new JMenuItem("About this program");
        aboutApplicationItem.addActionListener(this);

        aboutMenu.add(aboutApplicationItem);


        menuBar.add(fileMenu);
        menuBar.add(colorMenu);
        menuBar.add(presetMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);
    }

    public void updateTabs() {

        if (crosshair.getStyleId() <= 1) {
            tabPanels[crosshair.getStyleId()].setCrosshair(crosshair);
            jTabbedPane.setSelectedIndex(crosshair.getStyleId());
        } else {
            tabPanels[2].setCrosshair(crosshair);
            jTabbedPane.setSelectedIndex(2);
        }

        drawPanel.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem item = (JMenuItem) e.getSource();

        if (item == importItem) {

            Crosshair temp = new CrosshairImporter(this).read(null);
            if (temp != null) {
                crosshair = temp;
                updateTabs();

                JOptionPane.showMessageDialog(this, "Crosshair import successfull", "Crosshair Import", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Crosshair import failed", "Crosshair Import", JOptionPane.ERROR_MESSAGE);
            }

        } else if (item == exportItem) {
            if (new CrosshairExporter(crosshair, this).export()) {
                JOptionPane.showMessageDialog(this, "Crosshair export successfull", "Crosshair Export", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Crosshair export failed", "Crosshair Export", JOptionPane.ERROR_MESSAGE);
            }
        } else if (item == colorGreenItem) {
            crosshair.setColor(CrColor.ColorMode.GREEN, null);
            updateTabs();
        } else if (item == colorYellowItem) {
            crosshair.setColor(CrColor.ColorMode.YELLOW, null);
            updateTabs();
        } else if (item == colorBlueItem) {
            crosshair.setColor(CrColor.ColorMode.BLUE, null);
            updateTabs();
        } else if (item == colorCyanItem) {
            crosshair.setColor(CrColor.ColorMode.CYAN, null);
            updateTabs();
        } else if (item == colorCustomItem) {
            Color newColor = JColorChooser.showDialog(this, "Choose Color (Not for Default Crosshairs)", Color.WHITE);
            if (newColor != null) {
                crosshair.setColor(CrColor.ColorMode.CUSTOM, newColor);
            }

            updateTabs();
        }  else if (item == aboutApplicationItem) {
            String message = "<html><body><div width='250px' align='center'>" +
                    "<p style='color:red'>Still in Beta<p><br/>" +
                    "Hold Mouse-Left to drag crosshair around<br/>" +
                    "Klick Mouse-Right to change background<br/><br/>" +
                    "Made by Laki<br/>" +
                    "Check out my Website: http://lakinator.bplaced.net/" +
                    "</div></body></html>";
            JLabel messageLabel = new JLabel(message);
            JOptionPane.showMessageDialog(null, messageLabel, "About", JOptionPane.PLAIN_MESSAGE);
        } else if (item == presetLakiItem) {
            crosshair = crosshairLaki.copy();
            updateTabs();
        } else if (item == presetTrilluxeItem) {
            crosshair = crosshairTrilluxe.copy();
            updateTabs();
        } else if (item == presetFallenItem) {
            crosshair = crosshairFallen.copy();
            updateTabs();
        }
    }



    private class MouseHandler extends MouseAdapter {
        private Cursor transparent;

        public MouseHandler() {
            Toolkit tk = getToolkit();
            transparent = tk.createCustomCursor(tk.getImage(""), new Point(), "trans");
        }


        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                drawPanel.changeBackground();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            drawPanel.updatePos(e.getX(), e.getY());
            drawPanel.setIsMoving(true);
            setCursor(transparent);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(Cursor.getDefaultCursor());
            drawPanel.resetPos();
            drawPanel.setIsMoving(false);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(Cursor.getDefaultCursor());
            drawPanel.resetPos();
            drawPanel.setIsMoving(false);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setCursor(Cursor.getDefaultCursor());
            drawPanel.currentTime = 0;
            drawPanel.updatesPerSecond = -1;
            drawPanel.setIsMoving(false);
        }
    }


    private class DrawPanel extends JPanel {
        private int posX, posY;
        private BufferedImage[] img;
        private int imgPosition = 0;
        private boolean isMoving;

        private long old;
        private long currentTime = 0;
        private int updatesPerSecond;

        public DrawPanel() {
            setBackground(Color.DARK_GRAY);
            setBounds(5, Frame.this.getHeight() / 2 + 5, Frame.this.getWidth() - 15, Frame.this.getHeight() / 2 - 60);
            posX = getWidth() / 2;
            posY = getHeight() / 2;
            isMoving = false;

            img = new BufferedImage[5];
            try {
                img[0] = ImageIO.read(ClassLoader.getSystemResource("img/csgo_overpass.png"));
                img[1] = ImageIO.read(ClassLoader.getSystemResource("img/csgo_cobble.png"));
                img[2] = ImageIO.read(ClassLoader.getSystemResource("img/csgo_mirage.png"));
                img[3] = ImageIO.read(ClassLoader.getSystemResource("img/csgo_cache.png"));
                img[4] = ImageIO.read(ClassLoader.getSystemResource("img/csgo_inferno.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void changeBackground() {
            if (imgPosition + 1 > img.length - 1) {
                imgPosition = 0;
            } else {
                imgPosition++;
            }

            repaint();
        }

        public void updatePos(int x, int y) {
            this.posX = x;
            this.posY = y;
            repaint();
        }

        public void resetPos() {
            posX = getWidth() / 2;
            posY = getHeight() / 2;
            repaint();
        }

        public void setIsMoving(boolean isMoving) {
            this.isMoving = isMoving;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            currentTime += System.currentTimeMillis() - old;

            if (currentTime < 1000 && currentTime >= 0) {
                updatesPerSecond++;
            } else {
                updatesPerSecond = 0;
                currentTime = 0;
            }


            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (img[imgPosition] != null) {
                g2d.drawImage(img[imgPosition], 0, 0, getWidth(), getHeight(), null);
            } else {
                g2d.setBackground(Color.DARK_GRAY);
            }

            if (crosshair != null) {
                crosshair.draw(g2d, posX, posY, isMoving);
                g2d.setColor(Color.WHITE);
                g2d.drawString(crosshair.toString(), 0, 10);
            }

            g2d.drawString(String.format("[UPS:%s|isMoving:%s]", updatesPerSecond, isMoving), 0, getHeight() - 5);

            old = System.currentTimeMillis();
        }
    }

}
