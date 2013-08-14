/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LoadingBar.java
 *
 * Created on 16-dec-2009, 11:33:03
 */

package shadowmapper;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;

/**
 *
 * @author Kilian
 */
public class TestLoadBar extends javax.swing.JFrame implements Runnable{

    /** Creates new form LoadingBar */
    public TestLoadBar() {
        this.setIconImage(java.awt.Toolkit.getDefaultToolkit().createImage("icon.png"));
        initComponents();
        this.setVisible(true);

        //center the window
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }

    public void setLoadingBarMax(int max){
        BarLoading.setMinimum(0);
        BarLoading.setMaximum(max);
    }

    public void setLoadingBarValue(final int value){
        SwingUtilities.invokeLater(
	new Runnable() {
            public void run() {
                BarLoading.setValue(value);
            }
	});
    }

    public void setLabelText(final String text){
        SwingUtilities.invokeLater(
	new Runnable() {
            public void run() {
                labelLoading.setText(text);
            }
	});
    }

    public void addOneToLoadingBar(){
        BarLoading.setValue(BarLoading.getValue()+1);
    }

    public void setFinished(){
        this.dispose();
        //new Main(fm);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelLoading = new javax.swing.JLabel();
        BarLoading = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Loading");
        setResizable(false);

        labelLoading.setText("Loading: ");

        BarLoading.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BarLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelLoading))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(labelLoading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BarLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar BarLoading;
    private javax.swing.JLabel labelLoading;
    // End of variables declaration//GEN-END:variables

    public void run() {
        int i = 0;
        while(true){
            setLabelText("hah"+i);
            this.repaint();
        }
    }

}
