package nl.shadowlink.tools.shadowlib.ipl;

import com.nikhaldimann.inieditor.IniEditor;
import nl.shadowlink.tools.io.ReadFunctions;
import nl.shadowlink.tools.io.Vector3D;
import nl.shadowlink.tools.io.WriteFunctions;

/**
 * @author Shadow-Link
 */
public class Item_GRGE extends IPL_Item {
    private int gameType;

    public Vector3D lowLeftPos;
    public float lineX, lineY;
    public Vector3D topRightPos;
    public int doorType;
    public int garageType;
    public int hash;
    public String name;
    public int unknown;

    Item_GRGE(int gameType) {
        this.gameType = gameType;
    }

    @Override
    public void read(String line) {
        // Message.displayMsgHigh(line);
    }

    @Override
    public void read(ReadFunctions rf) {

    }

    @Override
    public void read(ReadFunctions rf, IniEditor ini) {
        lowLeftPos = rf.readVector3D();
        lineX = rf.readFloat();
        lineY = rf.readFloat();
        topRightPos = rf.readVector3D();
        doorType = rf.readInt();
        garageType = rf.readInt();
        long tempHash = rf.readUnsignedInt();
        name = "" + tempHash;
        hash = (int) tempHash;
        name = ini.get("Hashes", name); // temp
        unknown = rf.readInt();
        // display();
    }

    private void display() {
        System.out.println("LowLeftPos: " + lowLeftPos.x + ", " + lowLeftPos.y + ", " + lowLeftPos.z);
        System.out.println("Line x: " + lineX + " y: " + lineY);
        System.out.println("TopRightPos: " + topRightPos.x + ", " + topRightPos.y + ", " + topRightPos.z);
        System.out.println("Doortype: " + doorType);
        System.out.println("Garagetype: " + garageType);
        System.out.println("Hash: " + hash + " name: " + name);
        System.out.println("Unknown: " + unknown);
    }

    public void write(WriteFunctions wf) {
        wf.write(lowLeftPos);
        wf.write(lineX);
        wf.write(lineY);
        wf.write(topRightPos);
        wf.write(doorType);
        wf.write(garageType);
        wf.write(hash);
        wf.write(unknown);
        display();
    }

}