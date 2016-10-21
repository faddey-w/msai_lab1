/*
 * Decompiled with CFR 0_115.
 */
package windows;

import java.awt.Component;
import java.awt.Container;
import java.awt.DefaultKeyboardFocusManager;
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
import java.io.PrintStream;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class InputEntries
extends JDialog {
    public boolean result;
    public Vector<String> res;
    private Vector<JTextField> entries;
    private KeyEventDispatcher ked;
    private JButton cancelButton;
    private JButton okButton;

    public InputEntries(Frame parent, boolean modal, String title, Vector<String> names, Vector<String> _initialValues) {
        super(parent, modal);
        int i;
        this.initComponents();
        this.setLocationRelativeTo(parent);
        this.setTitle(title);
        int n = names.size();
        Vector initialValues = (Vector)_initialValues.clone();
        this.entries = new Vector();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        for (i = 0; i < n; ++i) {
            JLabel lab = new JLabel(names.get(i));
            gbc.gridx = 0;
            gbc.gridy = i;
            this.getContentPane().add((Component)lab, gbc);
            if (initialValues.size() <= i) {
                initialValues.add("");
            }
            JTextField entry = new JTextField((String)initialValues.get(i));
            gbc.gridx = 1;
            this.entries.add(entry);
            this.getContentPane().add((Component)entry, gbc);
        }
        gbc.gridx = 0;
        gbc.gridy = i;
        this.getContentPane().add((Component)this.okButton, gbc);
        gbc.gridx = 1;
        this.getContentPane().add((Component)this.cancelButton, gbc);
        this.res = initialValues;
        this.pack();
        this.ked = new KeyEventDispatcher(){

            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == 401 && e.getKeyCode() == 10) {
                    System.out.println("enter");
                    e.consume();
                    InputEntries.this.okButtonActionPerformed(null);
                }
                return false;
            }
        };
        DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this.ked);
    }

    private void initComponents() {
        this.cancelButton = new JButton();
        this.okButton = new JButton();
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                InputEntries.this.cancelButtonActionPerformed(evt);
            }
        });
        this.okButton.setText("OK");
        this.okButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                InputEntries.this.okButtonActionPerformed(evt);
            }
        });
        this.setDefaultCloseOperation(2);
        this.addWindowListener(new WindowAdapter(){

            public void windowClosed(WindowEvent evt) {
                InputEntries.this.formWindowClosed(evt);
            }

            public void windowOpened(WindowEvent evt) {
                InputEntries.this.formWindowOpened(evt);
            }
        });
        this.getContentPane().setLayout(new GridBagLayout());
        this.pack();
    }

    private void formWindowOpened(WindowEvent evt) {
        this.result = false;
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        this.result = true;
        for (int i = 0; i < this.res.size(); ++i) {
            this.res.set(i, this.entries.get(i).getText());
        }
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
                Vector<String> n = new Vector<String>();
                n.add("First index: ");
                n.add("Last index: ");
                n.add("Something else: ");
                Vector<String> i = new Vector<String>();
                i.add("1");
                i.add("-1");
                InputEntries dialog = new InputEntries(new JFrame(), true, "title", n, i);
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

