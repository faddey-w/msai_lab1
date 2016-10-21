/*
 * Decompiled with CFR 0_115.
 */
package windows;

import java.awt.Component;
import java.awt.Container;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import strings.Translatable;
import windows.VectorStringModel;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class VectorStringEdit
extends JDialog {
    public VectorStringModel model;
    public boolean result;
    private KeyEventDispatcher ked;
    private JButton addButton;
    private JButton cancelButton;
    private JButton changeButton;
    private JList dataList;
    private JTextField dataTextField;
    private JScrollPane jScrollPane1;
    private JButton okButton;
    private JButton removeButton;

    public VectorStringEdit(Frame parent, boolean modal, Vector<String> data, String title) {
        super(parent, modal);
        this.initComponents();
        this.model = new VectorStringModel(data);
        this.setTitle(title);
        this.changeButton.setText(Translatable.change);
        this.dataList.setModel(this.model);
        this.cancelButton.setText(Translatable.cancel);
        this.setLocationRelativeTo(parent);
        this.dataList.setSelectedIndex(this.model.getSize() - 1);
        this.pack();
        this.ked = new KeyEventDispatcher(){

            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == 401) {
                    if (e.getKeyCode() == 10) {
                        e.consume();
                        VectorStringEdit.this.changeButtonActionPerformed(null);
                    }
                    if (e.getKeyChar() == '+') {
                        e.consume();
                        VectorStringEdit.this.addButtonActionPerformed(null);
                    }
                    if (e.getKeyChar() == '-') {
                        e.consume();
                        VectorStringEdit.this.removeButtonActionPerformed(null);
                    }
                }
                if (e.getID() == 400) {
                    if (e.getKeyChar() == '+') {
                        e.consume();
                    }
                    if (e.getKeyChar() == '-') {
                        e.consume();
                    }
                }
                return false;
            }
        };
        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this.ked);
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.dataList = new JList();
        this.dataTextField = new JTextField();
        this.addButton = new JButton();
        this.removeButton = new JButton();
        this.changeButton = new JButton();
        this.okButton = new JButton();
        this.cancelButton = new JButton();
        this.setDefaultCloseOperation(2);
        this.addWindowListener(new WindowAdapter(){

            public void windowClosed(WindowEvent evt) {
                VectorStringEdit.this.formWindowClosed(evt);
            }

            public void windowOpened(WindowEvent evt) {
                VectorStringEdit.this.formWindowOpened(evt);
            }
        });
        this.getContentPane().setLayout(new GridBagLayout());
        this.jScrollPane1.setPreferredSize(new Dimension(60, 100));
        this.dataList.setModel(new AbstractListModel(){
            String[] strings;

            public int getSize() {
                return this.strings.length;
            }

            public Object getElementAt(int i) {
                return this.strings[i];
            }
        });
        this.dataList.setMinimumSize(new Dimension(1, 1));
        this.dataList.addListSelectionListener(new ListSelectionListener(){

            public void valueChanged(ListSelectionEvent evt) {
                VectorStringEdit.this.dataListValueChanged(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.dataList);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.jScrollPane1, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.dataTextField, gridBagConstraints);
        this.addButton.setText("+");
        this.addButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                VectorStringEdit.this.addButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        this.getContentPane().add((Component)this.addButton, gridBagConstraints);
        this.removeButton.setText("-");
        this.removeButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                VectorStringEdit.this.removeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.removeButton, gridBagConstraints);
        this.changeButton.setText("Change");
        this.changeButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                VectorStringEdit.this.changeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.changeButton, gridBagConstraints);
        this.okButton.setText("OK");
        this.okButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                VectorStringEdit.this.okButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.okButton, gridBagConstraints);
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                VectorStringEdit.this.cancelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.cancelButton, gridBagConstraints);
        this.pack();
    }

    private void changeButtonActionPerformed(ActionEvent evt) {
        int i = this.dataList.getSelectedIndex();
        if (i != -1) {
            this.model.set(i, this.dataTextField.getText());
        }
        this.dataTextField.requestFocusInWindow();
    }

    private void removeButtonActionPerformed(ActionEvent evt) {
        int i = this.dataList.getSelectedIndex();
        if (i != -1) {
            this.model.remove(i);
            if (i == this.model.getSize()) {
                --i;
            }
            this.dataList.setSelectedIndex(i);
        }
        this.dataTextField.requestFocusInWindow();
    }

    private void addButtonActionPerformed(ActionEvent evt) {
        int i = this.dataList.getSelectedIndex();
        this.model.add(++i, this.dataTextField.getText());
        this.dataList.setSelectedIndex(i);
        this.dataTextField.requestFocusInWindow();
    }

    private void dataListValueChanged(ListSelectionEvent evt) {
        int i = this.dataList.getSelectedIndex();
        if (i != -1) {
            this.dataTextField.setText(this.model.getElementAt(i));
        }
        this.dataTextField.requestFocusInWindow();
    }

    private void formWindowOpened(WindowEvent evt) {
        this.result = false;
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        this.result = true;
        this.dispose();
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void formWindowClosed(WindowEvent evt) {
        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this.ked);
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
                VectorStringEdit dialog = new VectorStringEdit(new JFrame(), true, new Vector<String>(), "");
                dialog.addWindowListener(new WindowAdapter(){

                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }

        });
    }

}

