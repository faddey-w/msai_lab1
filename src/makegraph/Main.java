/*
 * Decompiled with CFR 0_115.
 */
package makegraph;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import makegraph.AboutBox;
import makegraph.AnswerFactory;
import makegraph.AnswerModel;
import makegraph.Configuration;
import makegraph.DrawPanel;
import makegraph.Module;
import strings.Names;
import strings.Translatable;
import test1_0_2_0.AdditionalInfo;
import test1_0_2_0.Constants;
import test1_0_2_0.Movable;
import test1_0_2_0.Node;
import test1_0_2_0.Note;
import test1_0_2_0.Test;
import test1_0_2_0.TestObject;
import test1_0_2_0.TestScheme;
import test1_0_2_0.WeightedEdge;
import test1_0_2_0.WeightedNode;
import test1_0_2_0.WeightedObject;
import windows.VectorStringEdit;
import windows.VectorStringModel;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Main
extends JFrame {
    final int timeSpan = 500;
    TestObject selected = null;
    public Test test;
    File dest;
    boolean stopDemonstrate = false;
    boolean move = false;
    int answerNumber = 0;
    private ButtonGroup buttonGroupInstruments;
    private DrawPanel drawPanel;
    private JButton jButtonAbout;
    private JButton jButtonAddAnswer;
    private JButton jButtonAnswerOK;
    private JButton jButtonChangeNodeSize;
    private JButton jButtonDelete;
    private JButton jButtonDemonstrate;
    private JButton jButtonNew;
    private JButton jButtonOpen;
    private JButton jButtonRemoveAnswer;
    private JButton jButtonSave;
    private JButton jButtonSaveAs;
    private JButton jButtonWeights;
    private JLabel jLabelRightAnswer;
    private JLabel jLabelTask;
    private JList jListAnswers;
    private JPanel jPanelAdditionalInfo;
    private JPanel jPanelDemonstrate;
    private JPanel jPanelInstruments;
    private JPanel jPanelMain;
    private JPanel jPanelTest;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea jTextAreaTask;
    private JTextField jTextFieldRightAnswer;
    private JToggleButton jToggleButtonEdge;
    private JToggleButton jToggleButtonEnterAnswer;
    private JToggleButton jToggleButtonNode;
    private JToggleButton jToggleButtonNote;
    private JToggleButton jToggleButtonSelect;

    public Main() {
        this.initComponents();
        try {
            Configuration.load();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "<html>Configuration file " + Names.configFile + " is damaged. Fix it or delete to load defaults<br>" + ex.getMessage(), Translatable.error, 0);
            System.exit(1);
        }
        this.setTitle(Names.makeGraph);
        this.jLabelTask.setText(Translatable.task);
        this.jToggleButtonNode.setText(Translatable.node);
        this.jToggleButtonNote.setText(Translatable.note);
        this.jToggleButtonEdge.setText(Translatable.edge);
        this.jToggleButtonSelect.setText(Translatable.select);
        this.jButtonDelete.setText(Translatable.delete);
        this.jButtonChangeNodeSize.setText(Translatable.changeNodeSize);
        this.jToggleButtonEnterAnswer.setText(Translatable.enterAnswer);
        this.jLabelRightAnswer.setText(Translatable.answer);
        this.jButtonOpen.setText(Translatable.open);
        this.jButtonSaveAs.setText(Translatable.saveAs);
        this.jButtonSave.setText(Translatable.save);
        this.jButtonNew.setText(Translatable.new_);
        this.jButtonAnswerOK.setText(Translatable.answerOK);
        this.jPanelMain.setBorder(BorderFactory.createTitledBorder(Translatable.fileTitle));
        this.jPanelInstruments.setBorder(BorderFactory.createTitledBorder(Translatable.instrumentsTitle));
        this.jPanelAdditionalInfo.setBorder(BorderFactory.createTitledBorder(Translatable.answerTitle));
        this.jPanelDemonstrate.setBorder(BorderFactory.createTitledBorder(Translatable.demonstrateTitle));
        this.jButtonDemonstrate.setText(Translatable.demonstrateTitle);
        this.jButtonAbout.setText(Translatable.about);
        this.jButtonWeights.setText(Translatable.weights);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(3, 3, 3, 3);
        for (int i = 0; i < AnswerFactory.size(); ++i) {
            JButton but = new JButton(AnswerFactory.getName(i));
            final int index = i;
            but.addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e) {
                    Main.this.releaseOperation();
                    try {
                        Main.this.demonstrate(true, AnswerFactory.exec(index, Main.this.test, Main.this.answerNumber, Main.this));
                    }
                    catch (Module.Cancel ex) {
                    }
                    catch (Module.MessageReceived ex) {
                        JOptionPane.showMessageDialog(Main.this, ex.getMessage(), AnswerFactory.getInnerName(index), 1);
                    }
                    catch (Exception ex) {
                        JOptionPane.showMessageDialog(Main.this, ex.getMessage(), AnswerFactory.getInnerName(index), 0);
                    }
                }
            });
            gbc.gridy = i + 1;
            this.jPanelDemonstrate.add((Component)but, gbc);
        }
        this.setLocationRelativeTo(null);
        this.jButtonNewActionPerformed(null);
        this.pack();
        this.setExtendedState(6);
        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher(){

            public boolean dispatchKeyEvent(KeyEvent e) {
                if (Main.this.drawPanel.demonstrate) {
                    return false;
                }
                if (e.getID() == 401) {
                    if (e.isControlDown()) {
                        if (e.getKeyCode() == 78) {
                            e.consume();
                            Main.this.jButtonNewActionPerformed(null);
                        }
                        if (e.getKeyCode() == 79) {
                            e.consume();
                            Main.this.jButtonOpenActionPerformed(null);
                        }
                        if (e.getKeyCode() == 83) {
                            e.consume();
                            Main.this.jButtonSaveActionPerformed(null);
                        }
                    }
                    if (e.getKeyCode() == 127) {
                        e.consume();
                        Main.this.jButtonDeleteActionPerformed(null);
                    }
                    if (e.getKeyCode() == 10 && Main.this.jToggleButtonEnterAnswer.isSelected()) {
                        e.consume();
                        Main.this.jButtonAnswerOKActionPerformed(null);
                    }
                    if (Main.this.drawPanel.hasFocus()) {
                        int x = 0;
                        int y = 0;
                        if (e.getKeyCode() == 38) {
                            y = -1;
                        }
                        if (e.getKeyCode() == 40) {
                            y = 1;
                        }
                        if (e.getKeyCode() == 37) {
                            x = -1;
                        }
                        if (e.getKeyCode() == 39) {
                            x = 1;
                        }
                        if ((x != 0 || y != 0) && !Main.this.move && Main.this.selected instanceof Movable) {
                            e.consume();
                            int width = Main.this.drawPanel.getWidth();
                            int height = Main.this.drawPanel.getHeight();
                            ((Movable)((Object)Main.this.selected)).startMoving(-1, -1, width, height);
                            ((Movable)((Object)Main.this.selected)).moveTo(x - 1, y - 1);
                            Main.this.update();
                        }
                    }
                }
                return false;
            }
        });
        this.jListAnswers.setModel(new AnswerModel(this));
        this.jListAnswers.setSelectedIndex(0);
        this.jListAnswers.requestFocusInWindow();
    }

    private void initComponents() {
        this.buttonGroupInstruments = new ButtonGroup();
        this.jPanelTest = new JPanel();
        this.drawPanel = new DrawPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jTextAreaTask = new JTextArea();
        this.jLabelTask = new JLabel();
        this.jPanelInstruments = new JPanel();
        this.jToggleButtonNode = new JToggleButton();
        this.jToggleButtonNote = new JToggleButton();
        this.jToggleButtonEdge = new JToggleButton();
        this.jToggleButtonSelect = new JToggleButton();
        this.jButtonDelete = new JButton();
        this.jButtonChangeNodeSize = new JButton();
        this.jButtonWeights = new JButton();
        this.jPanelAdditionalInfo = new JPanel();
        this.jToggleButtonEnterAnswer = new JToggleButton();
        this.jLabelRightAnswer = new JLabel();
        this.jTextFieldRightAnswer = new JTextField();
        this.jButtonAnswerOK = new JButton();
        this.jScrollPane2 = new JScrollPane();
        this.jListAnswers = new JList();
        this.jButtonRemoveAnswer = new JButton();
        this.jButtonAddAnswer = new JButton();
        this.jPanelMain = new JPanel();
        this.jButtonOpen = new JButton();
        this.jButtonSaveAs = new JButton();
        this.jButtonSave = new JButton();
        this.jButtonNew = new JButton();
        this.jButtonAbout = new JButton();
        this.jPanelDemonstrate = new JPanel();
        this.jButtonDemonstrate = new JButton();
        this.setDefaultCloseOperation(3);
        this.addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent evt) {
                Main.this.formWindowClosing(evt);
            }
        });
        this.getContentPane().setLayout(new GridBagLayout());
        this.jPanelTest.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.jPanelTest.setLayout(new GridBagLayout());
        this.drawPanel.addMouseListener(new MouseAdapter(){

            public void mousePressed(MouseEvent evt) {
                Main.this.drawPanelMousePressed(evt);
            }

            public void mouseReleased(MouseEvent evt) {
                Main.this.drawPanelMouseReleased(evt);
            }
        });
        this.drawPanel.addMouseMotionListener(new MouseMotionAdapter(){

            public void mouseDragged(MouseEvent evt) {
                Main.this.drawPanelMouseDragged(evt);
            }
        });
        this.drawPanel.setLayout(new CardLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.jPanelTest.add((Component)this.drawPanel, gridBagConstraints);
        this.jTextAreaTask.setColumns(20);
        this.jTextAreaTask.setFont(new Font("Tahoma", 0, 11));
        this.jTextAreaTask.setRows(2);
        this.jScrollPane1.setViewportView(this.jTextAreaTask);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.jPanelTest.add((Component)this.jScrollPane1, gridBagConstraints);
        this.jLabelTask.setText("Task:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.jPanelTest.add((Component)this.jLabelTask, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        this.getContentPane().add((Component)this.jPanelTest, gridBagConstraints);
        this.jPanelInstruments.setBorder(BorderFactory.createTitledBorder("Instruments"));
        this.jPanelInstruments.setLayout(new GridBagLayout());
        this.buttonGroupInstruments.add(this.jToggleButtonNode);
        this.jToggleButtonNode.setSelected(true);
        this.jToggleButtonNode.setText("Node");
        this.jToggleButtonNode.setFocusable(false);
        this.jToggleButtonNode.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jToggleButtonNodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.anchor = 11;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelInstruments.add((Component)this.jToggleButtonNode, gridBagConstraints);
        this.buttonGroupInstruments.add(this.jToggleButtonNote);
        this.jToggleButtonNote.setText("Note");
        this.jToggleButtonNote.setFocusable(false);
        this.jToggleButtonNote.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jToggleButtonNoteActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 2;
        gridBagConstraints.anchor = 11;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelInstruments.add((Component)this.jToggleButtonNote, gridBagConstraints);
        this.buttonGroupInstruments.add(this.jToggleButtonEdge);
        this.jToggleButtonEdge.setText("Edge");
        this.jToggleButtonEdge.setFocusable(false);
        this.jToggleButtonEdge.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jToggleButtonEdgeActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 2;
        gridBagConstraints.anchor = 11;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelInstruments.add((Component)this.jToggleButtonEdge, gridBagConstraints);
        this.buttonGroupInstruments.add(this.jToggleButtonSelect);
        this.jToggleButtonSelect.setText("Select");
        this.jToggleButtonSelect.setFocusable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = 2;
        gridBagConstraints.anchor = 11;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelInstruments.add((Component)this.jToggleButtonSelect, gridBagConstraints);
        this.jButtonDelete.setText("Delete");
        this.jButtonDelete.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonDeleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelInstruments.add((Component)this.jButtonDelete, gridBagConstraints);
        this.jButtonChangeNodeSize.setText("Change node size...");
        this.jButtonChangeNodeSize.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonChangeNodeSizeActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelInstruments.add((Component)this.jButtonChangeNodeSize, gridBagConstraints);
        this.jButtonWeights.setText("Weights...");
        this.jButtonWeights.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonWeightsActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = 2;
        gridBagConstraints.anchor = 11;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelInstruments.add((Component)this.jButtonWeights, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.getContentPane().add((Component)this.jPanelInstruments, gridBagConstraints);
        this.jPanelAdditionalInfo.setBorder(BorderFactory.createTitledBorder("Answer"));
        this.jPanelAdditionalInfo.setLayout(new GridBagLayout());
        this.jToggleButtonEnterAnswer.setText("Enter Answer");
        this.jToggleButtonEnterAnswer.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jToggleButtonEnterAnswerActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelAdditionalInfo.add((Component)this.jToggleButtonEnterAnswer, gridBagConstraints);
        this.jLabelRightAnswer.setText("Answer:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelAdditionalInfo.add((Component)this.jLabelRightAnswer, gridBagConstraints);
        this.jTextFieldRightAnswer.setEditable(false);
        this.jTextFieldRightAnswer.setFont(new Font("Tahoma", 1, 11));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelAdditionalInfo.add((Component)this.jTextFieldRightAnswer, gridBagConstraints);
        this.jButtonAnswerOK.setText("OK");
        this.jButtonAnswerOK.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonAnswerOKActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 2;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelAdditionalInfo.add((Component)this.jButtonAnswerOK, gridBagConstraints);
        this.jScrollPane2.setMinimumSize(new Dimension(1, 1));
        this.jScrollPane2.setPreferredSize(new Dimension(35, 1));
        this.jListAnswers.setModel(new AbstractListModel(){
            String[] strings;

            {
                this.strings = new String[0];
            }

            public int getSize() {
                return this.strings.length;
            }

            public Object getElementAt(int i) {
                return this.strings[i];
            }
        });
        this.jListAnswers.addListSelectionListener(new ListSelectionListener(){

            public void valueChanged(ListSelectionEvent evt) {
                Main.this.jListAnswersValueChanged(evt);
            }
        });
        this.jScrollPane2.setViewportView(this.jListAnswers);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelAdditionalInfo.add((Component)this.jScrollPane2, gridBagConstraints);
        this.jButtonRemoveAnswer.setText("-");
        this.jButtonRemoveAnswer.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonRemoveAnswerActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelAdditionalInfo.add((Component)this.jButtonRemoveAnswer, gridBagConstraints);
        this.jButtonAddAnswer.setText("+");
        this.jButtonAddAnswer.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonAddAnswerActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelAdditionalInfo.add((Component)this.jButtonAddAnswer, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.getContentPane().add((Component)this.jPanelAdditionalInfo, gridBagConstraints);
        this.jPanelMain.setBorder(BorderFactory.createTitledBorder("File"));
        this.jPanelMain.setLayout(new GridBagLayout());
        this.jButtonOpen.setText("Open...");
        this.jButtonOpen.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonOpenActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelMain.add((Component)this.jButtonOpen, gridBagConstraints);
        this.jButtonSaveAs.setText("Save As...");
        this.jButtonSaveAs.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonSaveAsActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelMain.add((Component)this.jButtonSaveAs, gridBagConstraints);
        this.jButtonSave.setText("Save");
        this.jButtonSave.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonSaveActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelMain.add((Component)this.jButtonSave, gridBagConstraints);
        this.jButtonNew.setText("New");
        this.jButtonNew.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonNewActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelMain.add((Component)this.jButtonNew, gridBagConstraints);
        this.jButtonAbout.setText("About...");
        this.jButtonAbout.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonAboutActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelMain.add((Component)this.jButtonAbout, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.getContentPane().add((Component)this.jPanelMain, gridBagConstraints);
        this.jPanelDemonstrate.setBorder(BorderFactory.createTitledBorder("Demonstrate"));
        this.jPanelDemonstrate.setLayout(new GridBagLayout());
        this.jButtonDemonstrate.setText("Demonstrate");
        this.jButtonDemonstrate.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jButtonDemonstrateActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.jPanelDemonstrate.add((Component)this.jButtonDemonstrate, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        this.getContentPane().add((Component)this.jPanelDemonstrate, gridBagConstraints);
        this.pack();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void jButtonNewActionPerformed(ActionEvent evt) {
        try {
            this.releaseAll();
            this.test = new Test();
            this.dest = null;
        }
        finally {
            this.viewTest();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void jButtonOpenActionPerformed(ActionEvent evt) {
        this.releaseAll();
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open");
        fc.setApproveButtonText("Open");
        fc.setFileSelectionMode(0);
        fc.setMultiSelectionEnabled(false);
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileFilter(){

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                return f.getName().toLowerCase().endsWith(".tst");
            }

            public String getDescription() {
                return Names.nameFilesTests;
            }
        });
        File dir = new File(Names.dirTests);
        if (!dir.exists()) {
            dir.mkdir();
        }
        fc.setCurrentDirectory(dir);
        int reply = fc.showOpenDialog(this);
        if (reply == 0) {
            try {
                this.test = Test.load(fc.getSelectedFile(), this);
                this.dest = fc.getSelectedFile();
                this.viewTest();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, Translatable.errorOpen, Translatable.error, 0);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void jButtonSaveActionPerformed(ActionEvent evt) {
        if (this.dest == null) {
            this.jButtonSaveAsActionPerformed(null);
            return;
        }
        try {
            this.releaseAll();
            if (!this.acceptChanges()) {
                return;
            }
            try {
                this.test.save(this.dest);
            }
            catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, Translatable.errorSave, Translatable.error, 0);
            }
        }
        finally {
            this.update();
        }
    }

    private void jButtonSaveAsActionPerformed(ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save As");
        fc.setApproveButtonText("Save");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileFilter(){

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                return f.getName().toLowerCase().endsWith(".tst");
            }

            public String getDescription() {
                return Names.nameFilesTests;
            }
        });
        File dir = new File(Names.dirTests);
        if (dir.exists()) {
            fc.setCurrentDirectory(dir);
        }
        int reply = fc.showSaveDialog(this);
        if (reply == 0) {
            this.dest = fc.getSelectedFile();
            if (!this.dest.getName().toLowerCase().endsWith(".tst")) {
                this.dest = new File(this.dest.getPath() + ".tst");
            }
            this.jButtonSave.setEnabled(true);
            this.jButtonSaveActionPerformed(null);
        }
    }

    private void jToggleButtonNodeActionPerformed(ActionEvent evt) {
        this.releaseOperation();
    }

    private void jToggleButtonNoteActionPerformed(ActionEvent evt) {
        this.releaseOperation();
    }

    private void jToggleButtonEdgeActionPerformed(ActionEvent evt) {
        this.releaseOperation();
    }

    private void jToggleButtonEnterAnswerActionPerformed(ActionEvent evt) {
        if (this.jToggleButtonEnterAnswer.isSelected()) {
            this.jToggleButtonNode.setSelected(true);
            this.test.additionalInfo.getAnswer(this.answerNumber).clear();
        }
        this.update();
    }

    private void jButtonDeleteActionPerformed(ActionEvent evt) {
        this.test.testScheme.deleteObject(this.selected);
        this.selected = null;
        this.update();
    }

    private void jButtonChangeNodeSizeActionPerformed(ActionEvent evt) {
        String s = (String)JOptionPane.showInputDialog(this, Translatable.enterSize, Translatable.nodeSize, 3, null, null, Constants.nodeDiameter);
        if (s == null || s.equals("")) {
            return;
        }
        try {
            double val = Double.parseDouble(s);
            if (0.0 >= val || val >= 1.0) {
                throw new Exception();
            }
            Constants.nodeDiameter = val;
            this.update();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, Translatable.errorFloat01, Translatable.error, 0);
            return;
        }
    }

    private void jButtonAnswerOKActionPerformed(ActionEvent evt) {
        this.jToggleButtonEnterAnswer.setSelected(false);
        this.jToggleButtonEnterAnswerActionPerformed(null);
    }

    private void jButtonDemonstrateActionPerformed(ActionEvent evt) {
        if (!this.drawPanel.demonstrate) {
            this.releaseOperation();
            this.demonstrate(false, null);
        } else {
            this.stopDemonstrate = true;
            this.setAllEnabled(true);
            this.jButtonDemonstrate.setText(Translatable.demonstrateTitle);
            this.drawPanel.demonstrate = false;
            this.update();
        }
    }

    private void drawPanelMouseDragged(MouseEvent evt) {
        if (this.move) {
            ((Movable)((Object)this.selected)).moveTo(evt.getX(), evt.getY());
            this.update();
        }
    }

    private void jButtonAboutActionPerformed(ActionEvent evt) {
        new AboutBox(this, true).setVisible(true);
    }

    private void jButtonRemoveAnswerActionPerformed(ActionEvent evt) {
        if (this.test.additionalInfo.size() > 1) {
            this.test.additionalInfo.remove(this.answerNumber);
            if (this.answerNumber == this.test.additionalInfo.size()) {
                --this.answerNumber;
            }
            this.update();
        }
    }

    private void jButtonAddAnswerActionPerformed(ActionEvent evt) {
        this.test.additionalInfo.add(++this.answerNumber);
        this.jListAnswers.setSelectedIndex(this.answerNumber);
        this.update();
    }

    private void jListAnswersValueChanged(ListSelectionEvent evt) {
        this.answerNumber = this.jListAnswers.getSelectedIndex();
        this.update();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void drawPanelMousePressed(MouseEvent evt) {
        block21 : {
            if (this.drawPanel.demonstrate) {
                return;
            }
            try {
                int index;
                int x = evt.getX();
                int y = evt.getY();
                if (this.jToggleButtonNote.isSelected()) {
                    String s = JOptionPane.showInputDialog(this, Translatable.enterText, Translatable.note, 3);
                    if (s == null || s.equals("")) {
                        return;
                    }
                    this.selected = this.drawPanel.addNote(x, y, s);
                    return;
                }
                TestObject to = this.drawPanel.findTestObject(x, y, this.selected);
                if (to != null) {
                    WeightedNode node;
                    if (to instanceof WeightedNode && (node = (WeightedNode)to) != null) {
                        this.setSelectedNode(node);
                        if (this.selected instanceof Movable) {
                            this.move = true;
                            ((Movable)((Object)this.selected)).startMoving(x, y, this.drawPanel.getWidth(), this.drawPanel.getHeight());
                        }
                        return;
                    }
                    this.selected = to;
                    if (this.selected instanceof Movable) {
                        this.move = true;
                        ((Movable)((Object)this.selected)).startMoving(x, y, this.drawPanel.getWidth(), this.drawPanel.getHeight());
                    }
                    return;
                }
                if (this.jToggleButtonEnterAnswer.isSelected()) {
                    return;
                }
                if (!this.jToggleButtonNode.isSelected()) break block21;
                String s = (String)JOptionPane.showInputDialog(this, Translatable.enterIndex, Translatable.node, 3, null, null, this.test.testScheme.getNextIndex());
                if (s == null || s.equals("")) {
                    return;
                }
                try {
                    index = Integer.parseInt(s);
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, Translatable.errorInteger, Translatable.error, 0);
                    this.update();
                    return;
                }
                this.selected = this.drawPanel.addNode(x, y, index);
            }
            finally {
                this.update();
            }
        }
    }

    private void drawPanelMouseReleased(MouseEvent evt) {
        this.move = false;
    }

    private void jButtonWeightsActionPerformed(ActionEvent evt) {
        VectorStringEdit edit = new VectorStringEdit(this, true, ((WeightedObject)this.selected).getNotesStrings(), Translatable.weightsTitle);
        edit.setVisible(true);
        if (edit.result) {
            ((WeightedObject)this.selected).setNotesStrings(this.drawPanel.getWidth(), this.drawPanel.getHeight(), edit.model.getData());
        }
        this.update();
    }

    private void formWindowClosing(WindowEvent evt) {
        Configuration.save();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                new Main().setVisible(true);
            }
        });
    }

    private void releaseAll() {
        this.answerNumber = 0;
        this.jToggleButtonNode.setSelected(true);
        if (this.test == null) {
            return;
        }
        this.jListAnswers.setSelectedIndex(0);
        this.releaseOperation();
    }

    private void releaseOperation() {
        if (this.selected == null) {
            return;
        }
        if (this.jToggleButtonEnterAnswer.isSelected()) {
            this.jToggleButtonEnterAnswer.setSelected(false);
        }
        this.selected = null;
        this.update();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setSelectedNode(TestObject i) {
        try {
            WeightedEdge j;
            if (i == null) {
                return;
            }
            if (this.jToggleButtonEnterAnswer.isSelected()) {
                this.test.additionalInfo.getAnswer(this.answerNumber).add(((WeightedNode)i).getMain().getIndex());
            } else if (this.jToggleButtonEdge.isSelected() && this.selected instanceof WeightedNode && i instanceof WeightedNode && (j = this.drawPanel.addDirectEdge((WeightedNode)this.selected, (WeightedNode)i)) != null) {
                i = j;
            }
        }
        finally {
            this.selected = i;
            this.update();
        }
    }

    private void viewTest() {
        this.jTextAreaTask.setText(this.test.task);
        this.drawPanel.setTestScheme(this.test.testScheme);
        this.update();
    }

    private boolean acceptChanges() {
        try {
            this.test.task = this.jTextAreaTask.getText();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, Translatable.errorPoints, Translatable.error, 0);
            return false;
        }
        return true;
    }

    private void update() {
        if (!this.drawPanel.demonstrate) {
            if (this.dest == null) {
                this.setTitle(Names.makeGraph);
            } else {
                this.setTitle(this.dest.getName() + " - " + Names.makeGraph);
            }
            this.jButtonSave.setEnabled(this.dest != null);
            this.jTextFieldRightAnswer.setText(this.test.additionalInfo.getAnswer(this.answerNumber).toString());
            this.drawPanel.setSelected(this.selected);
            this.drawPanel.setChoosed(null);
            if (this.jToggleButtonEnterAnswer.isSelected()) {
                this.drawPanel.setChoosed(this.test.additionalInfo.getAnswer(this.answerNumber));
            }
            this.drawPanel.repaint();
            this.jButtonDelete.setEnabled(this.selected != null);
            this.jButtonAnswerOK.setEnabled(this.jToggleButtonEnterAnswer.isSelected());
            this.jListAnswers.updateUI();
            this.jButtonWeights.setEnabled(this.selected instanceof WeightedObject);
        }
    }

    private void demonstrate(final boolean _check, final Vector<Integer> _v) {
        this.setAllEnabled(false);
        this.jButtonDemonstrate.setEnabled(true);
        this.jButtonDemonstrate.setText(Translatable.stop);
        this.drawPanel.demonstrate = true;
        this.stopDemonstrate = false;
        Thread t = new Thread(){
            boolean check;
            Vector<Integer> shouldbe;
            Vector<Integer> seq;
            Vector<Integer> demo;
            Vector<Integer> choosed;
            boolean lastWrong;
            boolean asked;

            {
                this.shouldbe = _v;
                this.check = _check;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void run() {
                try {
                    if (this.shouldbe == null) {
                        this.shouldbe = new Vector();
                    }
                    this.seq = new Vector();
                    this.choosed = Main.this.test.additionalInfo.getAnswer(Main.this.answerNumber);
                    if (!this.check) {
                        this.seq = this.choosed;
                    } else {
                        int i;
                        for (i = 0; i < this.shouldbe.size() && i < this.choosed.size() && this.shouldbe.get(i).equals(this.choosed.get(i)); ++i) {
                            this.seq.add(this.shouldbe.get(i));
                        }
                        if (i < this.choosed.size()) {
                            this.lastWrong = true;
                            this.seq.add(this.choosed.get(i));
                        } else if (i < this.shouldbe.size()) {
                            this.lastWrong = true;
                            this.seq.add(this.shouldbe.get(i));
                        }
                    }
                    this.demo = new Vector();
                    while (!Main.this.stopDemonstrate) {
                        Main.this.drawPanel.choosed = null;
                        Main.this.drawPanel.right = null;
                        Main.this.drawPanel.wrong = null;
                        if (!this.check) {
                            Main.this.drawPanel.choosed = (Collection)this.demo.clone();
                        } else if (!this.lastWrong || this.demo.size() < this.seq.size()) {
                            Main.this.drawPanel.right = (Collection)this.demo.clone();
                        } else {
                            Vector right = (Vector)this.demo.clone();
                            Vector<Integer> wrong = new Vector<Integer>();
                            wrong.add(this.demo.lastElement());
                            right.remove(right.size() - 1);
                            Main.this.drawPanel.right = right;
                            Main.this.drawPanel.wrong = wrong;
                        }
                        Main.this.drawPanel.repaint();
                        if (this.demo.size() == this.seq.size()) {
                            if (!this.check || this.asked) {
                                this.demo.clear();
                            } else {
                                try {
                                    Thread.sleep(500);
                                }
                                catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                int usrReply = JOptionPane.showConfirmDialog(
                                        Main.this.rootPane,
                                        "<html><body><p><span style=\"color:"
                                                + (this.lastWrong ? "red" : "green")
                                                + "; font-size:large\"><strong>"
                                                + (this.lastWrong ? Translatable.wrong : Translatable.right)
                                                + "</strong></span></p><p><br>" + Translatable.tostop
                                                + "</p></body></html>",
                                        Translatable.demonstrateTitle, 0
                                );
                                if (usrReply == 0) {
                                    Main.this.jButtonDemonstrateActionPerformed(null);
                                    return;
                                }
                                this.asked = true;
                                this.demo.clear();
                            }
                        } else {
                            this.demo.add(this.seq.get(this.demo.size()));
                        }
                        try {
                            Thread.sleep(500);
                        }
                        catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                finally {
                    Main.this.drawPanel.choosed = null;
                    Main.this.drawPanel.right = null;
                    Main.this.drawPanel.wrong = null;
                }
            }
        };
        t.setPriority(5);
        t.start();
    }

    private void setAllEnabled(boolean b) {
        this.setAllEnabled(this.getContentPane(), b);
    }

    private void setAllEnabled(Container contentPane, boolean b) {
        for (Component comp : contentPane.getComponents()) {
            if (comp instanceof JPanel) {
                this.setAllEnabled((Container)comp, b);
                continue;
            }
            comp.setEnabled(b);
        }
    }

}

