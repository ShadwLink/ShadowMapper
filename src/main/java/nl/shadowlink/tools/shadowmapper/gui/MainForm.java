package nl.shadowlink.tools.shadowmapper.gui;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import kotlin.Unit;
import nl.shadowlink.tools.shadowlib.ipl.Item_CARS;
import nl.shadowlink.tools.shadowlib.ipl.Item_INST;
import nl.shadowlink.tools.shadowlib.utils.filechooser.ExtensionFilter;
import nl.shadowlink.tools.shadowlib.utils.filechooser.FileChooserUtil;
import nl.shadowlink.tools.shadowmapper.checklist.CheckListManager;
import nl.shadowlink.tools.shadowmapper.gui.about.About;
import nl.shadowlink.tools.shadowmapper.gui.install.InstallDialog;
import nl.shadowlink.tools.shadowmapper.render.GlListener;
import nl.shadowlink.tools.shadowmapper.utils.EncryptionKeyExtractor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Set;

import static java.awt.Toolkit.getDefaultToolkit;

/**
 * @author Shadow-Link
 */
public class MainForm extends javax.swing.JFrame {
    private final FPSAnimator animator;
    public GlListener glListener;

    CheckListManager checkList;

    private FileManager fm;

    /**
     * Creates new form Main
     */
    public MainForm() {
        this.fm = new FileManager();
        glListener = new GlListener(this, fm, fps -> {
            if (labelFPS != null) labelFPS.setText("FPS: " + fps);
            return Unit.INSTANCE;
        });

        this.setIconImage(getDefaultToolkit().createImage("icon.png"));

        initComponents();

        this.setExtendedState(Frame.MAXIMIZED_BOTH);

        animator = new FPSAnimator(gLCanvas1, 60, true);
        animator.start();

        glListener.setCanvasPosition(gLCanvas1.getLocation());
        this.setVisible(true);

        new InstallDialog(install -> {
            LoadingDialog loadingDialog = new LoadingDialog();
            EncryptionKeyExtractor keyExtractor = new EncryptionKeyExtractor();
            byte[] key = keyExtractor.getKey(install.getPath());
            if (key == null) {
                showError("Error loading install", "Unable to detect encryption key");
                dispose();
            }

            fm.startLoading(loadingDialog, install, key);
            return Unit.INSTANCE;
        }).setVisible(true);
    }

    private void showError(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    private void initComponents() {

        buttonGroup1 = new ButtonGroup();
        gLCanvas1 = new GLCanvas();
        listIDE = new JTabbedPane();
        panelRender = new JPanel();
        jButton1 = new JButton();
        jScrollPane1 = new JScrollPane();
        listScene = new JList();
        jPanel3 = new JPanel();
        checkCars = new JCheckBox();
        checkZones = new JCheckBox();
        checkWater = new JCheckBox();
        checkMap = new JCheckBox();
        jCheckBox1 = new JCheckBox();
        panelIPL = new JPanel();
        jScrollPane4 = new JScrollPane();
        listIPL = new JList();
        jButton9 = new JButton();
        jButton10 = new JButton();
        buttonNewIPLItem = new JButton();
        buttonEditIPLItem = new JButton();
        buttonDeleteIPLItem = new JButton();
        jScrollPane5 = new JScrollPane();
        listIPLItems = new JList();
        comboIPLType = new JComboBox();
        panelIDE = new JPanel();
        jScrollPane2 = new JScrollPane();
        idesJList = new JList();
        jButton3 = new JButton();
        jButton4 = new JButton();
        buttonNewIDEItem = new JButton();
        buttonDelIDEItem = new JButton();
        buttonEditIDEItem = new JButton();
        jScrollPane3 = new JScrollPane();
        listIDEItems = new JList();
        panelMapper = new JPanel();
        jPanel1 = new JPanel();
        labelModelName = new JLabel();
        labelTextureName = new JLabel();
        labelLodName = new JLabel();
        textTextureName = new JTextField();
        textLODName = new JTextField();
        textModelName = new JTextField();
        buttonSelectModel = new JButton();
        buttonSelectModel1 = new JButton();
        buttonSelectModel2 = new JButton();
        jPanel2 = new JPanel();
        labelPosition = new JLabel();
        labelRotation = new JLabel();
        spinnerRotX = new JSpinner();
        spinnerPosX = new JSpinner();
        spinnerPosY = new JSpinner();
        spinnerRotY = new JSpinner();
        spinnerPosZ = new JSpinner();
        spinnerRotZ = new JSpinner();
        jPanel4 = new JPanel();
        jButton6 = new JButton();
        jButton7 = new JButton();
        labelFPS = new JLabel();
        labelCameraPosition = new JLabel();
        textX = new JTextField();
        textY = new JTextField();
        textZ = new JTextField();
        jToggleButton1 = new JToggleButton();
        jToggleButton2 = new JToggleButton();
        jToggleButton3 = new JToggleButton();
        jButton2 = new JButton();
        mapCleanerButton = new JButton();
        mainMenuBar = new JMenuBar();
        fileMenu = new JMenu();
        saveInstallMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        editMenu = new JMenu();
        resourceBrowserMenuItem = new JMenuItem();
        hashishGenMenuItem = new JMenuItem();
        mapCleanerMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        aboutMenuItem = new JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Shadow Mapper");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        gLCanvas1.addGLEventListener(glListener);
        gLCanvas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                gLCanvas1MouseClicked(evt);
            }

            public void mousePressed(MouseEvent evt) {
                gLCanvas1MousePressed(evt);
            }
        });
        gLCanvas1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                gLCanvas1MouseDragged(evt);
            }
        });
        gLCanvas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gLCanvas1KeyPressed(evt);
            }
        });

        jButton1.setText("Render");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        listScene.setModel(fm.modelIPL);
        checkList = new CheckListManager(listScene);
        checkList.setFileManager(fm);
        listScene.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                listSceneMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listScene);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("What to render"));

        checkCars.setSelected(true);
        checkCars.setText("Cars");
        checkCars.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                checkCarsActionPerformed(evt);
            }
        });

        checkZones.setText("Zones");
        checkZones.setEnabled(false);

        checkWater.setSelected(true);
        checkWater.setText("Water");
        checkWater.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                checkWaterItemStateChanged(evt);
            }
        });

        checkMap.setSelected(true);
        checkMap.setText("Map");
        checkMap.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                checkMapItemStateChanged(evt);
            }
        });

        jCheckBox1.setText("Paths");
        jCheckBox1.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel3Layout
                                .createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        jPanel3Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                        jPanel3Layout.createSequentialGroup().addComponent(checkWater)
                                                                .addGap(18, 18, 18).addComponent(checkCars)
                                                                .addGap(18, 18, 18).addComponent(checkMap))
                                                .addGroup(
                                                        jPanel3Layout.createSequentialGroup().addComponent(checkZones)
                                                                .addGap(18, 18, 18).addComponent(jCheckBox1)))
                                .addContainerGap(81, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel3Layout
                                .createSequentialGroup()
                                .addGroup(
                                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(checkWater).addComponent(checkCars)
                                                .addComponent(checkMap))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(checkZones).addComponent(jCheckBox1))));

        javax.swing.GroupLayout panelRenderLayout = new javax.swing.GroupLayout(panelRender);
        panelRender.setLayout(panelRenderLayout);
        panelRenderLayout.setHorizontalGroup(panelRenderLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        panelRenderLayout.createSequentialGroup().addContainerGap().addComponent(jButton1)
                                .addContainerGap(205, Short.MAX_VALUE))
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE));
        panelRenderLayout.setVerticalGroup(panelRenderLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                panelRenderLayout
                        .createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton1)
                        .addContainerGap()));

        listIDE.addTab("Scene", panelRender);

        listIPL.setModel(fm.modelIPL);
        listIPL.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                listIPLValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(listIPL);

        jButton9.setText("New");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onAddIplClicked(evt);
            }
        });

        jButton10.setText("Delete");

        buttonNewIPLItem.setText("New");
        buttonNewIPLItem.setEnabled(false);
        buttonNewIPLItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonNewIPLItemActionPerformed(evt);
            }
        });

        buttonEditIPLItem.setText("Edit");
        buttonEditIPLItem.setEnabled(false);

        buttonDeleteIPLItem.setText("Delete");
        buttonDeleteIPLItem.setEnabled(false);
        buttonDeleteIPLItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonDeleteIPLItemActionPerformed(evt);
            }
        });

        listIPLItems.setModel(fm.modelIPLItems);
        listIPLItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                listIPLItemsMouseClicked(evt);
            }
        });
        listIPLItems.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                listIPLItemsValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(listIPLItems);

        comboIPLType.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Instances", "Garages", "Cars",
                "Cull", "Strbig", "LODCull", "Zone", "Blok"}));
        comboIPLType.setEnabled(false);
        comboIPLType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                comboIPLTypeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelIPLLayout = new javax.swing.GroupLayout(panelIPL);
        panelIPL.setLayout(panelIPLLayout);
        panelIPLLayout
                .setHorizontalGroup(panelIPLLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                panelIPLLayout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                panelIPLLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane4,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 262,
                                                                Short.MAX_VALUE)
                                                        .addComponent(jScrollPane5,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 262,
                                                                Short.MAX_VALUE)
                                                        .addGroup(
                                                                panelIPLLayout
                                                                        .createSequentialGroup()
                                                                        .addComponent(jButton9)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jButton10))
                                                        .addGroup(
                                                                panelIPLLayout
                                                                        .createSequentialGroup()
                                                                        .addComponent(buttonNewIPLItem,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                53,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(buttonEditIPLItem)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(buttonDeleteIPLItem)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(comboIPLType, 0, 73,
                                                                                Short.MAX_VALUE))).addContainerGap()));
        panelIPLLayout.setVerticalGroup(panelIPLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        panelIPLLayout
                                .createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        panelIPLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButton10).addComponent(jButton9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 278,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        panelIPLLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(comboIPLType, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonNewIPLItem).addComponent(buttonEditIPLItem)
                                                .addComponent(buttonDeleteIPLItem))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                .addContainerGap()));

        listIDE.addTab("IPL", panelIPL);

        idesJList.setModel(fm.modelIDE);
        idesJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(idesJList);

        jButton3.setText("New");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onAddIdeClicked(evt);
            }
        });

        jButton4.setText("Delete");

        buttonNewIDEItem.setText("New");
        buttonNewIDEItem.setEnabled(false);
        buttonNewIDEItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonNewIDEItemActionPerformed(evt);
            }
        });

        buttonDelIDEItem.setText("Delete");
        buttonDelIDEItem.setEnabled(false);
        buttonDelIDEItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonDelIDEItemActionPerformed(evt);
            }
        });

        buttonEditIDEItem.setText("Edit");
        buttonEditIDEItem.setEnabled(false);
        buttonEditIDEItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonEditIDEItemActionPerformed(evt);
            }
        });

        listIDEItems.setModel(fm.modelIDEItems);
        listIDEItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                listIDEItemsMouseClicked(evt);
            }
        });
        listIDEItems.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                listIDEItemsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(listIDEItems);

        javax.swing.GroupLayout panelIDELayout = new javax.swing.GroupLayout(panelIDE);
        panelIDE.setLayout(panelIDELayout);
        panelIDELayout
                .setHorizontalGroup(panelIDELayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                panelIDELayout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                panelIDELayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jScrollPane2,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 262,
                                                                Short.MAX_VALUE)
                                                        .addComponent(jScrollPane3,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 262,
                                                                Short.MAX_VALUE)
                                                        .addGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                panelIDELayout
                                                                        .createSequentialGroup()
                                                                        .addComponent(jButton3)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jButton4))
                                                        .addGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                panelIDELayout
                                                                        .createSequentialGroup()
                                                                        .addComponent(buttonNewIDEItem)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(buttonEditIDEItem)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(buttonDelIDEItem)))
                                        .addContainerGap()));
        panelIDELayout.setVerticalGroup(panelIDELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        panelIDELayout
                                .createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        panelIDELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButton3).addComponent(jButton4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(
                                        panelIDELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(buttonNewIDEItem).addComponent(buttonEditIDEItem)
                                                .addComponent(buttonDelIDEItem))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                .addContainerGap()));

        listIDE.addTab("IDE", panelIDE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameters"));

        labelModelName.setText("Model: ");

        labelTextureName.setText("Texture Dictionary:");

        labelLodName.setText("LOD Name: ");

        textTextureName.setEditable(false);

        textLODName.setEditable(false);

        textModelName.setEditable(false);

        buttonSelectModel.setText("...");
        buttonSelectModel.setEnabled(false);

        buttonSelectModel1.setText("...");
        buttonSelectModel1.setEnabled(false);

        buttonSelectModel2.setText("...");
        buttonSelectModel2.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel1Layout
                                .createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(labelTextureName).addComponent(labelLodName)
                                                .addComponent(labelModelName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(textModelName, javax.swing.GroupLayout.DEFAULT_SIZE, 109,
                                                        Short.MAX_VALUE).addComponent(textTextureName)
                                                .addComponent(textLODName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(buttonSelectModel1,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonSelectModel,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonSelectModel2,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 26,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(11, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel1Layout
                                .createSequentialGroup()
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(labelModelName)
                                                .addComponent(buttonSelectModel)
                                                .addComponent(textModelName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(labelTextureName)
                                                .addComponent(textTextureName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonSelectModel1))
                                .addGap(9, 9, 9)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(labelLodName)
                                                .addComponent(textLODName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(buttonSelectModel2))));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Object Placement"));

        labelPosition.setText("Position");

        labelRotation.setText("Rotation");

        spinnerRotX.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
        spinnerRotX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerRotXStateChanged(evt);
            }
        });

        spinnerPosX.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(0.1f)));
        spinnerPosX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerPosXStateChanged(evt);
            }
        });

        spinnerPosY.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
        spinnerPosY.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerPosYStateChanged(evt);
            }
        });

        spinnerRotY.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
        spinnerRotY.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerRotYStateChanged(evt);
            }
        });

        spinnerPosZ.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
        spinnerPosZ.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerPosZStateChanged(evt);
            }
        });

        spinnerRotZ.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), null, null, Float.valueOf(1.0f)));
        spinnerRotZ.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerRotZStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel2Layout
                                .createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(labelPosition).addComponent(labelRotation))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel2Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(spinnerRotX, javax.swing.GroupLayout.Alignment.TRAILING,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(spinnerPosX, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel2Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(spinnerRotY, javax.swing.GroupLayout.Alignment.TRAILING,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(spinnerPosY, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel2Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(spinnerRotZ, javax.swing.GroupLayout.Alignment.TRAILING,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(spinnerPosZ, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));
        jPanel2Layout
                .setVerticalGroup(jPanel2Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                jPanel2Layout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(
                                                                jPanel2Layout
                                                                        .createSequentialGroup()
                                                                        .addComponent(spinnerPosZ,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(spinnerRotZ,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(
                                                                jPanel2Layout
                                                                        .createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(
                                                                                jPanel2Layout
                                                                                        .createSequentialGroup()
                                                                                        .addGroup(
                                                                                                jPanel2Layout
                                                                                                        .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                        .addComponent(
                                                                                                                labelPosition)
                                                                                                        .addComponent(
                                                                                                                spinnerPosX,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addGroup(
                                                                                                jPanel2Layout
                                                                                                        .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                        .addComponent(
                                                                                                                labelRotation)
                                                                                                        .addComponent(
                                                                                                                spinnerRotX,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                        .addGroup(
                                                                                jPanel2Layout
                                                                                        .createSequentialGroup()
                                                                                        .addComponent(
                                                                                                spinnerPosY,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addComponent(
                                                                                                spinnerRotY,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addContainerGap(6, Short.MAX_VALUE)));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Debug"));

        jButton6.setText("Convert IPL");
        jButton6.setEnabled(false);

        jButton7.setText("Convert IDE");
        jButton7.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel4Layout
                                .createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        jPanel4Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jButton6)
                                                .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(150, Short.MAX_VALUE)));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel4Layout.createSequentialGroup().addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)));

        javax.swing.GroupLayout panelMapperLayout = new javax.swing.GroupLayout(panelMapper);
        panelMapper.setLayout(panelMapperLayout);
        panelMapperLayout.setHorizontalGroup(panelMapperLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panelMapperLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                panelMapperLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(
                                                panelMapperLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGap(2, 2, 2))
                                        .addGroup(
                                                panelMapperLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addContainerGap())
                                        .addGroup(
                                                panelMapperLayout
                                                        .createSequentialGroup()
                                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addContainerGap()))));
        panelMapperLayout.setVerticalGroup(panelMapperLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panelMapperLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(136, 136, 136)));

        listIDE.addTab("Properties", panelMapper);

        labelFPS.setText("FPS: 0");

        labelCameraPosition.setText("DirectPosition");

        textX.setText("0.0000");
        textX.setAutoscrolls(false);
        textX.setEnabled(false);
        textX.setMaximumSize(new java.awt.Dimension(6, 20));
        textX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textXKeyTyped(evt);
            }
        });

        textY.setText("0.0000");
        textY.setEnabled(false);

        textZ.setText("0.0000");
        textZ.setEnabled(false);

        buttonGroup1.add(jToggleButton1);
        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/shaded.png"))); // NOI18N
        jToggleButton1.setSelected(true);
        jToggleButton1.setToolTipText("Shaded");
        jToggleButton1.setBorderPainted(false);
        jToggleButton1.setFocusable(false);
        jToggleButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/shadedSel.png"))); // NOI18N

        buttonGroup1.add(jToggleButton2);
        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wireframe.png"))); // NOI18N
        jToggleButton2.setToolTipText("Wireframe");
        jToggleButton2.setFocusable(false);
        jToggleButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wireframeSel.png"))); // NOI18N
        jToggleButton2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                jToggleButton2ItemStateChanged(evt);
            }
        });

        buttonGroup1.add(jToggleButton3);
        jToggleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/wireframeshaded.png"))); // NOI18N
        jToggleButton3.setFocusable(false);
        jToggleButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource(
                "/Images/wireframeshadedSel.png"))); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/browser.png"))); // NOI18N
        jButton2.setToolTipText("Resource Browser");
        jButton2.setBorder(null);
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onOpenResourceBrowserClicked(evt);
            }
        });

        mapCleanerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mapcleaner.png"))); // NOI18N
        mapCleanerButton.setToolTipText("Map Cleaner");
        mapCleanerButton.setBorder(null);
        mapCleanerButton.setEnabled(false);
        mapCleanerButton.setFocusable(false);
        fileMenu.setText("File");

        saveInstallMenuItem.setText("Save install");
        saveInstallMenuItem.setEnabled(false);
        saveInstallMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onSaveClicked(evt);
            }
        });
        fileMenu.add(saveInstallMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onCloseClicked(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mainMenuBar.add(fileMenu);

        editMenu.setText("Edit");

        resourceBrowserMenuItem.setText("Resource Browser");
        resourceBrowserMenuItem.addActionListener(evt -> onResourceBrowserClicked(evt));
        editMenu.add(resourceBrowserMenuItem);

        hashishGenMenuItem.setText("HashishGen");
        hashishGenMenuItem.addActionListener(evt -> onHashishGenClicked(evt));
        editMenu.add(hashishGenMenuItem);

        mapCleanerMenuItem.setText("Map Cleaner");
        mapCleanerMenuItem.setEnabled(false);
        editMenu.add(mapCleanerMenuItem);

        mainMenuBar.add(editMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        layout.createSequentialGroup()
                                .addGroup(
                                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jButton2)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(mapCleanerButton)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jToggleButton2,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jToggleButton3,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jToggleButton1,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(
                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                        layout.createSequentialGroup()
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(
                                                                                        gLCanvas1,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        715, Short.MAX_VALUE)
                                                                                .addGroup(
                                                                                        layout.createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addComponent(labelFPS)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(
                                                                                                        labelCameraPosition)
                                                                                                .addGap(8, 8, 8)
                                                                                                .addComponent(
                                                                                                        textX,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        44,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(
                                                                                                        textY,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        44,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(
                                                                                                        textZ,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                        42,
                                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(10, 10, 10)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(listIDE, javax.swing.GroupLayout.PREFERRED_SIZE, 287,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jToggleButton2, 0, 0, Short.MAX_VALUE)
                                        .addComponent(jToggleButton3, 0, 0, Short.MAX_VALUE)
                                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                Short.MAX_VALUE)
                                        .addComponent(mapCleanerButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(listIDE, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                                        .addComponent(gLCanvas1, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelFPS)
                                        .addComponent(labelCameraPosition)
                                        .addComponent(textZ, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textY, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textX, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
        String[] thanks = {"Aru ", "DeXx  ", "JostVice", "Johnline", "OinkOink", "Paroxum  ", "REspawn  ",
                "supermortalhuman ", "Tim  ", "", "Everyone I forgot"};
        new About("Shadow Mapper", "Beta 0.1a", "31-12-2009", thanks);
    }// GEN-LAST:event_jMenuItem1ActionPerformed

    private void gLCanvas1KeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_gLCanvas1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            switch (fm.selType) {
                case PickingType.map:
                    if (fm.selParam1 != -1 && fm.selParam2 != -1) {
                        fm.ipls.get(fm.selParam1).items_inst.remove(fm.selParam2);
                        fm.ipls.get(fm.selParam1).changed = true;
                        fm.updateIPLItemList(fm.selParam1, fm.selParam1);
                    }
                    break;
                default:
                    System.out.println("Nothing to delete");
            }
        }
        glListener.keyPressed(evt);
    }// GEN-LAST:event_gLCanvas1KeyPressed

    private void gLCanvas1MouseDragged(MouseEvent evt) {// GEN-FIRST:event_gLCanvas1MouseDragged
        glListener.mouseMoved(evt);
    }// GEN-LAST:event_gLCanvas1MouseDragged

    private void jButton1ActionPerformed(ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        glListener.reloadMap();
    }// GEN-LAST:event_jButton1ActionPerformed

    private void gLCanvas1MouseClicked(MouseEvent evt) {// GEN-FIRST:event_gLCanvas1MouseClicked
        if (evt.getClickCount() == 2) {
            glListener.setPick();
        }
    }// GEN-LAST:event_gLCanvas1MouseClicked

    private void gLCanvas1MousePressed(MouseEvent evt) {// GEN-FIRST:event_gLCanvas1MousePressed
        System.out.println("Set pos " + evt.getX() + ", " + evt.getY());
        glListener.setCurrentMousePos(evt.getPoint());
    }// GEN-LAST:event_gLCanvas1MousePressed

    private void jToggleButton2ItemStateChanged(ItemEvent evt) {// GEN-FIRST:event_jToggleButton2ItemStateChanged
        glListener.setWireFrame(jToggleButton2.isSelected());
    }// GEN-LAST:event_jToggleButton2ItemStateChanged

    private void spinnerPosXStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_spinnerPosXStateChanged
        switch (fm.selType) {
            case PickingType.map:
                fm.ipls.get(fm.selParam1).items_inst.get(fm.selParam2).position.x = (Float) spinnerPosX.getValue();
                fm.ipls.get(fm.selParam1).changed = true;
                break;
        }
    }// GEN-LAST:event_spinnerPosXStateChanged

    private void spinnerPosYStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_spinnerPosYStateChanged
        switch (fm.selType) {
            case PickingType.map:
                fm.ipls.get(fm.selParam1).items_inst.get(fm.selParam2).position.y = (Float) spinnerPosY.getValue();
                fm.ipls.get(fm.selParam1).changed = true;
                break;
        }
    }// GEN-LAST:event_spinnerPosYStateChanged

    private void spinnerPosZStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_spinnerPosZStateChanged
        switch (fm.selType) {
            case PickingType.map:
                fm.ipls.get(fm.selParam1).items_inst.get(fm.selParam2).position.z = (Float) spinnerPosZ.getValue();
                fm.ipls.get(fm.selParam1).changed = true;
                break;
        }
    }// GEN-LAST:event_spinnerPosZStateChanged

    private void textXKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_textXKeyTyped
        glListener.camera.pos.x = Float.valueOf(textX.getText());
    }// GEN-LAST:event_textXKeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
        new SaveScreen(fm, true);
    }// GEN-LAST:event_formWindowClosing

    private void checkMapItemStateChanged(ItemEvent evt) {// GEN-FIRST:event_checkMapItemStateChanged
        glListener.displayMap = checkMap.isSelected();
    }// GEN-LAST:event_checkMapItemStateChanged

    private void checkWaterItemStateChanged(ItemEvent evt) {
        glListener.displayWater = checkWater.isSelected();
    }// GEN-LAST:event_checkWaterItemStateChanged

    private void listIPLValueChanged(ListSelectionEvent evt) {
        fm.updateIPLItemList(listIPL.getSelectedIndex(), comboIPLType.getSelectedIndex());
        if (listIPL.getSelectedIndex() != -1) {
            buttonNewIPLItem.setEnabled(true);
            comboIPLType.setEnabled(true);
        } else {
            buttonNewIPLItem.setEnabled(false);
            comboIPLType.setEnabled(false);
        }
    }

    private void comboIPLTypeItemStateChanged(ItemEvent evt) {
        fm.updateIPLItemList(listIPL.getSelectedIndex(), comboIPLType.getSelectedIndex());
    }

    private void buttonDeleteIPLItemActionPerformed(ActionEvent evt) {
        switch (comboIPLType.getSelectedIndex()) {
            case 0:
                fm.ipls.get(listIPL.getSelectedIndex()).items_inst.remove(listIPLItems.getSelectedIndex());
                break;
            case 2:
                fm.ipls.get(listIPL.getSelectedIndex()).items_cars.remove(listIPLItems.getSelectedIndex());
                break;
        }
        fm.ipls.get(listIPL.getSelectedIndex()).changed = true;
        fm.updateIPLItemList(listIPL.getSelectedIndex(), comboIPLType.getSelectedIndex());
    }

    private void jList2ValueChanged(ListSelectionEvent evt) {
        fm.updateIDEItemList(idesJList.getSelectedIndex(), 0);
        buttonNewIDEItem.setEnabled(true);
        buttonDelIDEItem.setEnabled(true);
    }

    private void onResourceBrowserClicked(ActionEvent evt) {
        Browser browser = new Browser(fm);
    }

    private void onHashishGenClicked(ActionEvent evt) {
        new HashishGen();
    }

    private void listIPLItemsMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            switch (comboIPLType.getSelectedIndex()) {
                case 0:
                    fm.setSelection(PickingType.map, listIPL.getSelectedIndex(), listIPLItems.getSelectedIndex());
                    break;
                case 2:
                    fm.setSelection(PickingType.car, listIPL.getSelectedIndex(), listIPLItems.getSelectedIndex());
                    break;
            }
            selectionChanged();
        }
    }

    private void listIDEItemsMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            new IDEForm(idesJList.getSelectedIndex(), listIDEItems.getSelectedIndex(), fm);
        }
    }

    private void buttonEditIDEItemActionPerformed(ActionEvent evt) {
        new IDEForm(idesJList.getSelectedIndex(), listIDEItems.getSelectedIndex(), fm);
    }

    private void listIDEItemsValueChanged(ListSelectionEvent evt) {
        buttonEditIDEItem.setEnabled(listIDEItems.getSelectedIndex() != -1);
    }

    private void buttonNewIDEItemActionPerformed(ActionEvent evt) {
        new IDEForm(idesJList.getSelectedIndex(), fm);
    }

    private void listSceneMouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            Item_INST instance = fm.ipls.get(listScene.getSelectedIndex()).items_inst.get(0);
            glListener.camera.pos.x = instance.position.x;
            glListener.camera.pos.z = 0 - instance.position.y;
            glListener.camera.pos.y = instance.position.z;
            glListener.camera.view.x = instance.position.x;
            glListener.camera.view.z = 0 - instance.position.y + 5.0f;
            glListener.camera.view.y = instance.position.z - 0.5f;
            glListener.camera.up.x = 0;
            glListener.camera.up.z = 0;
            glListener.camera.up.y = 1;
        }
    }

    private void buttonNewIPLItemActionPerformed(ActionEvent evt) {
        switch (comboIPLType.getSelectedIndex()) {
            case 0:
                new IPLForm(fm, listIPL.getSelectedIndex(), glListener.camera.view);
                // selIPL = listIPL.getSelectedIndex();
                // selItem = listIPLItems.getSelectedIndex();
                break;
            case 2:
                new CarForm(fm, listIPL.getSelectedIndex(), glListener.camera.view);
                break;
        }
    }

    private void buttonDelIDEItemActionPerformed(ActionEvent evt) {
        fm.ides.get(idesJList.getSelectedIndex()).items_objs.remove(listIDEItems.getSelectedIndex());
        fm.ides.get(idesJList.getSelectedIndex()).changed = true;
        fm.updateIDEItemList(idesJList.getSelectedIndex(), 0);
    }

    private void onAddIdeClicked(ActionEvent evt) {
        File file = FileChooserUtil.openFileChooser(this, new ExtensionFilter(Set.of("ide"), "IDE File"));
        fm.addNewIDE(file);
    }

    private void onCloseClicked(ActionEvent evt) {
        this.dispose();
    }

    private void onAddIplClicked(ActionEvent evt) {
        File file = FileChooserUtil.openFileChooser(this, new ExtensionFilter(Set.of("wpl"), "WPL File"));
        fm.addNewIPL(file);
    }

    private void onSaveClicked(ActionEvent evt) {
        new SaveScreen(fm, false);
    }

    private void checkCarsActionPerformed(ActionEvent evt) {
        glListener.displayCars = checkCars.isSelected();
    }

    private void listIPLItemsValueChanged(ListSelectionEvent evt) {
        if (listIPLItems.getSelectedIndex() != -1) {
            buttonEditIPLItem.setEnabled(true);
            buttonDeleteIPLItem.setEnabled(true);
        } else {
            buttonEditIPLItem.setEnabled(false);
            buttonDeleteIPLItem.setEnabled(false);
        }
    }

    private void spinnerRotXStateChanged(javax.swing.event.ChangeEvent evt) {
        switch (fm.selType) {
            case PickingType.map:
                fm.ipls.get(fm.selParam1).items_inst.get(fm.selParam2).axisAngle.x = (Float) spinnerRotX.getValue();
                fm.ipls.get(fm.selParam1).changed = true;
                break;
        }
    }

    private void spinnerRotYStateChanged(javax.swing.event.ChangeEvent evt) {
        switch (fm.selType) {
            case PickingType.map:
                fm.ipls.get(fm.selParam1).items_inst.get(fm.selParam2).axisAngle.y = (Float) spinnerRotY.getValue();
                fm.ipls.get(fm.selParam1).changed = true;
                break;
        }
    }

    private void spinnerRotZStateChanged(javax.swing.event.ChangeEvent evt) {
        switch (fm.selType) {
            case PickingType.map:
                fm.ipls.get(fm.selParam1).items_inst.get(fm.selParam2).axisAngle.z = (Float) spinnerRotZ.getValue();
                fm.ipls.get(fm.selParam1).changed = true;
                break;
        }
    }

    private void onOpenResourceBrowserClicked(ActionEvent evt) {
        new Browser(fm);
    }

    public void selectionChanged() {
        switch (fm.selType) {
            case PickingType.map:
                Item_INST instance = fm.ipls.get(fm.selParam1).items_inst.get(fm.selParam2);
                textModelName.setText(instance.name);
                spinnerPosX.setValue(instance.position.x);
                spinnerPosY.setValue(instance.position.y);
                spinnerPosZ.setValue(instance.position.z);
                spinnerRotX.setValue(instance.axisAngle.x);
                spinnerRotY.setValue(instance.axisAngle.y);
                spinnerRotZ.setValue(instance.axisAngle.z);
                listIPL.setSelectedIndex(fm.selParam1);
                listIPLItems.setSelectedIndex(fm.selParam2);
                break;
            case PickingType.water:
                textModelName.setText("Water");
                break;
            case PickingType.car:
                Item_CARS car = fm.ipls.get(fm.selParam1).items_cars.get(fm.selParam2);
                textModelName.setText(car.name);
                spinnerPosX.setValue(car.position.x);
                spinnerPosY.setValue(car.position.y);
                spinnerPosZ.setValue(car.position.z);
                spinnerRotX.setValue(car.rotation.x);
                spinnerRotY.setValue(car.rotation.y);
                spinnerRotZ.setValue(car.rotation.z);
                break;
            default:
                textModelName.setText("Unknown");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton buttonDelIDEItem;
    private JButton buttonDeleteIPLItem;
    private JButton buttonEditIDEItem;
    private JButton buttonEditIPLItem;
    private ButtonGroup buttonGroup1;
    private JButton buttonNewIDEItem;
    private JButton buttonNewIPLItem;
    private JButton buttonSelectModel;
    private JButton buttonSelectModel1;
    private JButton buttonSelectModel2;
    private JCheckBox checkCars;
    private JCheckBox checkMap;
    private JCheckBox checkWater;
    private JCheckBox checkZones;
    private JComboBox comboIPLType;
    private GLCanvas gLCanvas1;
    private JButton jButton1;
    private JButton jButton10;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton mapCleanerButton;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton9;
    private JCheckBox jCheckBox1;
    private JList idesJList;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;
    private JMenuBar mainMenuBar;
    private JMenuItem aboutMenuItem;
    private JMenuItem resourceBrowserMenuItem;
    private JMenuItem hashishGenMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem saveInstallMenuItem;
    private JMenuItem mapCleanerMenuItem;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JToggleButton jToggleButton1;
    private JToggleButton jToggleButton2;
    private JToggleButton jToggleButton3;
    private JLabel labelCameraPosition;
    private JLabel labelFPS;
    private JLabel labelLodName;
    private JLabel labelModelName;
    private JLabel labelPosition;
    private JLabel labelRotation;
    private JLabel labelTextureName;
    private JTabbedPane listIDE;
    private JList listIDEItems;
    private JList listIPL;
    private JList listIPLItems;
    private JList listScene;
    private JPanel panelIDE;
    private JPanel panelIPL;
    private JPanel panelMapper;
    private JPanel panelRender;
    private JSpinner spinnerPosX;
    private JSpinner spinnerPosY;
    private JSpinner spinnerPosZ;
    private JSpinner spinnerRotX;
    private JSpinner spinnerRotY;
    private JSpinner spinnerRotZ;
    private JTextField textLODName;
    private JTextField textModelName;
    private JTextField textTextureName;
    private JTextField textX;
    private JTextField textY;
    private JTextField textZ;
    // End of variables declaration//GEN-END:variables

}
