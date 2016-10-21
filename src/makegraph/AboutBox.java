/*
 * Decompiled with CFR 0_115.
 */
package makegraph;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import strings.Names;
import strings.Translatable;

public class AboutBox
extends JDialog {
    private JLabel appDescLabel;
    private JLabel appTitleLabel;
    private JLabel authorLabel;
    private JLabel companyLabel;
    private JLabel developerLabel;
    private JLabel distributionLabel;
    private JLabel emailLabel;
    private JButton jButtonClose;
    private JLabel jLabelImage;
    private JLabel versionLabel;

    public AboutBox(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.setTitle(Names.about + " " + Names.makeGraph);
        this.appTitleLabel.setText(Names.makeGraph);
        this.appDescLabel.setText(Translatable.makeGraphDesc);
        this.versionLabel.setText(Translatable.productVersion + Names.makeGraphVersion);
        this.authorLabel.setText(Translatable.author + Translatable.appAuthor);
        this.developerLabel.setText(Translatable.developer + Translatable.appDeveloper);
        this.companyLabel.setText(Translatable.appCompany);
        this.distributionLabel.setText(Translatable.distribution + Translatable.appDistribution);
        this.jButtonClose.setText(Translatable.close);
        this.setLocationByPlatform(true);
        this.pack();
    }

    private void initComponents() {
        this.jLabelImage = new JLabel();
        this.appTitleLabel = new JLabel();
        this.appDescLabel = new JLabel();
        this.developerLabel = new JLabel();
        this.versionLabel = new JLabel();
        this.authorLabel = new JLabel();
        this.emailLabel = new JLabel();
        this.distributionLabel = new JLabel();
        this.jButtonClose = new JButton();
        this.companyLabel = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setModal(true);
        this.setResizable(false);
        this.getContentPane().setLayout(new GridBagLayout());
        this.jLabelImage.setIcon(new ImageIcon(this.getClass().getResource("/strings/11-03-28-topcoder.jpg")));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = 18;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.jLabelImage, gridBagConstraints);
        this.appTitleLabel.setFont(this.appTitleLabel.getFont().deriveFont(this.appTitleLabel.getFont().getStyle() | 1, this.appTitleLabel.getFont().getSize() + 4));
        this.appTitleLabel.setText("Basic Application Example");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.appTitleLabel, gridBagConstraints);
        this.appDescLabel.setText("<html>A simple Java desktop application based on Swing Application Framework.");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.appDescLabel, gridBagConstraints);
        this.developerLabel.setText("Developer:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.insets = new Insets(0, 50, 0, 5);
        this.getContentPane().add((Component)this.developerLabel, gridBagConstraints);
        this.versionLabel.setText("Product Version:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.versionLabel, gridBagConstraints);
        this.authorLabel.setText("Author:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.insets = new Insets(5, 50, 5, 5);
        this.getContentPane().add((Component)this.authorLabel, gridBagConstraints);
        this.emailLabel.setText("<html>(<u><span style=\"color:blue\">arktoswb@gmail.com</u></span>)");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.insets = new Insets(0, 70, 5, 5);
        this.getContentPane().add((Component)this.emailLabel, gridBagConstraints);
        this.distributionLabel.setText("Distribution");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.distributionLabel, gridBagConstraints);
        this.jButtonClose.setText("Close");
        this.jButtonClose.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                AboutBox.this.jButtonCloseActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = 3;
        gridBagConstraints.anchor = 13;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.jButtonClose, gridBagConstraints);
        this.companyLabel.setText("Company");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        this.getContentPane().add((Component)this.companyLabel, gridBagConstraints);
        this.pack();
    }

    private void jButtonCloseActionPerformed(ActionEvent evt) {
        this.dispose();
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
                AboutBox dialog = new AboutBox(new JFrame(), true);
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

