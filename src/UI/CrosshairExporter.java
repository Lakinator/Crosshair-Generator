package UI;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Crosshair.Crosshair;

import javax.swing.*;

/**
 * 10.02.2018 | created by Lukas S
 */

public class CrosshairExporter {
    private JFileChooser chooser;
    private Crosshair crosshair;
    private Component parent;

    public CrosshairExporter(Crosshair crosshair, Component parent) {
        this.crosshair = crosshair;
        this.parent = parent;

        chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());
    }

    public boolean export() {
        int returnVal = chooser.showSaveDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println(chooser.getSelectedFile());
            File f = chooser.getSelectedFile();
            try {
                if (f.createNewFile()) {
                    System.out.println("Datei neu erstellt");
                } else {
                    System.out.println("Datei wird überschrieben");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return write(crosshair, f.getPath());
        }

        return false;
    }

    private boolean write(Crosshair crosshair, String path) {
        FileWriter fw;
        try {
            fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            for (String command : crosshair.getCommands()) {
                bw.write(command);
                bw.newLine();
            }

            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
