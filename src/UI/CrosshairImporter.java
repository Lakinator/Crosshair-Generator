package UI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import Crosshair.*;
import com.sun.istack.internal.Nullable;

/**
 * 10.02.2018 | created by Lukas S
 */

public class CrosshairImporter {
    private Component parent;
    private JFileChooser chooser;

    public CrosshairImporter(@Nullable Component parent) {
        this.parent = parent;

        chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());
    }

    private File getFileFromDialog() {

        int returnVal = chooser.showOpenDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }

        return null;
    }

    //Set inputReader => null for filechooser
    public Crosshair read(@Nullable Reader inputReader) {
        if (inputReader == null) {
            try {
                File f = getFileFromDialog();
                if (f != null) inputReader = new FileReader(f);
                else return null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        BufferedReader br;
        String[] cmdArr = null;

        try {
            br = new BufferedReader(inputReader);
            try {
                ArrayList<String> temp = new ArrayList<>();
                String line = br.readLine().trim();

                while (line != null) {
                    //Sort out commented lines
                    if (!line.startsWith("//")) temp.add(line);

                    line = br.readLine();
                }

                cmdArr = temp.toArray(new String[temp.size()]);
            } finally {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (cmdArr == null) return null;

        //Determine crosshairstyla and load crosshair

        int crosshairstyle = -1;

        for (String s : cmdArr) {
            if (s.contains("cl_crosshairstyle")) {
                s = s.replace("\"", " ");
                s = s.trim();
                String[] cmd = s.split(" ");
                crosshairstyle = Double.valueOf(cmd[cmd.length - 1]).intValue();
            }
        }

        switch (crosshairstyle) {
            default:
                System.err.println("Wrong crosshairstyle: " + crosshairstyle);
                return null;
            case 0:
                CrDefault crDefault = new CrDefault();
                crDefault.initWithCommands(cmdArr);
                return crDefault;
            case 1:
                CrDefaultStatic crDefaultStatic = new CrDefaultStatic();
                crDefaultStatic.initWithCommands(cmdArr);
                return crDefaultStatic;
            case 2:
                CrClassic crClassic = new CrClassic();
                crClassic.initWithCommands(cmdArr);
                return crClassic;
            case 3:
                CrClassicDynamic crClassicDynamic = new CrClassicDynamic();
                crClassicDynamic.initWithCommands(cmdArr);
                return crClassicDynamic;
            case 4:
                CrClassicStatic crClassicStatic = new CrClassicStatic();
                crClassicStatic.initWithCommands(cmdArr);
                return crClassicStatic;
            case 5:
                CrClassicStaticPlus crClassicStaticPlus = new CrClassicStaticPlus();
                crClassicStaticPlus.initWithCommands(cmdArr);
                return crClassicStaticPlus;
        }

    }

}
