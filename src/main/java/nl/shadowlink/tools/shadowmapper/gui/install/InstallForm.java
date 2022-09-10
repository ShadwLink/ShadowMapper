

/*
 * Select.java
 *
 * Created on 14-aug-2009, 18:17:11
 */

package nl.shadowlink.tools.shadowmapper.gui.install;

import kotlin.Unit;
import nl.shadowlink.tools.shadowlib.utils.GameType;
import nl.shadowlink.tools.shadowlib.utils.filechooser.FileChooserUtil;
import nl.shadowlink.tools.shadowlib.utils.filechooser.FileNameFilter;
import nl.shadowlink.tools.shadowmapper.utils.GuiFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.Set;

import static nl.shadowlink.tools.shadowmapper.utils.GuiFunctions.setLookAndFeel;

/**
 * @author Shadow-Link
 */
public class InstallForm extends JFrame {

    private static final Set<String> SUPPORTED_EXES = Set.of(GameType.GTA_IV.getExecutableName());

    private final InstallRepository installRepository = new InstallRepository(new IniInstallStorage());
    private Install selectedInstall;

    /**
     * Creates new form Select
     */
    public InstallForm(OnInstallSelectedListener listener) {
        this.setIconImage(Toolkit.getDefaultToolkit().createImage("icon.png"));
        setLookAndFeel();
        initComponents(listener);
        GuiFunctions.centerWindow$Shadow_Mapper(this);

        installRepository.observeInstalls(installs -> {
            fillGameList(installs);
            return Unit.INSTANCE;
        });
    }

    private void fillGameList(List<Install> installs) {
        listGames.removeAll();
        installs.forEach(install -> listGames.add(install.getName()));
    }

    private void initComponents(OnInstallSelectedListener onInstallSelectedListener) {

        listGames = new java.awt.List();
        buttonOK = new JButton();
        buttonAddInstall = new JButton();
        image = new JLabel();
        buttonRemove = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select install");
        setResizable(false);

        listGames.addItemListener(this::listGamesItemStateChanged);

        buttonOK.setText("Select");
        buttonOK.setEnabled(false);
        buttonOK.addActionListener(e -> selectInstallButtonPressed(onInstallSelectedListener));

        buttonAddInstall.setText("Add Install");
        buttonAddInstall.addActionListener(this::addInstallButtonPressed);

        image.setIcon(new ImageIcon(getClass().getResource("/Images/shadowmapper.png")));

        buttonRemove.addActionListener(this::removeInstallButtonPressed);
        buttonRemove.setText("Remove install");
        buttonRemove.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                        layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        layout.createSequentialGroup()
                                                                                .addComponent(buttonRemove)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(buttonAddInstall)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(buttonOK))
                                                                .addComponent(image))
                                                .addComponent(listGames, javax.swing.GroupLayout.DEFAULT_SIZE, 365,
                                                        Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addComponent(image)
                        .addGap(1, 1, 1)
                        .addComponent(listGames, javax.swing.GroupLayout.PREFERRED_SIZE, 196,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonAddInstall).addComponent(buttonOK).addComponent(buttonRemove))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }

    private void removeInstallButtonPressed(ActionEvent evt) {
        installRepository.removeInstall(selectedInstall);
        selectedInstall = null;
        buttonOK.setEnabled(false);
        buttonRemove.setEnabled(false);
    }

    private void selectInstallButtonPressed(OnInstallSelectedListener onInstallSelectedListener) {
        if (selectedInstall != null) {
            onInstallSelectedListener.onInstallSelected(selectedInstall);
        }
        this.dispose();
    }

    private void addInstallButtonPressed(ActionEvent evt) {
        File file = FileChooserUtil.openFileChooser(this, new FileNameFilter(SUPPORTED_EXES, "IV Install Folder"));
        if (file != null && file.exists() && file.isFile()) {
            String installName = JOptionPane.showInputDialog("Set the name of the install");
            installRepository.addInstall(installName, file.getParent() + "\\", GameType.GTA_IV);
        }
    }

    private void listGamesItemStateChanged(java.awt.event.ItemEvent evt) {
        if (listGames.getSelectedIndex() != -1) {
            selectedInstall = installRepository.getInstall(listGames.getSelectedIndex());
            buttonOK.setEnabled(true);
            buttonRemove.setEnabled(true);
        } else {
            buttonOK.setEnabled(false);
            buttonRemove.setEnabled(false);
        }
    }

    private JButton buttonOK;
    private JButton buttonRemove;
    private JButton buttonAddInstall;
    private JLabel image;
    private java.awt.List listGames;

    public interface OnInstallSelectedListener {
        void onInstallSelected(Install install);
    }
}
