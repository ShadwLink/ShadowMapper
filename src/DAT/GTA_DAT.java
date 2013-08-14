/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAT;

import file_io.Message;
import shadowmapper.Finals;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Kilian Steenman (Shadow-Link)
 *
 */
public class GTA_DAT {
    private FileReader fileReader; //Reader
    private BufferedReader input; //Reader

    private String gameDir; //game dir
    private String fileName; //file name
    private int gameType; //gametype ie: SA

    public boolean changed = false; //True when the file needs to be saved

    public ArrayList<String> img = new ArrayList();
    public ArrayList<String> ipl = new ArrayList();
    public ArrayList<String> ide = new ArrayList();
    public ArrayList<String> imgList = new ArrayList();
    public ArrayList<String> water = new ArrayList();
    public ArrayList<String> colFile = new ArrayList();
    public ArrayList<String> splash = new ArrayList();

    public GTA_DAT(String gameDir, int gameType) {
        this.gameDir = gameDir;
        this.gameType = gameType;
        switch(this.gameType){
            case Finals.gIII:
                this.fileName = gameDir + "data/gta3.dat";
                break;
            case Finals.gVC:
                this.fileName = gameDir + "data/gta_vc.dat";
                break;
            case Finals.gSA:
                this.fileName = gameDir + "data/gta.dat";
                break;
            case Finals.gIV:
                this.fileName = gameDir + "common/data/gta.dat";
                break;
        }
        Message.displayMsgHigh("Filename: " + fileName);
        loadGTA_DAT();
    }

    private boolean loadGTA_DAT(){
        if(openGTA_DAT()){
            try {
                String line = null; //not declared within while loop
                while ((line = input.readLine()) != null) {
                    if (line.startsWith("#") || line.length() < 1) {
                        Message.displayMsgHigh(line);
                    }else{
                        String split[] = line.split(" ");
                        split[1] = split[1].replace("platform:", "pc");
                        split[1] = split[1].replace("common:", "common");
                        if(!split[1].contains("common")) split[1] = split[1].replace("IPL", "WPL");
                        if(split[0].equals("IMG")){
                            split[1] = split[1].replace("\\", "/");
                            img.add(split[1]);
                            Message.displayMsgHigh("IMG: " + split[1]);
                        }else if(split[0].equals("IDE")){
                            ide.add(split[1]);
                            Message.displayMsgHigh("IDE: " + split[1]);
                        }else if(split[0].equals("IPL")){
                            ipl.add(split[1]);
                            Message.displayMsgHigh("IPL: " + split[1]);
                        }else if(split[0].equals("IMGLIST")){
                            imgList.add(split[1]);
                            Message.displayMsgHigh("IMGLIST: " + split[1]);
                        }else if(split[0].equals("WATER")){
                            water.add(split[1]);
                            Message.displayMsgHigh("WATER: " + split[1]);
                        }else if(split[0].equals("SPLASH")){
                            splash.add(split[1]);
                            Message.displayMsgHigh("SPLASH: " + split[1]);
                        }else if(split[0].equals("COLFILE")){
                            colFile.add(split[1]);
                            Message.displayMsgHigh("COLFILE: " + split[2]);
                        }
                    }
                }
                closeGTA_DAT();
            } catch (IOException ex) {
                Logger.getLogger(GTA_DAT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadImagesFromIMGLIST();
        return true;
    }

    public void loadImagesFromIMGLIST(){
        for(int imgTexts = 0; imgTexts < imgList.size(); imgTexts++){
            try {
                FileReader imgTextReader = null;
                System.out.println("Loading img text: " + gameDir + imgList.get(imgTexts));
                imgTextReader = new FileReader(gameDir + imgList.get(imgTexts));
                BufferedReader inputImgText = new BufferedReader(imgTextReader);
                String line = null; //not declared within while loop
                while ((line = inputImgText.readLine()) != null) {
                    if (line.startsWith("platformimg:")) {
                        line = line.replace("platformimg:", "pc");
                        line = line.replace("\t", "");
                        img.add(line);
                        System.out.println(line);
                    }
                }
                inputImgText.close();
                imgTextReader.close();
            } catch (IOException ex) {
                Logger.getLogger(GTA_DAT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean openGTA_DAT(){
        try {
            fileReader = new FileReader(fileName);
            input =  new BufferedReader(fileReader);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public boolean closeGTA_DAT(){
        try {
            input.close();
            fileReader.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public void save(){
        try {
            FileWriter fileWriter = null;
            BufferedWriter output = null;
            fileWriter = new FileWriter(fileName);
            output = new BufferedWriter(fileWriter);
            output.write("#gta.dat generated by Shadow-Mapper");
            output.newLine();
            output.newLine();
            output.write("#");
            output.newLine();
            output.write("# Imglist");
            output.newLine();
            output.write("#");
            output.newLine();
            output.newLine();
            for(int i = 0; i < imgList.size(); i++){
                output.write("IMGLIST " + imgList.get(i).replaceFirst("pc", "platform:").replaceFirst("common", "common:"));
                output.newLine();
            }

            output.newLine();
            output.write("#");
            output.newLine();
            output.write("# Water");
            output.newLine();
            output.write("#");
            output.newLine();
            output.newLine();
            for(int i = 0; i < water.size(); i++){
                output.write("WATER " + water.get(i).replaceFirst("pc", "platform:").replaceFirst("common", "common:"));
                output.newLine();
            }

            output.newLine();
            output.write("#");
            output.newLine();
            output.write("# Object types");
            output.newLine();
            output.write("#");
            output.newLine();
            output.newLine();
            for(int i = 0; i < ide.size(); i++){
                output.write("IDE " + ide.get(i).replaceFirst("pc", "platform:").replaceFirst("common", "common:"));
                output.newLine();
            }

            output.newLine();
            output.write("#");
            output.newLine();
            output.write("# IPL");
            output.newLine();
            output.write("#");
            output.newLine();
            output.newLine();
            for(int i = 0; i < ipl.size(); i++){
                output.write("IPL " + ipl.get(i).replaceFirst("pc", "platform:").replaceFirst("common", "common:").replace("wpl", "ipl"));
                output.newLine();
            }

            output.flush();
            output.close();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Errord " + ex);
        }
    }


}
