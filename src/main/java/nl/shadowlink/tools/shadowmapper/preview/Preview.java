

/*
 * Preview.java
 *
 * Created on 14-dec-2009, 0:47:01
 */

package nl.shadowlink.tools.shadowmapper.preview;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import nl.shadowlink.tools.io.ByteReader;
import nl.shadowlink.tools.io.ReadFunctions;
import nl.shadowlink.tools.shadowlib.img.IMG_Item;
import nl.shadowlink.tools.shadowlib.texturedic.TextureDic;
import nl.shadowlink.tools.shadowlib.utils.GameType;
import nl.shadowlink.tools.shadowmapper.gui.FileManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Shadow-Link
 */
public class Preview extends javax.swing.JFrame {
    private FileManager fm;
    private int imgID;
    private int itemID;
    private Animator animator;
    public glListener glListener = new glListener();
    private DefaultListModel list = new DefaultListModel();

    /**
     * Creates new form Preview
     */
    public Preview(FileManager fm, int imgID, int itemID) {
        this.fm = fm;
        this.imgID = imgID;
        this.itemID = itemID;

        this.setIconImage(java.awt.Toolkit.getDefaultToolkit().createImage("icon.png"));

        initComponents();
        this.setVisible(true);

        //center the window
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

        animator = new Animator(gLCanvas1);
        animator.start();

        System.out.println("Canvas Location: " + gLCanvas1.getX() + ", " + gLCanvas1.getLocation().y);

        glListener.setCanvasPosition(gLCanvas1.getLocation());

        init();
    }

    private void init() {
        System.out.println("IMGID: " + imgID + " itemID: " + itemID);
        IMG_Item item = fm.imgs[imgID].getItems().get(itemID);
        ReadFunctions rf = new ReadFunctions(fm.imgs[imgID].getFileName());
        rf.seek(item.getOffset());
        if (item.getName().toLowerCase().endsWith(".wdr")) {
            list.addElement(item.getName());
            glListener.type = 0;
        } else if (item.getName().toLowerCase().endsWith(".wft")) {
            list.addElement(item.getName());
            glListener.type = 1;
        } else if (item.getName().toLowerCase().endsWith(".wtd")) {
            ByteReader br = rf.getByteReader(item.getSize());
            rf.seek(item.getOffset());
            // TODO: Something changed here, what?
            TextureDic txd = new TextureDic("", br, GameType.GTA_IV, false, item.getSize());
            for (int i = 0; i < txd.texName.length; i++) {
                list.addElement(txd.texName[i]);
            }
            glListener.type = 3;
        } else if (item.getName().toLowerCase().endsWith(".wbd")) {
            ByteReader br = rf.getByteReader(item.getSize());
            rf.seek(item.getOffset());
            // TODO: What happened to WBD files?
//            WBDFile wbd = new WBDFile(br);
//            for (int i = 0; i < wbd.bounds.hashCount; i++) {
//                list.addElement(wbd.bounds.hashes.Values.get(i) + "(" + wbd.bounds.phBounds._items.get(i).type + ")");
//            }
//            wbd = null;
            glListener.type = 4;
        } else if (item.getName().toLowerCase().endsWith(".wbn")) {
            rf.getByteReader(item.getSize());
            rf.seek(item.getOffset());
            glListener.type = 5;
        } else {
            JOptionPane.showMessageDialog(this, "Only WDR, WFT, WBD and WTD files are supported at this moment", "Unable to preview file", JOptionPane.ERROR_MESSAGE);
        }
            /*else if(item.getName().toLowerCase().endsWith(".wdd")){
            list.addElement(item.getName());
            glListener.type = 2;
        }*/
        glListener.br = rf.getByteReader(item.getSize());
        glListener.size = item.getSize();
        glListener.load = true;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gLCanvas1 = new GLCanvas();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Shadow Mapper - Preview");
        setResizable(false);

        gLCanvas1.addGLEventListener(glListener);
        gLCanvas1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                gLCanvas1MouseWheelMoved(evt);
            }
        });
        gLCanvas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                gLCanvas1MousePressed(evt);
            }
        });
        gLCanvas1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                gLCanvas1MouseDragged(evt);
            }

            public void mouseMoved(java.awt.event.MouseEvent evt) {
                gLCanvas1MouseMoved(evt);
            }
        });
        gLCanvas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gLCanvas1KeyPressed(evt);
            }
        });

        jList1.setModel(list);
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gLCanvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                        .addComponent(gLCanvas1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gLCanvas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gLCanvas1KeyPressed
        glListener.keyPressed(evt);
    }//GEN-LAST:event_gLCanvas1KeyPressed

    private void gLCanvas1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gLCanvas1MouseMoved

    }//GEN-LAST:event_gLCanvas1MouseMoved

    private void gLCanvas1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gLCanvas1MouseDragged
        glListener.mouseMoved(evt);
    }//GEN-LAST:event_gLCanvas1MouseDragged

    private void gLCanvas1MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_gLCanvas1MouseWheelMoved
        glListener.mouseWheelMoved(evt);
    }//GEN-LAST:event_gLCanvas1MouseWheelMoved

    private void gLCanvas1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gLCanvas1MousePressed
        glListener.mousePressed(evt);
    }//GEN-LAST:event_gLCanvas1MousePressed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if (jList1.getSelectedIndex() != -1) glListener.setSelected(jList1.getSelectedIndex());
    }//GEN-LAST:event_jList1ValueChanged

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (evt.getClickCount() == 2) {
//            if (glListener.wbd != null) {
//                JOptionPane.showMessageDialog(this, glListener.wbd.bounds.phBounds._items.get(jList1.getSelectedIndex()).phBvh.info);
//            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private GLCanvas gLCanvas1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
